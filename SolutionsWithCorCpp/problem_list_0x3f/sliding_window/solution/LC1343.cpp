#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int numOfSubarrays(vector<int> &arr, int k, int threshold) {
	int n = arr.size(), ret = 0, cur_sum = arr[0], r = 0;
	for (int i = 0; i < n; i++) {
		while (r + 1 < n && r - i + 1 < k) {
			r++;
			cur_sum += arr[r];
		}
		if (r - i + 1 == k && (double)(cur_sum / k) >= threshold) { ret++; }
		cur_sum -= arr[i];
	}
	return ret;
}

int main() {
	vector<int> arr = {2, 2, 2, 2, 5, 5, 5, 8};
	int k = 3, threshold = 4;
	cout << numOfSubarrays(arr, k, threshold) << endl;
}