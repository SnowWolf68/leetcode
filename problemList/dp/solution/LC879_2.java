package problemList.dp.solution;

/**
"不超过/至少" 型背包

dp[i][j][k] 表示从[0, i]区间的工作中选, 当前使用的工人数量不超过j, 得到的利润不少于k的方案数
    dp[i][j][k]: 针对下标为i的这个工作, 当前有选或不选两种可能
        1. 选:
            需要注意: 如果是 "恰好装满" 型背包, 即dp[i][j][k] 表示从[0, i]区间的工作中选, 工人数量为j, 利润为k的方案数
            那么此时这种情况下的状态转移方程为dp[i][j][k] += dp[i - 1][j - group[i - 1]][k - profit[i - 1]];
            但是在这种 "不超过/至少" 类型的背包的状态表示下, 这里的状态转移方程需要一些改动:
            由于这里要求 "利润不少于k" 也就是说, 如果当前下标为i的这个任务的利润 > k, 那么此时选这个任务也是符合dp[i][j][k]的要求的, 选这个任务意味着前面的任务不需要有任何的利润就可以
            而如果下标为i的任务的利润 > k, 通过k - profit[i]得到的值就是负数, 因此需要对0取一个max
            但是对于 "恰好装满" 型的背包来说, 此时就不是一个合法的方案, 因为此时要求利润 正好等于 k, 而下标为i这个任务的利润已经超过了k, 所以此时dp[i][j][k]显然不能选下标为i的这个任务
            因此在 "不超过/至少" 类型的背包中, 此时的状态转移方程为dp[i][j][k] += dp[i - 1][j - group[i - 1]][Math.max(0, k - profit[i - 1])];
        2. 不选: dp[i][j][k] += dp[i - 1][j][k];
初始化: i这个维度添加一面的辅助节点, 这些辅助节点意味着此时没有任何工作, 那么此时初始化dp[0][..][0] = 1, 其余位置都是0  // ..意味着j = [0, n]区间的所有值

时间复杂度: O(m * n * minProfit)
 */
public class LC879_2 {
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int m = group.length, MOD = (int)1e9 + 7;
        int[][][] dp = new int[m + 1][n + 1][minProfit + 1];
        for(int i = 0;i <= n;i++) dp[0][i][0] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= n;j++){
                for(int k = 0;k <= minProfit;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - group[i - 1] >= 0) dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j - group[i - 1]][Math.max(0, k - profit[i - 1])]) % MOD;
                }
            }
        }
        return dp[m][n][minProfit];
    }
}
