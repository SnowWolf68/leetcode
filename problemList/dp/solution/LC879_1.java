package problemList.dp.solution;

/**
"恰好装满" 型背包

dp[i][j][k] 表示考虑[0, i]区间的工作, 使用到的工人数量为j, 产生的利润为k, 此时的方案数
    dp[i][j][k]: 针对下标为i的工作, 有选或不选两种可能
        1. 选: dp[i][j][k] += dp[i - 1][j - group[i]][k - profit[i]];
        2. 不选: dp[i][j][k] += dp[i - 1][j][k];
    由于求的是方案数, 因此上面两种情况需要取一个和
初始化: 初始化i这个维度, 添加一面的辅助节点, 此时i == 0意味着当前没有任何工作, 此时dp[0][0][0] = 1, 其余位置都是非法, 初始化为0

时间复杂度: O(m * n * sum(profit))
 */
public class LC879_1 {
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int m = group.length, sum = 0, MOD = (int)1e9 + 7;
        for(int x : profit) sum += x;
        int[][][] dp = new int[m + 1][n + 1][sum + 1];
        dp[0][0][0] = 1;
        int ret = 0;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= n;j++){
                for(int k = 0;k <= sum;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - group[i - 1] >= 0 && k - profit[i - 1] >= 0) dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j - group[i - 1]][k - profit[i - 1]]) % MOD;
                    if(i == m && k >= minProfit) {
                        ret = (ret + dp[i][j][k]) % MOD;
                    }
                }
            }
        }
        return ret;
    }
}
