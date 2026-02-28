#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int minSizeSubarray(vector<int> &nums, int target) {
    int n = nums.size(), ret = n, r = 0;
    long long totSum = 0, curSum = 0;
    for(int i = 0;i < n;i++) totSum += nums[i];
    int times = target / totSum;
    target %= totSum;
    for(int i = 0;r < n;r++){
        curSum += nums[r];
        while(curSum >= target && i < n){
            if(curSum == target){
                ret = min(ret, r - i + 1);
            }
            curSum -= nums[i];
            i++;
        }
    }
    curSum = 0, r = 0;
    for(int i = 0;r < n;r++){
        curSum += nums[r];
        while(curSum >= totSum - target && i < n){
            if(curSum == totSum - target){
                ret = min(ret, n - (r - i + 1));
            }
            curSum -= nums[i];
            i++;
        }
    }
    return (ret == n ? (totSum == target ? ret : -1) : (ret + n * times));
}