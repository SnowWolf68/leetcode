#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int minimumRecolors(string blocks, int k) {
	int n = blocks.size(), cur = 0;
	// 处理[0, k]
	for (int i = 0; i < k; i++) {
		if (blocks[i] == 'B') cur++;
	}
	int r = k, ret = cur;
	for (int i = 1; r < n; i++) {
		if (blocks[r] == 'B') cur++;
		if (blocks[i - 1] == 'B') cur--;
		ret = max(ret, cur);
		r++;
	}
	return k - ret;
}

int main() {
	string blocks = "WBWBBBW";
	int k = 2;
	cout << minimumRecolors(blocks, k) << endl;
}