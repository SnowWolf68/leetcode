#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
进行n次两数之和
2765ms
 */
vector<vector<int>> threeSum(vector<int> &nums) {
	int n = nums.size();
	vector<vector<int>> ret;
    sort(nums.begin(), nums.end());
	for (int i = 0; i < n; i++) {
        if(i != 0 && nums[i] == nums[i - 1]) continue;
		int target = -nums[i];
		unordered_set<int> set;
        unordered_set<int> retSet;
		for (int j = i + 1; j < n; j++) {
			if (set.count(target - nums[j]) && !retSet.count(nums[j])) {
				ret.push_back({nums[i], -nums[i] - nums[j], nums[j]});
                retSet.insert(nums[j]);
			}
			set.insert(nums[j]);
		}
	}
	return ret;
}