package revise_problemList;

/**

 */
public class LC879_2 {
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int m = profit.length, sum = 0, INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        for(int p : profit) sum += p;
        int[][][] dp = new int[m + 1][n + 1][sum + 1];
        dp[0][0][0] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= n;j++){
                for(int k = 0;k <= sum;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - group[i - 1] >= 0 && k - profit[i - 1] >= 0) {
                        dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j - group[i - 1]][k - profit[i - 1]]) % MOD;
                    }
                }
            }
        }
        int ret = 0;
        for(int j = 0;j <= n;j++){
            for(int k = minProfit;k <= sum;k++){
                ret = (ret + dp[m][j][k]) % MOD;
            }
        }
        return ret;
    }
}
