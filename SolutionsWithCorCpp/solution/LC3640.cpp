#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

long long maxSumTrionic(vector<int> &nums) {
	int n = nums.size(), l = 0, r = 0, ret = INT_MIN;
	while (l < n) {
		r = l;
		int sum = nums[l], inc1 = 0, dec = 0, inc2 = 0;
		while (r + 1 < n && nums[r + 1] > nums[r]) {
            // cout << "sum += " << nums[r] << " ";
			r++;
			sum += nums[r];
			inc1 = 1;
		}
		while (r + 1 < n && nums[r + 1] < nums[r]) {
            // cout << "sum += " << nums[r] << " ";
			r++;
			sum += nums[r];
			dec = 1;
		}
		int bottom = r;
		while (r + 1 < n && nums[r + 1] > nums[r]) {
            if(inc2 == 1 && nums[r + 1] < 0) break;
			// cout << "sum += " << nums[r] << " ";
			r++;
			sum += nums[r];
			inc2 = 1;
		}
		// sum += nums[r];
		// cout << "sum += " << nums[r] << endl;

		cout << "l = " << l << " bottom = " << bottom << " r = " << r
			 << " sum = " << sum << " inc1 = " << inc1 << " dec = " << dec
			 << " inc2 = " << inc2 << endl;
		if (inc1 + dec + inc2 == 3) {
			ret = max(ret, sum);
		}
		if (dec == 1)
			l = bottom;
		else
			l = r + 1;
	}
	return ret;
}

int main() {
	// vector<int> nums = {0, -2, -1, -3, 0, 2, -1}; // -4
	vector<int> nums = {1, 4, 2, 2, 3, 1, 2}; // 8
    // vector<int> nums = {2,993,-791,-635,-569};  // -431
    // vector<int> nums = {159,208,-920,-460,295};  // -718
	cout << maxSumTrionic(nums) << endl;
}