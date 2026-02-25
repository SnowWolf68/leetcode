#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

/**
sliding_window
 */
int longestAlternatingSubarray(vector<int> &nums, int threshold) {
	int n = nums.size(), idx = 0, res = 0;
	for (int i = 0; i < n; i++) {
		if (nums[i] % 2 != 0)
			continue;
		if(idx < i) idx = i;
		while (idx < n && nums[idx] <= threshold &&
			   (idx == i || nums[idx] % 2 != nums[idx - 1] % 2)) {
			idx++;
		}
		res = max(res, idx - i);
	}
	return res;
}

int main() {
	vector<int> v = {3, 2, 5, 4};
	int threshold = 5;
	cout << longestAlternatingSubarray(v, threshold) << endl;
}