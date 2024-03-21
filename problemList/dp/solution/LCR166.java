package problemList.dp.solution;

/*
dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]) + a[i][j];
 */
public class LCR166 {
    public int jewelleryValue(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]) + a[i - 1][j - 1];
            }
        }
        return dp[m][n];
    }
}
