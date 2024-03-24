package problemList.dp.solution;

import java.util.*;

public class LC174 {
    public int calculateMinimumHP(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m + 1][n + 1];
        Arrays.fill(dp[m], Integer.MAX_VALUE);
        for(int i = 0;i <= m;i++) dp[i][n] = Integer.MAX_VALUE;
        dp[m][n - 1] = 1;
        for(int i = m - 1;i >= 0;i--){
            for(int j = n - 1;j >= 0;j--){
                dp[i][j] = Math.max(1, Math.min(dp[i + 1][j], dp[i][j + 1]) - grid[i][j]);
            }
        }
        return dp[0][0];
    }
}
