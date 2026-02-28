#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;


// 由于所有的vec中pair的second存的是下标, 一定非负,
// 所以可以使用数组代替unordered_map
// 核心: 如何把O(3500)的常数优化掉 -- 提示: 灵神题解
vector<int> smallestRange(vector<vector<int>> &nums) {
	int k = nums.size();
	unordered_map<int, vector<int>> map;
	for (int i = 0; i < nums.size(); i++) {
		for (int x : nums[i]) {
			map[x].push_back(i);
		}
	}
	vector<pair<int, vector<int>>> vec;
	for (auto &kv : map) {
		vec.push_back(kv);
	}
	sort(vec.begin(), vec.end(),
		 [](pair<int, vector<int>> &x, pair<int, vector<int>> &y) {
			 return x.first < y.first;
		 });

	int n = vec.size(), r = 0, retL = vec[0].first, retR = vec[n - 1].first,
		ret = retR - retL + 1;
	int cnt[3505] = {0}, fullCnt = 0;
	for (int i = 0; r < n; r++) {
		for (auto &x : vec[r].second) {
			if (cnt[x] == 0) fullCnt++;
			cnt[x]++;
		}
		while (fullCnt == k && i < n) {
			if (vec[r].first - vec[i].first + 1 < ret) {
				retL = vec[i].first;
				retR = vec[r].first;
				ret = retR - retL + 1;
			}
			for (auto &x : vec[i].second) {
				cnt[x]--;
				if (cnt[x] == 0) fullCnt--;
			}
			i++;
		}
	}
	return {retL, retR};
}

int main() {
	vector<vector<int>> nums = {
		{4, 10, 15, 24, 26}, {0, 9, 12, 20}, {5, 18, 22, 30}};
	vector<int> ret = smallestRange(nums);
	cout << ret[0] << " " << ret[1] << endl;
}