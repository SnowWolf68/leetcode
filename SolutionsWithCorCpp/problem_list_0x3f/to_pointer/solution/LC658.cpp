#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
找到k个最接近的 --> 去掉n - k个最远的
 */
vector<int> findClosestElements(vector<int> &nums, int k, int x) {
	int n = nums.size();
	k = n - k;
	int del_cnt = 0;
	auto i = nums.begin(), j = nums.end() - 1;
	while (del_cnt < k) {
		if (abs(*i - x) > abs(*j - x)) {
			i++;
			del_cnt++;
		} else {    // 要求保留小的, 那么删的时候优先删大的
			j--;
			del_cnt++;
		}
	}
	return vector<int>(i, j + 1);
}

int main() {
	// vector<int> nums = {1, 2};
	vector<int> nums = {1, 2, 3, 4, 5};
	// int k = 1, x = 1;
	int k = 4, x = 3;
	vector<int> ret = findClosestElements(nums, k, x);
	for (int x : ret) {
		cout << x << " ";
	}
	cout << endl;

	return 0;
}