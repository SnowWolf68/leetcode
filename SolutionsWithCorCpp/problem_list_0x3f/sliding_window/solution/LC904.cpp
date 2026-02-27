#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

int totalFruit(vector<int> &nums) {
    int n = nums.size(), r = 0, cur = 0, ret = 0;
    unordered_map<int, int> map;
    for(int i = 0;r < n;r++){
        map[nums[r]]++;
        cur++;
        while(map.size() > 2 && i < n){
            map[nums[i]]--;
            cur--;
            if(map[nums[i]] == 0) map.erase(nums[i]);
            i++;
        }
        if(i <= n) ret = max(ret, cur);
    }
    return ret;
}