#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int triangleNumber(vector<int> &nums) {
	int n = nums.size();
	sort(nums.begin(), nums.end(), [](int a, int b) { return a > b; });
    int ret = 0;
    for(int k = 0;k < n;k++){
        int i = k + 1, j = n - 1;
        while(i < j){
            if(nums[i] + nums[j] > nums[k]){
                ret += j - i;
                i++;
            }else if(nums[i] + nums[j] <= nums[k]){
                j--;
            }
        }
    }
    return ret;
}