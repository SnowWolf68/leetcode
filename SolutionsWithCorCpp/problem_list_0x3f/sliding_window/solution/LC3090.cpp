#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int LC3_variable(string s, int k){
    int n = s.size(), r = 0, res = 0;
    int hash[256] = {0};
    for(int i = 0;r < n;r++){
        hash[s[r]]++;
        while(hash[s[r]] > k && i < n){
            hash[s[i]]--;
            i++;
        }
        if(i <= r) res = max(res, r - i + 1);
    }
    return res;
}

int maximumLengthSubstring(string s) {
    return LC3_variable(s, 2);
}