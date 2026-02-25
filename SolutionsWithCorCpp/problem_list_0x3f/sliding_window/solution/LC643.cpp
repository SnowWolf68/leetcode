#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

double findMaxAverage(vector<int> &nums, int k) {
    int n = nums.size(), r = 0;
    double max_avg = -1e4, cur_sum = nums[0];
    for(int i = 0;i < n;i++){
        while(r + 1 < n && r - i + 1 < k){
            r++;
            cur_sum += nums[r];
        }
        if(r - i + 1 == k){
            max_avg = max(max_avg, cur_sum / (r - i + 1));
        }
        cur_sum -= nums[i];
    }
    return max_avg;
}