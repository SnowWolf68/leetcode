#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
要求: 做到O(n)
-- 参考灵神思路
先把所有数字放到hashSet中
遍历hashSet: (auto& x : hashSet)
    1. 如果x - 1也在哈希集合中, 那么跳过
        因为对于每一串连续的序列来说, 只需要从最小的那个元素开始, 往后拓展一次即可
        对于中间的元素不需要重复操作
    2. 如果x - 1不在哈希集合中, 那么向后进行拓展
        -> 此时x一定是某一条连续序列中最小的元素
时间复杂度: 
    虽然有两层循环, 但是每一个元素至多被访问两次, 因此时间复杂度仍然是O(n)
 */
int longestConsecutive(vector<int> &nums) {
    unordered_set<int> set;
    for(auto& x : nums){
        set.insert(x);
    }
    int mx = 0, cur = 0;
    for(auto x : set){
        if(set.count(x - 1) != 0) continue;
        else{
            cur = 1;
            while(set.count(++x) != 0){
                cur++;
            }
            mx = max(mx, cur);
        }
    }
    return mx;
}