#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;


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
	unordered_map<int, int> cnt;
	for (int i = 0; r < n; r++) {
		for (auto &x : vec[r].second) {
			cnt[x]++;
		}
		while (cnt.size() == k && i < n) {
			if (vec[r].first - vec[i].first + 1 < ret) {
				retL = vec[i].first;
				retR = vec[r].first;
				ret = retR - retL + 1;
			}
			for (auto &x : vec[i].second) {
				cnt[x]--;
				if (cnt[x] == 0) cnt.erase(x);
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