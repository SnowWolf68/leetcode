package problemList.dp.solution;

import java.util.*;
public class LC64 {
    public int minPathSum(int[][] g) {
        int m = g.length, n = g[0].length;
        int[][] dp = new int[m + 1][n + 1];
        Arrays.fill(dp[0], Integer.MAX_VALUE);
        for(int i = 2;i <= m;i++) dp[i][0] = Integer.MAX_VALUE;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + g[i - 1][j - 1];
            }
        }
        return dp[m][n];
    }
}
