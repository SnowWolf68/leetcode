#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
转换成两次小于 (LC2824) 有点前缀和那味
假设 <= upper --> cnt1, < lower --> cnt2
则 ret = cnt1 - cnt2
 */
long long countFairPairs(vector<int> &nums, int lower, int upper) {
    int n = nums.size();
    int i = 0, j = n - 1;
    long long cnt1 = 0, cnt2 = 0;
    sort(nums.begin(), nums.end());
    while(i < j){
        if(nums[i] + nums[j] > upper){
            j--;
        }else{
            cnt1 += j - i;
            i++;
        }
    }
    i = 0; j = n - 1;
    while(i < j){
        if(nums[i] + nums[j] >= lower){
            j--;
        }else{
            cnt2 += j - i;
            i++;
        }
    }
    return cnt1 - cnt2;
}