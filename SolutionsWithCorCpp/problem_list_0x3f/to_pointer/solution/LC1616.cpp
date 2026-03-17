#include <algorithm>
#include <iostream>
#include <stack>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
Note: 
a = "pvhmupgqeltozftlmfjjde"
b = "yjgpzbezspnnpszebzmhvp"
预期结果 : true
 */

/**
这个dp是一个O(n ^ 2)的dp, 在1e5的范围下会TLE
*/
vector<vector<bool>> dp(string& a){
    int n = a.size();
    vector<vector<bool>> dp(n, vector<bool>(n, false));
    for(int i = 0;i < n;i++) dp[i][i] = true;
    for(int i = n - 2;i >= 0;i--){
        for(int j = i + 1;j < n;j++){
            dp[i][j] = (j - 1 < i + 1 ? true : dp[i + 1][j - 1]) && (a[i] == a[j]);
        }
    }
    return dp;
}

// a.prefix + b.suffix
// 如果用O(n ^ 2)来判断[i, j]的子串是否回文, 那么在1e5的范围下会TLE
bool check(string& a, string& b){
    int n = a.size(), m = b.size();
    vector<vector<bool>> a_dp = dp(a), b_dp = dp(b);
    if(a_dp[0][n - 1] || b_dp[0][m - 1]) return true;
    int i = 0, j = m - 1;
    while(i < n && j >= 0){
        if(a[i] == b[j]){
            if(i + 1 == j || i + 2 == j) return true;
            if(a_dp[i + 1][j - 1] || b_dp[i + 1][j - 1]) return true;
            i++;
            j--;
        }else{
            break;
        }
    }
    return false;
}

// a.prefix + b.suffix
// 如果想要优化, 一种方法是使用Manacher在O(n)时间内判断子串回文, 或者考虑其他的办法(贪心)
/**
贪心策略: 其实我们并不需要每次i++, j--之后都判断子串是否回文, 只有a[i], b[j]失配之后判断一次即可
为什么这个贪心策略是正确的?
    如果对于某一次的[i, j], 此时a[i + 1, j - 1]区间是回文串, 
        那么i++, j--匹配多次直到失配之后, 此时a[i' + 1, j' - 1]仍然是回文串
    

    a: a[0], ... a[i], a[i + 1], ... a[i'], a[i' + 1]

    b: b[0], ...                                                b[j' - 1], b[j'], ... b[j - 1], b[j], ... b[m - 1]

 */

bool checkSingleStr(string &a){
    int n = a.size(), i = 0, j = n - 1;
    while(i < j){
        if(a[i] != a[j]) return false;
        i++;
        j--;
    }
    return true;
}

bool checkSingleSubStr(string& a, int l, int r){
    while(l < r){
        if(a[l] != a[r]) {
            return false;
        }
        l++;
        r--;
    }
    return true;
}

bool check2(string& a, string& b){
    int n = a.size();   // a.size() == b.size() 题目保证
    int i = 0, j = n - 1;
    while(i < n && j >= 0){
        if(a[i] == b[j]){
            i++;
            j--;
        }else{
            break;
        }
    }
    return checkSingleSubStr(a, i, j) || checkSingleSubStr(b, i, j);
}

bool checkPalindromeFormation(string a, string b) {
    if(checkSingleStr(a) || checkSingleStr(b)) return true;
    return check2(a, b) || check2(b, a);
}

int main(){
    // string a = "abda", b = "acmc";  // false
    string a = "pvhmupgqeltozftlmfjjde", b = "yjgpzbezspnnpszebzmhvp";  // true
    cout << checkPalindromeFormation(a, b) << endl;

    return 0;
}