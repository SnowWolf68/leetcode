#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int equalSubstring(string s, string t, int maxCost) {
	int n = s.size(), curCost = 0, r = 0, ret = 0;
	for (int i = 0; r < n; r++) {
		curCost += abs(s[r] - t[r]);
		while (curCost > maxCost && i < n) {
			curCost -= abs(s[i] - t[i]);
			i++;
		}
		if (i <= r) ret = max(ret, r - i + 1);
	}
	return ret;
}

int main() {
	string s = "abcd";
	string t = "cdef";
	int maxCost = 3;
	cout << equalSubstring(s, t, maxCost) << endl;

	return 0;
}