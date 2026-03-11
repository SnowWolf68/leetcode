#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int minimumLength(string s) {
	int n = s.size();
	int i = 0, j = n - 1;
    // 前后缀不能有交集, 因此这里是不等号, 
    // 当i == j时, 此时无法再进行删除
	while (i < j) {
		if (s[i] != s[j]) { break; }
		char ch = s[i];
        // 如果某一次能全部删干净, 那此时就能自由分配前后缀
        // (任意分其中若干个当前缀, 若干个当后缀)
        // 因此这里可以写等于号
		while (i <= j && s[i] == ch)
			i++;
		while (i <= j && s[j] == ch)
			j--;
	}
	return j - i + 1;
}

int main() {
	string s = "cabaabac";
	cout << minimumLength(s) << endl;

	return 0;
}