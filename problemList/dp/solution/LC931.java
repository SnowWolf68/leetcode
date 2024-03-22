package problemList.dp.solution;

/**
dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j + 1])) + matrix[i][j];
 */
public class LC931 {
    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 2];
        for(int i = 0;i <= m;i++){
            dp[i][0] = Integer.MAX_VALUE;
            dp[i][n + 1] = Integer.MAX_VALUE;
        }
        int ret = Integer.MAX_VALUE;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j + 1])) + matrix[i - 1][j - 1];
                if(i == m) ret = Math.min(ret, dp[i][j]);
            }
        }
        return ret;
    }
}
