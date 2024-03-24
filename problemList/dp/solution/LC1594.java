package problemList.dp.solution;

public class LC1594 {
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length, MOD = (int)1e9 + 7;
        long[][] f = new long[m + 1][n + 1], g = new long[m + 1][n + 1];
        f[0][1] = 1; g[0][1] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
               f[i][j] = Math.max(Math.max(f[i - 1][j] * grid[i - 1][j - 1], f[i][j - 1] * grid[i - 1][j - 1]), Math.max(g[i - 1][j] * grid[i - 1][j - 1], g[i][j - 1] * grid[i - 1][j - 1]));
               g[i][j] = Math.min(Math.min(f[i - 1][j] * grid[i - 1][j - 1], f[i][j - 1] * grid[i - 1][j - 1]), Math.min(g[i - 1][j] * grid[i - 1][j - 1], g[i][j - 1] * grid[i - 1][j - 1]));
            }
        }
        boolean zero = false;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(grid[i][j] == 0) zero = true;
            }
        }
        return (int)((f[m][n] == 0 && !zero) ? -1 : f[m][n] % MOD);
    }
}
