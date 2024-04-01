package problemList.dp.solution;

import java.util.*;

/**
显然长度的优先级 > 数字大小的优先级
因此考虑首先计算长度, 然后构造
dp[i][j] 表示考虑[0 + 1, i + 1]区间的数字(对应cost[0, i]区间), 成本和为j, 此时的最长长度
    dp[i][j]: 针对(i + 1)这个数字, 此时有多种选择: 
        1. 不选: dp[i][j] = dp[i - 1][j];
        2. 选一个/多个(优化后): dp[i][j] = dp[i][j - cost[i]] + 1;
    对于上面两种情况, 取一个max即可
初始化: 初始化i这个维度, 添加一行辅助节点, 此时意味着没有任何数字, 此时成本只能是0, 初始化dp[0][0] = 0, 其余位置都是非法, 初始化为-INF

考虑如何按照数字大小构造最后结果: 
显然是按照数字大小从大到小构造, 对于dp[i][j]来说, 就是按照i从大到小构造
注: 在下面的分析中, i这个下标我都是在 添加了辅助节点 的基础上进行分析
从dp[n][target]开始, 考虑i这个数字是否使用, 以及使用了多少次
具体的: 如果dp[i][j] == dp[i][j - cost[i - 1]], 那么说明i用了多次, 那么先减去一次, i不变, j -= cost[i - 1]
        如果dp[i][j] == dp[i - 1][j], 说明i没有继续使用, 那么j不变, i--;
直到dp[i][j] == 0为止
注意: 由于当dp[i][j] < 0 (即dp[i][j] == -INF) 时, 此时dp[i][j]是非法位置, 所以循环条件需要写成while(dp[i][j] > 0)
 */
public class LC1449 {
    public String largestNumber(int[] cost, int target) {
        int n = cost.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][target + 1];
        Arrays.fill(dp[0], -INF);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= target;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - cost[i - 1] >= 0) dp[i][j] = Math.max(dp[i][j], dp[i][j - cost[i - 1]] + 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = n, j = target;
        while(dp[i][j] > 0){
            if(i - 1 >= 0 && j - cost[i - 1] >= 0 && dp[i][j] == dp[i][j - cost[i - 1]] + 1){
                sb.append(i);
                j -= cost[i - 1];
            }else{
                i--;
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
