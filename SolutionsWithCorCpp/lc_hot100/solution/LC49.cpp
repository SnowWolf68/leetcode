#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

vector<vector<string>> groupAnagrams(vector<string> &strs) {
	unordered_map<string, vector<string>> map;
	for (auto &s : strs) {
		string sorted = s;
		sort(sorted.begin(), sorted.end(),
			 [](char a, char b) { return a < b; });
		map[sorted].push_back(s);
	}
	vector<vector<string>> ret;
	for (auto &kv : map){
        ret.push_back(kv.second);
    }
    return ret;
}