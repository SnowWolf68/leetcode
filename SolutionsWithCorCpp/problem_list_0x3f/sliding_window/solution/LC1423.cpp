#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

// 逆向思维, 拿两边的 ---> 剩中间的
// 两边最大 ---> 中间最小
int maxScore(vector<int> &nums, int k) {
    int n = nums.size(), ret = 0, cur_sum = 0;
    k = n - k;
    int r = k;
    for(int i = 0;i < k;i++){
        cur_sum += nums[i];
    }
    ret = cur_sum;
    for(int i = 1;r < n;i++){
        cur_sum += nums[r];
        cur_sum -= nums[i - 1];
        ret = min(ret, cur_sum);
        r++;
    }
    int tot_sum = 0;
    for(int x : nums) tot_sum += x;
    return tot_sum - ret;
}