#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int longestOnes(vector<int> &nums, int k) {
    int n = nums.size(), r = 0, ret = 0, cnt = 0;
    for(int i = 0;r < n;r++){
        if(nums[r] == 0) cnt++;
        while(cnt > k && i < n){
            if(nums[i] == 0) cnt--;
            i++;
        }
        if(i <= r) ret = max(ret, r - i + 1);
    }
    return ret;
}