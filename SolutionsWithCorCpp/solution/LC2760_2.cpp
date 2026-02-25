#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

/**
grouped_loop
 */
int longestAlternatingSubarray(vector<int> &nums, int threshold) {
	int n = nums.size(), ret = 0, j = 0;
	for (int i = 0; i < n; i++) {
		if (nums[i] % 2 != 0)
			continue;
		j = i;
		while (j < n && nums[j] <= threshold &&
			   (j == i || (j > i && nums[j] % 2 != nums[j - 1] % 2)))
			j++;
		ret = max(ret, j - i);
		if(i != j) i = j - 1;
	}
	return ret;
}

int main() {
	// vector<int> v = {3, 2, 5, 4};
	// int threshold = 5;
	vector<int> v = {2, 3, 4, 5};
	int threshold = 4;
	cout << longestAlternatingSubarray(v, threshold) << endl;
}