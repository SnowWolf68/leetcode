#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int comb(int a, int b) {
	int ret = 1;
	for (int i = 1; i <= b; i++) {
		ret = ret * a-- / i;
	}
	return ret;
}

int comb2(int a) { return a * (a - 1) / 2; }

int threeSumMulti(vector<int> &nums, int target) {
	int n = nums.size(), ret = 0, MOD = 1e9 + 7;
	sort(nums.begin(), nums.end());
	for (int k = 0; k < n; k++) {
		int t = target - nums[k];
		int i = k + 1, j = n - 1;
		while (i < j) {
			if (nums[i] + nums[j] == t) {
				if (nums[i] == nums[j]) {
					ret = (ret + comb(j - i + 1, 2)) % MOD;
					// ret = (ret + comb2(j - i + 1)) % MOD;
					break;
				} else {
					int lcnt = 1, rcnt = 1;
					while (i < j && nums[i + 1] == nums[i]) {
						i++;
						lcnt++;
					}
					while (i < j && nums[j - 1] == nums[j]) {
						j--;
						rcnt++;
					}
					i++;
					j--;
					ret = (ret + lcnt * rcnt) % MOD;
				}
			} else if (nums[i] + nums[j] < t) {
				i++;
			} else {
				j--;
			}
		}
	}
	return ret;
}