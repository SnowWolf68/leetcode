package revise_problemList.no_more_than_dp;

public class LC2742_MLE {
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length, INF = 0x3f3f3f3f;
        // dp[i][j][k]: [0, i]区间的墙, 付费工人刷j个, 用时k, 此时最小花费
        int[][][] dp = new int[n + 1][n + 1][n + 1];
        for(int j = 0;j <= n;j++){
            for(int k = 0;k <= n;k++){
                dp[0][j][k] = INF;
            }
        }
        dp[0][0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= n;j++){
                for(int k = 0;k <= n;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - 1 >= 0 && k - time[i - 1] >= 0) dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j - 1][k - time[i - 1]] + cost[i - 1]);
                }
            }
        }
        int ret = INF;
        for(int j = 0;j <= n;j++){
            for(int k = 0;k <= n;k++){
                if(j + k >= n) ret = Math.min(ret, dp[n][j][k]);
            }
        }
        return ret;
    }
}
