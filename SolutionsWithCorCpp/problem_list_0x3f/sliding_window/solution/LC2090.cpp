#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

vector<int> getAverages(vector<int> &nums, int k) {
	int n = nums.size(), l = 0, r = 0;
    long long cur_sum = nums[0];
	vector<int> res(n, -1);
	for (int i = 0; i < n; i++) {
		while (r + 1 < n && r - i < k) {
			r++;
			cur_sum += nums[r];
		}
		if (i - l < k) res[i] = -1;
		else if (r - i == k) {
			res[i] = cur_sum / (r - l + 1);
			cur_sum -= nums[l];
			l++;
		} else {
			break;
		}
	}
	return res;
}