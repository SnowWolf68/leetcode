package problemList.dp.solution;

/**
这题就老老实实(i1, j1, i2, j2)吧
但是由于每一个人都有三种选择, 因此这里就不能直接硬取max, 只能遍历
初始化: 这里由于不涉及 "障碍" , 因此辅助节点初始化为0或-INF均可
时间复杂地O(m ^ 2 * n ^ 2) t了

优化: 由于两个人每次都是向下移动一行, 因此i1 == i2, 可以把i1, i2合并
时间复杂度: O(m * n ^ 2)
 */
public class LC1463 {
    private int[] dj = new int[]{-1, 0, 1};
    public int cherryPickup(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m + 1][n + 1][n + 1];
        for(int i1 = m - 1;i1 >= 0;i1--){
            for(int j1 = n - 1;j1 >= 0;j1--){
                int i2 = i1;
                for(int j2 = n - 1;j2 >= 0;j2--){
                    for(int k1 = 0;k1 < 3;k1++){
                        int nj1 = j1 + dj[k1];
                        if(nj1 < 0 || nj1 >= n) continue;
                        for(int k2 = 0;k2 < 3;k2++){
                            int nj2 = j2 + dj[k2];
                            if(nj2 < 0 || nj2 >= n) continue;
                            dp[i1][j1][j2] = Math.max(dp[i1][j1][j2], dp[i1 + 1][nj1][nj2] + grid[i1][j1] + (i1 == i2 && j1 == j2 ? 0 : 1) * grid[i2][j2]);
                        }
                    }
                }
            }
        }
        return dp[0][0][n - 1];
    }
}
