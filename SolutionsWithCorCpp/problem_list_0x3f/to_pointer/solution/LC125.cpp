#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

bool isPalindrome(string s) {
    int n = s.size();
    int i = 0, j = n - 1;
    while(i < j){
        while(i < j && !isalpha(s[i]) && !isalnum(s[i])) i++;
        while(i < j && !isalpha(s[j]) && !isalnum(s[j])) j--;
        s[i] = tolower(s[i]);
        s[j] = tolower(s[j]);
        if(s[i] != s[j]) return false;
        i++;
        j--;
    }
    return true;
}