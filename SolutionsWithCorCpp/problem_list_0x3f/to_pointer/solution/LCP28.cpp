#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int purchasePlans(vector<int> &nums, int target) {
    int MOD = (int)1e9 + 7;
    int n = nums.size();
    int i = 0, j = n - 1, ret = 0;
    sort(nums.begin(), nums.end());
    while(i < j){
        if(nums[i] + nums[j] > target){
            j--;
        }else{
            ret = (ret + j - i) % MOD;
            i++;
        }
    }
    return ret;
}