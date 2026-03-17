#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

/**
买入分数一定是从最小的令牌开始, 卖出分数一定是从最大的令牌开始
因此先排序, 然后从两端分别开始买入和卖出

1. 先买还是先卖?
    初始分数为0, 只能先买分数, 不能先卖分数
2. 每次买/卖几个?
    1. 由于目的是得到最大的分数, 并且分数越多, 就越能够卖掉分数获得能量
        因此贪心的认为, 每次只要能买分数, 就一直买, 直到能量不够买不了为止
    2. 因为卖分数会减少当前的分数, 因此每次只卖一个分数
        如果一次卖多个分数, 可能会导致本来卖掉一个分数就可以买剩下所有的分数, 但是这里多卖了, 所以就导致能买到的分数减少
        进而就获取不到最大值
3. 怎么循环下来?
    每次先买多个, 再卖一个, 再买多个, ...
4. 什么时候获取最大值?  
    1. 买入分数之后, 如果所有令牌都处理完了, 那么此时就是最大值
    2. 如果只剩下一个令牌, 并且此时要卖出分数, 那么就没必要卖分数了, 此时的分数就是答案
5. Note: 如果一开始就买不了分数, 那么无法执行下去, return 0
6. 只要一开始能买分数, 那么就一定能循环起来, 使用到所有的令牌
7. 也可以使用maxScore维护移动过程中的最大值, 不过没必要
 */

int bagOfTokensScore(vector<int> &nums, int power) {
	int n = nums.size(), score = 0, i = 0, j = n - 1;
    sort(nums.begin(), nums.end());
	while(i <= j && power >= nums[i]){       // Note: 也可以把条件加在大循环上
        while(i <= j && power >= nums[i]){
            power -= nums[i];
            i++;
            score++;
        }
        if(i <= j && score > 0){
            if(i == j){
                return score;
            }
            score--;
            power += nums[j];
            j--;
        }
    }
    return score;
}

int bagOfTokensScore2(vector<int> &nums, int power) {
	int n = nums.size(), score = 0, i = 0, j = n - 1;
    sort(nums.begin(), nums.end());
    if(nums.size() == 0 ||power < nums[0]) return 0;   // Note: 其实只要判断一开始的条件即可
	while(i <= j){                                     // Note: 循环没有额外的条件
        while(i <= j && power >= nums[i]){
            power -= nums[i];
            i++;
            score++;
        }
        if(i <= j && score > 0){
            if(i == j){
                return score;
            }
            score--;
            power += nums[j];
            j--;
        }
    }
    return score;
}