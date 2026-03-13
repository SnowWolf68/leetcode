#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

bool judgeSquareSum(int c) {
	int i = 0, j = sqrt(c);
	while (i <= j) {
		if (c - i * i == j * j) {   // 通过移项避免溢出
			return true;
		} else if (i * i < c - j * j) { // 同上
			i++;
		} else {
			j--;
		}
	}
	return false;
}