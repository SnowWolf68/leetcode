#include <algorithm>
#include <iostream>
#include <vector>
#include <unordered_map>
using namespace std;

long long maxSum(vector<int> &nums, int m, int k) {
	int n = nums.size(), r = k;
    if(n < k) return 0;
	unordered_map<int, int> hash;
	// [0, k - 1]
	long long cur_sum = 0;
	for (int i = 0; i < k; i++) {
		cur_sum += nums[i];
		hash[nums[i]]++;
	}
	long long ret = hash.size() >= m ? cur_sum : 0;

	// ..
	for (int i = 1; r < n; i++) {
		hash[nums[r]]++;
		cur_sum += nums[r];
		hash[nums[i - 1]]--;
		cur_sum -= nums[i - 1];
        if(hash[nums[i - 1]] == 0) hash.erase(nums[i - 1]);
        if(hash.size() >= m) ret = max(ret, cur_sum);
		r++;
	}
    return ret;
}