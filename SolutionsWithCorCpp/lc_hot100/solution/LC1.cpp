#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

vector<int> twoSum(vector<int> &nums, int target) {
    int n = nums.size();
    unordered_map<int, int> hashMap;
    for(int i = 0;i < n;i++){
        auto it = hashMap.find(target - nums[i]);
        if(it != hashMap.end()){
            return {it->second, i};
        }
        hashMap.insert(make_pair(nums[i], i));
    }
    return {};
}

int main(){
    vector<int> nums = {2,7,11,15};
    int target = 9;
    vector<int> ret = twoSum(nums, target);
    for(auto& x : ret){
        cout << x << " ";
    }
    cout << endl;

    return 0;
}