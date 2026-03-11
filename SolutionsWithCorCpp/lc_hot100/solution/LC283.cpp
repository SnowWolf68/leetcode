#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
-- 原地操作

从前往后遍历nums[i], 同时维护一个下标idx, 使得[idx, i]区间全都是0(空位)
初始情况: i = 0, idx = 0, idx <= i

                    idx   i
    nums: [x, x, x, 0, 0, 0, x, x, x, ...]

遍历nums[i]: 
    1. 如果nums[i] == 0: 
        idx不动, i++
    2. 如果nums[i] != 0:
        交换nums[idx]与nums[i], idx++, i++

(idx要么等于i, 要么指向从左端起, 第一个0的位置)
 */
void moveZeroes(vector<int> &nums) {
    int n = nums.size();
    int idx = 0;
    for(int i = 0;i < n;i++){
        if(nums[i] != 0){
            swap(nums[idx], nums[i]);
            idx++;   
        }
    }
}