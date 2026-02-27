#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int maxConsecutiveAnswers(string s, int k) {
    int n = s.size(), ret = 0, r = 0, cnt = 0;
    // 连续T中F
    for(int i = 0;r < n;r++){
        if(s[r] == 'F') cnt++;
        while(cnt > k && i < n){
            if(s[i] == 'F') cnt--;
            i++;
        }
        if(i <= r) ret = max(ret, r - i + 1);
    }
    // 连续F中T
    cnt = 0;
    r = 0;
    for(int i = 0;r < n;r++){
        if(s[r] == 'T') cnt++;
        while(cnt > k && i < n){
            if(s[i] == 'T') cnt--;
            i++;
        }
        if(i <= r) ret = max(ret, r - i + 1);
    }
    return ret;
}