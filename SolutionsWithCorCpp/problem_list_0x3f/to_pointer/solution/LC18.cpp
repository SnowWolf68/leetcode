#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

vector<vector<int>> fourSum(vector<int> &nums, int target) {
	int n = nums.size();
	vector<vector<int>> ret;
	sort(nums.begin(), nums.end());
	for (int p = 0; p < n; p++) {
		if (p != 0 && nums[p] == nums[p - 1]) continue;
		for (int k = p + 1; k < n; k++) {
			if (k != p + 1 && nums[k] == nums[k - 1]) continue;
			long long t = (long long)target - nums[p] - nums[k];
			int i = k + 1, j = n - 1;
			while (i < j) {
				if (nums[i] + nums[j] > t) {
					j--;
				} else if (nums[i] + nums[j] < t) {
					i++;
				} else {
					ret.push_back({nums[p], nums[k], nums[i], nums[j]});
					while (i < j && nums[i + 1] == nums[i])
						i++;
					while (i < j && nums[j - 1] == nums[j])
						j--;
					i++;
				}
			}
		}
	}

	return ret;
}