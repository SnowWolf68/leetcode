package problemList.dp.solution;

public class LC2435 {
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length, MOD = (int)1e9 + 7;
        int[][][] dp = new int[m][n][k];
        int sum = 0;
        for(int i = 0;i < n;i++) {
            sum += grid[0][i];
            dp[0][i][sum % k] = 1;
        }
        sum = 0;
        for(int i = 0;i < m;i++) {
            sum += grid[i][0];
            dp[i][0][sum % k] = 1;
        }
        for(int i = 1;i < m;i++){
            for(int j = 1;j < n;j++){
                for(int p = 0;p < k;p++){
                    int cur = grid[i][j] % k;
                    int prev = (p - cur + k) % k;
                    dp[i][j][p] = (dp[i - 1][j][prev] + dp[i][j - 1][prev]) % MOD;
                }
            }
        }
        return dp[m - 1][n - 1][0];
    }
}
