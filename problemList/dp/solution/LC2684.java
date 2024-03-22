package problemList.dp.solution;

public class LC2684 {
    public int maxMoves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        int ret = 0;
        for(int j = 1;j < n;j++){
            // cur记录当前列的最大移动次数
            int cur = 0;
            for(int i = 0;i < m;i++){
                // 初始化为-INF是为了保证起点只能是最左边的这一列点
                dp[i][j] = Integer.MIN_VALUE;
                if(i - 1 >= 0 && grid[i - 1][j - 1] < grid[i][j]) dp[i][j] = dp[i - 1][j - 1] + 1;
                if(grid[i][j - 1] < grid[i][j]) dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] + 1);
                if(i + 1 < m && grid[i + 1][j - 1] < grid[i][j]) dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + 1);
                cur = Math.max(cur, dp[i][j]);
            }
            ret = Math.max(ret, cur);
        }
        return ret;
    }
}