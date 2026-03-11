#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

vector<int> twoSum(vector<int> &nums, int target) {
    int n = nums.size();
    int i = 0, j = n - 1;
    while(i < j){
        if(nums[i] + nums[j] == target){
            return {i + 1, j + 1};
        }else if(nums[i] + nums[j] < target){
            i++;
        }else{
            j--;
        }
    }
    return {};
}