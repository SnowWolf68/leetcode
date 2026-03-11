#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

string makeSmallestPalindrome(string s) {
    int n = s.size();
    int i = 0, j = n - 1;
    while(i < j){
        char mn = min(s[i], s[j]);
        s[i] = s[j] = mn;
        i++;
        j--;
    }
    return s;
}