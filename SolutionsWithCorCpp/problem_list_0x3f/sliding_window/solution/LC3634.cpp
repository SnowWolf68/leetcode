#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int minRemoval(vector<int> &nums, int k) {
    sort(nums.begin(), nums.end());
    int n = nums.size(), r = 0, ret = 0;;
    for(int i = 0;r < n;r++){
        while(nums[r] > ((long long)k * nums[i]) && i < n){
            i++;
        }
        if(i <= r) ret = max(ret, r - i + 1);
    }
    return n - ret;
}