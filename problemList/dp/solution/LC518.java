package problemList.dp.solution;

/**
dp[i][j] 表示考虑[0, i]区间的硬币, 凑成面额为amount, 此时的方案数
    dp[i][j]: 针对下标为i的硬币, 此时有多种可能
        1. 不选: dp[i][j] = dp[i - 1][j];
        2. 选一个或多个(优化后): dp[i][j] = dp[i][j - coins[i]];
    针对上面多种情况, 取一个和即可
初始化: 初始化i这个维度, 添加一行辅助节点, 第一行此时意味着没有任何硬币, 那么此时dp[0][0] = 1, 其余位置都是非法, 初始化为0
return dp[n - 1][amount];
 */
public class LC518 {
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= amount;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - coins[i - 1] >= 0) dp[i][j] += dp[i][j - coins[i - 1]];
            }
        }
        return dp[n][amount];
    }
}
