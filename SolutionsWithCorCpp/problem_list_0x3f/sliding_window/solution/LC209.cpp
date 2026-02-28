#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int minSubArrayLen(int target, vector<int> &nums) {
	int n = nums.size(), r = 0, ret = INT_MAX;
	long long curSum = 0;
	for (int i = 0; r < n; r++) {
		curSum += nums[r];
		while (curSum >= target && i < n) {
			if (i <= r) ret = min(ret, r - i + 1);
			curSum -= nums[i];
			i++;
		}
	}
	return ret == INT_MAX ? 0 : ret;
}