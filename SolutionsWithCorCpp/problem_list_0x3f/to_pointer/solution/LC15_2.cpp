#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
进行n次 LC167 版本的两数之和

Note:
	1. LC167中题目保证答案唯一, 因此找到之后可以直接返回
		但是本题可以有多个答案, 所以找到一组值之后还需要考虑怎么移动两个指针i, j
        --- 实际上此时任意移动任何一个指针均可


	2. 本题中还要求不能有重复的, 因此需要考虑如何去重
        分为两部分: 
            1. 对于nums[k], 每次遍历的新的nums[k], 都判断其与前一个nums[k - 1]是否相同, 相同则跳过
            2. 对于nums[i], nums[j], 这里的去重比较特殊. 
                如果按照和nums[k]类似的逻辑, 即先判断后一个与前一个是否相同, 若相同则跳过, 那么这样会出问题
                正确的做法是当每次找到一个符合要求的三元组时, 再去重, 即判断接下来的 i 和 j 是否相同, 若相同则跳过
 */
vector<vector<int>> threeSum(vector<int> &nums) {
	int n = nums.size();
	vector<vector<int>> ret;
	sort(nums.begin(), nums.end());
	for (int k = 0; k < n; k++) {
		if (k != 0 && nums[k] == nums[k - 1]) continue;
		int target = -nums[k];
		int i = k + 1, j = n - 1;
		while (i < j) {
			if (nums[i] + nums[j] > target) {
				j--;
			} else if (nums[i] + nums[j] < target) {
				i++;
			} else {
				ret.push_back({nums[k], nums[i], nums[j]});
				while (i < j && nums[i + 1] == nums[i])
					i++;
				while (i < j && nums[j - 1] == nums[j])
					j--;
                i++;
			}
		}
	}
	return ret;
}

int main() {
	vector<int> nums = {1, 2, 0, 1, 0, 0, 0, 0};
	vector<vector<int>> ret = threeSum(nums);
	cout << "---------------" << endl;
	for (auto &v : ret) {
		for (int x : v) {
			cout << x << " ";
		}
		cout << endl;
	}

	return 0;
}