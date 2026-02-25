#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

/**
grouped_loop
 */
int longestAlternatingSubarray(vector<int> &nums, int threshold) {
	int n = nums.size(), ret = 0, l = 0, r = 0;
	while (l < n) {
		if (nums[l] % 2 != 0 || nums[l] > threshold) {
			l++;
			continue;
		}
		r = l;
		while (r < n && nums[r] <= threshold &&
			   (r == l || nums[r] % 2 != nums[r - 1] % 2)) {
            r++;
		}
        ret = max(ret, r - l);
        l = r;
	}
	return ret;
}

int main() {
	// vector<int> v = {3, 2, 5, 4};
	// int threshold = 5;
	// vector<int> v = {2, 3, 4, 5};
	// int threshold = 4;
    vector<int> v = {4};
    int threshold = 1;
	cout << longestAlternatingSubarray(v, threshold) << endl;
}