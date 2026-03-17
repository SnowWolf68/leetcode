#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
前后缀分解: 
    考虑每一个i的正上方能接多少水: min(i左边的最大值, i右边的最大值)
        即: min([0, i]区间的最大值pre[i], [i, n - 1]区间的最大值suf[i])
    其中pre[]和suf[]均可以通过一次遍历 (dp) 计算出来
 */

int trap(vector<int> &height) {
    int n = height.size();
    // int pre[n], suf[n];      // C++也不允许
    vector<int> pre(n, 0), suf(n, 0);
    pre[0] = height[0];
    suf[n - 1] = height[n - 1];
    for(int i = 1;i < n;i++){
        pre[i] = max(pre[i - 1], height[i]);
    }
    for(int i = n - 2;i >= 0;i--){
        suf[i] = max(suf[i + 1], height[i]);
    }
    int ret = 0;
    for(int i = 0;i < n;i++){
        ret += min(pre[i], suf[i]) - height[i];
    }
    return ret;
}

/**
考虑这样一种场景: 假设我们知道了[0, l]区间的最大值lmax, 以及[r, n - 1]区间的最大值rmax
    如果此时有lmax < rmax, 那么对于 l + 1 位置来说, 其能接的水量就会受限于lmax, 即其能接的水量为 lmax - height[l + 1]
        那么接下来继续移动 l 指针, 这就有双指针的样子了
    如果lmax > rmax, 同理

在代码实现中, 由于0和n - 1位置一定接不到水, 因此让l, r初始化为0, n - 1
    每次循环中, l和r位置都认为是处理过的位置(更新过ret), 只需要对l, r位置计算lmax, rmax
    然后根据情况用 l + 1 或 r - 1 位置累加ret即可
 */
int trap2(vector<int> &height) {
    int n = height.size(), l = 0, r = n - 1, lmax = 0, rmax = 0, ret = 0;
    while(l < r){
        lmax = max(lmax, height[l]);
        rmax = max(rmax, height[r]);
        if(lmax < rmax){
            ret += max(0, lmax - height[l + 1]);
            l++;
        }else{
            ret += max(0, rmax - height[r - 1]);
            r--;
        }
    }
    return ret;
}

int main(){

    return 0;
}