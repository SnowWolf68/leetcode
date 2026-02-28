#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

// Note: 时间复杂度是 n^2 级别, 因为字符串比较和substr都要O(n)的复杂度
string shortestBeautifulSubstring(string s, int k) {
	int n = s.size(), r = 0, curLen = n, curCnt = 0;
	string ret;
	for (int i = 0; r < n; r++) {
		if (s[r] == '1') curCnt++;
		while (curCnt >= k) {
			if (curCnt == k) {
				if (r - i + 1 < curLen) {
					curLen = r - i + 1;
					ret = s.substr(i, curLen);
				} else if (r - i + 1 == curLen) {
					string cur = s.substr(i, curLen);
					if (cur < ret || ret.size() == 0) { ret = cur; }
				}
			}
			if (s[i] == '1') curCnt--;
			i++;
		}
	}
	return ret;
}

int main() {

	string s = "1111111011111";
	int k = 12;
	cout << shortestBeautifulSubstring(s, k) << endl;

	return 0;
}