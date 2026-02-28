#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int minLength(vector<int> &nums, int k) {
	int n = nums.size(), r = 0, ret = INT_MAX, curSum = 0;
	unordered_map<int, int> map;
	for (int i = 0; r < n; r++) {
		map[nums[r]]++;
		if (map[nums[r]] == 1) curSum += nums[r];
		while (curSum >= k && i < n) {
			if (i <= r) ret = min(ret, r - i + 1);
			map[nums[i]]--;
			if (map[nums[i]] == 0) curSum -= nums[i];
			i++;
		}
	}
	return ret == INT_MAX ? -1 : ret;
}