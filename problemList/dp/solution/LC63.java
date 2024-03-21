package problemList.dp.solution;

public class LC63 {
    public int uniquePathsWithObstacles(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] dp = new int[m + 1][n + 1];
        dp[0][1] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(a[i - 1][j - 1] == 0){
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m][n];
    }
}
