#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <stack>
using namespace std;

/**
从左到右, 栈中维护的是 还没有确定 下一个更大位置 的元素的下标

对于相同元素的处理: 题目中要求的是 下一个更高 的温度, 并且栈中维护的是 还没有找到下一个更高温度 的下标
因此对于相同的元素, 应该都要入栈, 此时栈中可以有相同的元素
 */
vector<int> dailyTemperatures(vector<int> &nums) {
    int n = nums.size();
    vector<int> ret(n, 0);
    stack<int> st;  // 存下标
    for(int i = 0;i < n;i++){
        while(!st.empty() && nums[i] > nums[st.top()]){
            ret[st.top()] = i - st.top();
            st.pop();
        }
        st.push(i);
    }
    return ret;
}

/**
从右到左, 栈中维护的是 当前右边下一个更大元素的下标

对于相同元素的处理: 题目中要求的是 下一个更高 的温度, 但是栈中维护的是 下一个更高元素的下标
    如果遇到了相同的元素, 那么对于前面更小的元素来说, 下一个更高的元素就应该是刚才新遍历到的这个元素
    因此此时栈中就不能有相同的元素
 */
vector<int> dailyTemperatures2(vector<int> &nums) {
    int n = nums.size();
    vector<int> ret(n, 0);
    stack<int> st;  // 存下标
    for(int i = n - 1;i >= 0;i--){
        while(!st.empty() && nums[i] >= nums[st.top()]){
            st.pop();
        }
        if(!st.empty()) ret[i] = st.top() - i;
        st.push(i);
    }
    return ret;
}