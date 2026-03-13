#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int countPairs(vector<int> &nums, int target) {
	int n = nums.size();
	sort(nums.begin(), nums.end());
	int i = 0, j = n - 1, cnt = 0;
	while (i < j) {
		if (nums[i] + nums[j] >= target) {
			j--;
		} else if (nums[i] + nums[j] < target) {
			cnt += j - i;
			i++;
		}
	}
	return cnt;
}

int main() {
	vector<int> nums = {-1, 1, 2, 3, 1}; // 3
	int target = 2;
	cout << countPairs(nums, target) << endl;

	return 0;
}