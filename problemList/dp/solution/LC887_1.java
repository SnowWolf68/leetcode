package problemList.dp.solution;

import java.util.Arrays;

/**
如何优化? 
    
 */
public class LC887_1 {
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        int INF = 0x3f3f3f3f;
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                for(int p = 1;p <= j;p++){
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][p - 1] + 1, dp[i][j - p] + 1));
                }
            }
        }
        return dp[k][n];
    }
}
