#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

vector<int> getStrongest(vector<int> &nums, int k) {
	int n = nums.size();
	sort(nums.begin(), nums.end());
	int m = nums[(n - 1) / 2];
	int i = 0, j = n - 1;
	vector<int> ret;
	int cnt = 0;
	while (cnt < k) {
		if (abs(nums[i] - m) > abs(nums[j] - m) ||
			(abs(nums[i] - m) == abs(nums[j] - m) && nums[i] > nums[j])) {
			ret.push_back(nums[i]);
			i++;
		} else {
			ret.push_back(nums[j]);
			j--;
		}
		cnt++;
	}
	return ret;
}

int main() {
	// vector<int> nums = {6, 7, 11, 7, 6, 8};
	vector<int> nums = {-7,22,17,3};
	int k = 2;
	vector<int> ret = getStrongest(nums, k);
	for (int x : ret) {
		cout << x << " ";
	}
	cout << endl;

	return 0;
}