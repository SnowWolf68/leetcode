package problemList.dp.solution;

public class LC1289 {
    public int minFallingPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m + 1][n + 2];
        for(int i = 0;i <= m;i++){
            dp[i][0] = Integer.MAX_VALUE;
            dp[i][n + 1] = Integer.MAX_VALUE;
        }
        int ret = Integer.MAX_VALUE;
        if(m == 1){
            for(int i = 0;i < n;i++) ret = Math.min(ret, grid[0][i]);
            return ret;
        }
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = 1;k <= n;k++){
                    if(k != j){
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + grid[i - 1][j - 1]);
                    }
                }
                if(i == m) ret = Math.min(ret, dp[i][j]);
            }
        }
        return ret;
    }
}
