#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
还有一种三指针的做法

先排序, 从左到右枚举nums[i], 那么此时位于[lower - nums[i], upper -
nums[i]]区间的元素个数 就是此时其中一个端点为nums[i]时的所有数对个数

由于nums[i]从小到大枚举, 那么显然[lower - nums[i], upper -
nums[i]]区间的两个端点也是递减的 那么可以初始化区间为[l, r] = [n - 1, n - 1],
并且随着nums[i]的增大而往左移动

实现细节: 
    1. 为了写起来简单, 这里[l, r]改成(l, r]
    2. 如果r跑到了i或者i的左边, 那么此时再往后移动i, 也不会有合法区间了, 可以break
    3. 如果l跑到了i的左边, 此时只要r仍然有i < r, 那么仍然可以有合法区间, 
        只不过此时区间左端点变成了i + 1, 而不是l + 1 
            (由于前面计算l时取的开区间, 所以这里合法区间的左端点应该是l + 1)
 */
long long countFairPairs(vector<int> &nums, int lower, int upper) {
	int n = nums.size();
	int l = n - 1, r = n - 1;
	long long ret = 0;
	sort(nums.begin(), nums.end());
	for (int i = 0; i < n; i++) {
		while (l - 1 >= 0 && nums[l] >= lower - nums[i])
			l--; // (l, ..
		while (r - 1 >= 0 && nums[r] > upper - nums[i])
			r--; // .., r]
		if (i < r) {
			ret += r - max(i, l);
		} else {
			break;
		}
	}
	return ret;
}

int main() {
	// vector<int> nums = {0, 1, 7, 4, 4, 5};  // 6
	// vector<int> nums = {1,7,9,2,5};  // 1
	vector<int> nums = {0, 0, 0, 0, 0, 0};  // 15
	// int lower = 3, upper = 6;
	// int lower = 11, upper = 11;
	int lower = 0, upper = 0;
	cout << countFairPairs(nums, lower, upper) << endl;

	return 0;
}