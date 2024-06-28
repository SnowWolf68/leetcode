package solution;

/**
比较容易想到的一种解法就是直接dfs
 */
public class LC576_1 {
    private int[] dx = {1, 0, -1, 0};
    private int[] dy = {0, 1, 0, -1};
    private int MOD = (int)1e9 + 7;
    private int m, n;
    private int[][][] memo;
    public int findPaths(int _m, int _n, int maxMove, int startRow, int startColumn) {
        m = _m; n = _n;
        memo = new int[m][n][maxMove + 1];
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                for(int k = 0;k <= maxMove;k++){
                    memo[i][j][k] = -1;
                }
            }
        }
        return dfs(startRow, startColumn, maxMove);
    }

    // x, y: 当前位置, curMove: 走到当前位置需要的步数
    // return: 从当前位置(i, j)走到棋盘外面的路径数
    public int dfs(int x, int y, int curMove){
        if(x < 0 || y < 0 || x >= m || y >= n){
            return 1;
        }
        if(memo[x][y][curMove] != -1){
            return memo[x][y][curMove];
        }
        int cnt = 0;
        for(int i = 0;i < 4;i++){
            int nx = x + dx[i], ny = y + dy[i];
            // 注意这里是 >= 0, 因为curMove - 1得到的是走到下一个格子此时剩余的步数
            if(curMove - 1 >= 0) cnt = (cnt + dfs(nx, ny, curMove - 1)) % MOD;
        }
        memo[x][y][curMove] = cnt;
        return cnt;
    }
}
