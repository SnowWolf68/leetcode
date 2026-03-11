#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

bool judgeSquareSum(int c) {
	int i = 0, j = sqrt(c);
	while (i <= j) {
		int cur = i * i + j * j;
		if (cur == c) {
			return true;
		} else if (cur < c) {
			i++;
		} else {
			j--;
		}
	}
	return false;
}