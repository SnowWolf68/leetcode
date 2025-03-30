package revise_problemList.no_more_than_dp;

public class LC2742 {
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length, INF = 0x3f3f3f3f;
        // dp[i][j]: 考虑[0, i]区间的墙壁, 付费油漆工至少工作j时间, 此时的最少花费
        int[][] dp = new int[n + 1][((n + 1) >> 1) + 1];
        dp[0][0] = 0;
        for(int j = 1;j <= (n + 1) >> 1;j++) dp[0][j] = INF;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= (n + 1) >> 1;j++){
                dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][Math.max(0, j - time[i - 1])] + cost[i - 1]);
            }
        }
        // System.out.println("sum = " + sum + " (sum + 1) >> 1 = " + ((sum + 1) >> 1));
        // for(int i = 0;i <= n;i++){
        //     for(int j = 0;j <= (n + 1) >> 1;j++){
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        return dp[n][(n + 1) >> 1];
    }
}
