#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

// 只要对于选定的区间之外的元素来说, 没有一个元素的频率超过 n / 4 ,
// 那么总存在一种替换方式

int getIdx(char c) {
	if (c == 'Q') return 0;
	else if (c == 'W') return 1;
	else if (c == 'E') return 2;
	else if (c == 'R') return 3;
	else return -1;
}

bool check(int cnt[], int totCnt[], int n) {
	for (int i = 0; i < 4; i++) {
		if (totCnt[i] - cnt[i] > n / 4) return false;
	}
	return true;
}

int balancedString(string s) {
	int n = s.size();
	int totCnt[4] = {0};
	for (char c : s) {
		totCnt[getIdx(c)]++;
	}
	int cnt[4] = {0};
	int r = 0, ret = n;
	for (int i = 0; r < n; r++) {
		cnt[getIdx(s[r])]++;
		while (check(cnt, totCnt, n) && i < n) {
			ret = min(ret, r - i + 1);
			cnt[getIdx(s[i])]--;
			i++;
		}
	}
	return ret;
}