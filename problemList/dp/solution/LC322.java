package problemList.dp.solution;

import java.util.*;

/**
dp[i][j] 表示考虑[0, i]区间的硬币, 凑成j的金额, 此时所需要的最少硬币个数
    dp[i][j]: 针对下标为i的硬币, 此时有一下几种选择:
        1. 不选: dp[i][j] = dp[i - 1][j];
        2. 选一个或多个(优化后): dp[i][j] = dp[i][j - coins[i]] + 1;
    对于上面几种选择, 只需要取一个min即可
初始化: 对i这个维度进行初始化, 添加一行辅助节点, 第一行此时意味着没有任何硬币, 那么此时dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return dp[n][amount] >= INF ? -1 : dp[n][amount];
 */
public class LC322 {
    public int coinChange(int[] coins, int amount) {
        int n = coins.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][amount + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= amount;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - coins[i - 1] >= 0) dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i - 1]] + 1);
            }
        }
        return dp[n][amount] >= INF ? -1 : dp[n][amount];
    }
}
