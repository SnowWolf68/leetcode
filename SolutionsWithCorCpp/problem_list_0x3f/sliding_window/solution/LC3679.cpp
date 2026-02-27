#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int minArrivalsToDiscard(vector<int> &nums, int w, int m) {
	unordered_map<int, int> map;
	int n = nums.size(), res = 0;
	for (int i = 0; i < w; i++) {
		map[nums[i]]++;
		if (map[nums[i]] > m && nums[i] != 0) {
			map[nums[i]]--;
			res++;
			nums[i] = 0;
		}
	}
	int r = w;
	map[nums[0]]--;
	for (int i = 1; r < n; i++, r++) {
		map[nums[r]]++;
		if (map[nums[r]] > m && nums[r] != 0) {
			map[nums[r]]--;
			nums[r] = 0;
			res++;
		}
        map[nums[i]]--;
	}
    return res;
}

int main(){

    vector<int> nums = {8,8,8,1,7,4,3,7,5,2};
    int w = 7, m = 1;
    cout << minArrivalsToDiscard(nums, w, m) << endl;

    return 0;
}