package problemList.dp.solution;

public class LC2304 {
    public int minPathCost(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for(int i = 0;i < n;i++) dp[0][i] = grid[0][i];
        int ret = Integer.MAX_VALUE;
        for(int i = 1;i < m;i++){
            for(int j = 0;j < n;j++){
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = 0;k < n;k++){
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + moveCost[grid[i - 1][k]][j] + grid[i][j]);
                }
                if(i == m - 1) ret = Math.min(ret, dp[i][j]);
            }
        }
        return ret;
    }
}
