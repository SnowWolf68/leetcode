#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

// --> 只包含一个0的最长子数组长度
int longestSubarray(vector<int> &nums) {
    int n = nums.size(), r = 0, ret = 0, cnt = 0;
    for(int i = 0;r < n;r++){
        if(nums[r] == 0) cnt++;
        while(cnt > 1 && i < n){
            if(nums[i] == 0) cnt--;
            i++;
        }
        if(i <= r) ret = max(ret, r - i + 1);
    }
    return min(ret - 1, n - 1);
}

int main(){
    vector<int> nums = {0,1,1,1,0,1,1,0,1};
    cout << longestSubarray(nums) << endl;
    return 0;
}