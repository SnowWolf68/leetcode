#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

/**
枚举nums[k] + 最接近的两数之和
 */
int threeSumClosest(vector<int> &nums, int target) {
    sort(nums.begin(), nums.end());
    int n = nums.size(), ret = INT_MAX, diff = INT_MAX;
    for(int k = 0;k < n;k++){
        int t = target - nums[k];
        int i = k + 1, j = n - 1;
        while(i < j){
            int cur = nums[i] + nums[j];
            if(abs(cur - t) < diff){
                diff = abs(cur - t);
                ret = nums[k] + cur;
            }
            if(nums[i] + nums[j] > t) j--;
            else i++;
        }
    }
    return ret;
}