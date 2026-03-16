#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
重点: 当相等时如何处理?
	由于numa1, nums2中可能会存在重复元素,
因此当相等的时候不能简单地只移动任意一个指针
	而是需要统计两端各有多少个相同的元素, 然后单独计算这些相等元素

	特别的, 在统计相等元素的时候也需要注意:
		对于 LC15 (三数之和) 这题来说, 由于最后返回的是去重后的三元组
		因此对于两端的相等元素, 只要跳过去就好
		但是对于这题来说, 由于要统计次数,
因此需要考虑两端的元素之间有多少种组合方式 这就需要好好考虑一下:

		1. 如果nums[i]和nums[j]之间的所有元素全相等, 那么此时组合数为comb(j - i
+ 1, 2)
		2. 如果nums[i]和nums[j]之间的元素并不全相等,
那么假设nums[i]右边有lcnt个元素相等, nums[j]左边有rcnt个元素相等 那么组合数为
lcnt * rcnt
 */

/**
有问题的实现, 可能会溢出
*/
int comb(int a, int b) {
	int ret = 1;
	for (int i = 0; i < b; i++) {
		ret *= (a--);
	}
	for (int i = 1; i <= b; i++) {
		ret /= i;
	}
	return ret;
}

/**
为什么 ret = ret * n / i 可以保证整除？

这是一个非常奇妙的数学性质。

    第 1 次循环：ret = 1 * n / 1，显然是整数。
    第 2 次循环：上一轮结果是 n，这轮是 n×(n−1)/2。任意两个连续整数中必有一个是偶数，所以肯定能被 2 整除。
    第 3 次循环：结果是 n(n−1)(n−2)/(1×2×3)​。任意三个连续整数中必有一个是 3 的倍数，所以除以 3 一定是整除。
 */
int comb_ac(int a, int b){
	int ret = 1;
	for(int i = 1;i <= b;i++){
		ret = ret * a-- / i;
	}
	return ret;
}

int comb2(int n){
	return n * (n - 1) / 2;
}

int numTriplets(vector<int> &nums1, vector<int> &nums2) {
	int n = nums1.size(), m = nums2.size(), ret = 0;
	sort(nums1.begin(), nums1.end());
	sort(nums2.begin(), nums2.end());
	for (int k = 0; k < n; k++) {
		int i = 0, j = m - 1;
		while (i < j) {
			if ((long long)nums2[i] * nums2[j] == (long long)nums1[k] * nums1[k]) {
				if (nums2[i] != nums2[j]) {
					int lcnt = 1, rcnt = 1;
					while (i < j && nums2[i + 1] == nums2[i]) {
						i++;
						lcnt++;
					}
					while (i < j && nums2[j - 1] == nums2[j]) {
						j--;
						rcnt++;
					}
					i++;
					j--;
					ret += lcnt * rcnt;
				} else {
					// ret += comb(j - i + 1, 2);
					// ret += comb2(j - i + 1);
					ret += comb_ac(j - i + 1, 2);
					break;
				}
			} else if ((long long)nums2[i] * nums2[j] < (long long)nums1[k] * nums1[k]) {
				i++;
			} else {
				j--;
			}
		}
	}
	for (int k = 0; k < m; k++) {
		int i = 0, j = n - 1;
		while (i < j) {
			if ((long long)nums1[i] * nums1[j] == (long long)nums2[k] * nums2[k]) {
				if (nums1[i] != nums1[j]) {
					int lcnt = 1, rcnt = 1;
					while (i < j && nums1[i + 1] == nums1[i]) {
						i++;
						lcnt++;
					}
					while (i < j && nums1[j - 1] == nums1[j]) {
						j--;
						rcnt++;
					}
					i++;
					j--;
					ret += lcnt * rcnt;
				} else {
					// ret += comb(j - i + 1, 2);
					// ret += comb2(j - i + 1);
					ret += comb_ac(j - i + 1, 2);
					break;
				}
			} else if ((long long)nums1[i] * nums1[j] < (long long)nums2[k] * nums2[k]) {
				i++;
			} else {
				j--;
			}
		}
	}
	return ret;
}

int main() {
	// vector<int> nums1 = {1, 1};
	vector<int> nums1 = {3,1,2,2};
	// vector<int> nums2 = {1, 1, 1};
	vector<int> nums2 = {1,3,4,4};
	cout << numTriplets(nums1, nums2) << endl;

	return 0;
}