package solution;

import java.util.Arrays;

/**
其实也是 排列 问题, 但是需要注意, 这里有可能某一个篮子中不装任何元素, 因此需要添加一个维度, 表示当前考虑[0, i]区间的篮子
dp[i][state] 表示考虑[0, i]区间的篮子, 当前nums中使用情况为state时, 此时的最大与和
dp[i][state]: 当前篮子有装或不装两种情况
    1. 不装: dp[i][state] = dp[i - 1][state];
    2. 装: 此时又分为装1个和装2个两种情况
        装1个: 假设装的元素下标为j, dp[i][state] = dp[i - 1][state & (~(1 << j))] + ((i + 1) & nums[j]);
        装2个: 假设装的元素下标分别为j, k, dp[i][state] = dp[i - 1][state & (~((1 << j) | (1 << k)))] + ((i + 1) & nums[j]) + ((i + 1) & nums[k]);
    对上面所有情况取一个max即可
初始化: 这里i - 1可能越界, 因此添加一行辅助节点, 第一行辅助节点此时意味着当前没有任何篮子, 那么显然只有dp[0][0]是合法, 因此初始化dp[0][0] = 0, 其余位置都是-INF
    需要注意的是, 这样添加辅助节点之后, i就不再是篮子的下标了, 而是 "第i个篮子" 因此状态转移方程也需要做一些改动, 需要将所有的 (i + 1) 都替换成 i
return dp[numSlots - 1][mask - 1];

时间复杂度: 状态个数: 2 ^ n * numSlots, 每个状态的计算时间 O(n ^ 2), 因此总的时间复杂度为: O(n ^ 2 * numSlots * 2 ^ n), 这个时间复杂度还是比较高的, 会T
 */
public class LC2172_TLE {
    public int maximumANDSum(int[] nums, int numSlots) {
        int n = nums.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[][] dp = new int[numSlots + 1][mask];
        Arrays.fill(dp[0], -INF);
        dp[0][0] = 0;
        for(int i = 1;i <= numSlots;i++){
            for(int state = 0;state < mask;state++){
                dp[i][state] = dp[i - 1][state];
                for(int j = 0;j < n;j++){
                    if(((state >> j) & 1) == 0) continue;
                    dp[i][state] = Math.max(dp[i][state], dp[i - 1][state & (~(1 << j))] + (i & nums[j]));
                    // 这里的k也可以从j + 1开始循环, 这样能稍微快一点, 但是总的单个状态的计算复杂度还是O(n ^ 2)
                    // 将这里k的循环起始位置从0改到j + 1之后, 下面也就不需要判断k和j是否相等了
                    for(int k = 0;k < n;k++){
                        if(k == j || ((state >> k) & 1) == 0) continue;
                        dp[i][state] = Math.max(dp[i][state], dp[i - 1][state & (~((1 << j) | (1 << k)))] + (i & nums[j]) + (i & nums[k]));
                    }
                }
            }
        }
        return dp[numSlots][mask - 1];
    }
}
