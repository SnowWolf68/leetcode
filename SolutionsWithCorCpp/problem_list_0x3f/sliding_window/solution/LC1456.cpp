#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

bool check(char ch) {
	if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
		return true;
	} else return false;
}

int maxVowels(string s, int k) {
	int n = s.size(), ret = 0, cur = check(s[0]) ? 1 : 0, r = 0;
	for (int i = 0; i < n; i++) {
		while (r + 1 < n && r - i + 1 < k) {
            r++;
            if(check(s[r])) cur++;
		}
        // cout << "l = " << i << " r = " << r << " cur = " << cur << endl;
        if(r - i + 1 == k){
            ret = max(ret, cur);
        }
        if(check(s[i])) cur--;
	}
    return ret;
}

int main(){
    string s = "abciiidef";
    int k = 3;
    cout << maxVowels(s, k) << endl;
}
