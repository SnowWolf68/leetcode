#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

vector<int> sortedSquares(vector<int> &nums) {
	vector<int> ret;
	int n = nums.size();
	int i = 0, j = n - 1;
	while (i <= j) {
		int abs_mx = max(abs(nums[i]), abs(nums[j]));
		if (abs(nums[i]) == abs_mx) i++;
		else j--;
		ret.push_back(abs_mx * abs_mx);
	}
	reverse(ret.begin(), ret.end());
	return ret;
}

int main() {
	vector<int> nums = {-4, -1, 0, 3, 10};
	vector<int> ret = sortedSquares(nums);
	for (int x : ret) {
		cout << x << " ";
	}
	cout << endl;

	return 0;
}