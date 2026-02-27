#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int maxSubarrayLength(vector<int> &nums, int k) {
    int n = nums.size(), ret = 0, r = 0;
    unordered_map<int, int> map;
    for(int i = 0;r < n;r++){
        map[nums[r]]++;
        while(map[nums[r]] > k && i < n){
            map[nums[i]]--;
            i++;
        }
        if(i <= r) ret = max(ret, r - i + 1);
    }
    return ret;
}