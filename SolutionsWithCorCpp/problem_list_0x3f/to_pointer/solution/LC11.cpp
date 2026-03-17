#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
1. 双指针如何移动?
    每次移动height[]小的那个, 因为小的这个和剩下中间的所有柱子都无法构成更大的容量
2. 何时更新最大值?
    维护全局的最大值maxRet, 每次先更新maxRet, 再移动
3. 为什么每次移动较小的那个不会导致漏解?
    就像两数之和那样, 剩下的那些范围中要么已经计算过, 要么已经被排除掉
 */

int maxArea(vector<int> &height) {
    int n = height.size(), i = 0, j = n - 1, ret = 0;
    while(i < j){
        ret = max(ret, min(height[i], height[j]) * (j - i));
        if(height[i] < height[j]){
            i++;
        }else{
            j--;
        }
    }
    return ret;
}