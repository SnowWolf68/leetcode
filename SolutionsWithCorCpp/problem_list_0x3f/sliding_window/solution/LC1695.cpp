#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int maximumUniqueSubarray(vector<int> &nums) {
    int n = nums.size(), curSum = 0, ret = 0, r = 0;
    unordered_map<int, int> map;
    for(int i = 0;r < n;r++){
        curSum += nums[r];
        map[nums[r]]++;
        while(map[nums[r]] > 1 && i < n){
            curSum -= nums[i];
            map[nums[i]]--;
            i++;
        }
        if(i <= r) ret = max(curSum, ret);
    }
    return ret;
}