package zuoshen.class067;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
题目6
矩阵中的最长递增路径
给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度
对于每个单元格，你可以往上，下，左，右四个方向移动
不能在对角线方向上移动或移动到边界外（即不允许环绕）
测试链接 : https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/


 */
public class Problem06_LongestIncreasingPathInAMatrix {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, -1, 0, 1};


    /**
     * TLE
     */
    public int longestIncreasingPath1(int[][] matrix) {
        int ret = 0;
        for(int i = 0;i < matrix.length;i++){
            for(int j = 0;j < matrix[0].length;j++){
                ret = Math.max(ret, dfs1(matrix, i, j, new boolean[matrix.length][matrix[0].length]));
            }
        }
        return ret + 1;
    }

    /**
     * TLE
     */
    private int dfs1(int[][] matrix, int i, int j, boolean[][] vis){
        int m = matrix.length, n = matrix[0].length;
        vis[i][j] = true;
        int ret = 0;
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx < 0 || ny < 0 || nx >= m || ny >= n || vis[nx][ny]) continue;
            if(matrix[nx][ny] <= matrix[i][j]) continue;
            ret = Math.max(ret, dfs1(matrix, nx, ny, vis) + 1);

        }
        vis[i][j] = false;
        return ret;
    }

    /**
     * 注意到由于路径要求严格递增, 因此vis数组可以省掉
     */


    /**
     * 注: 每一个位置(i, j)作为起点的dfs过程中的dp表可以共用, 时间复杂度: O(m * n)
     */
    public int longestIncreasingPath2(int[][] matrix) {
        int ret = 0, m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        for(int i = 0;i < matrix.length;i++){
            for(int j = 0;j < matrix[0].length;j++){
                ret = Math.max(ret, dfs2(matrix, i, j, dp));
            }
        }
        return ret + 1;
    }


    private int dfs2(int[][] matrix, int i, int j, int[][] dp){
        int m = matrix.length, n = matrix[0].length;
        int ret = 0;
        if(dp[i][j] != 0) return dp[i][j];
        for(int k = 0;k < 4;k++){
            int nx = i + dx[k], ny = j + dy[k];
            if(nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
            if(matrix[nx][ny] <= matrix[i][j]) continue;
            ret = Math.max(ret, dfs2(matrix, nx, ny, dp) + 1);

        }
        dp[i][j] = ret;
        return ret;
    }

    /**
     * 递推的状态依赖: mat[i][j]大的位置一定依赖与mat[i][j]小的位置
     * 因此递推顺序: 从mat[i][j]小的位置, 推到mat[i][j]大的位置
     */
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        List<int[]> list = new ArrayList<>();
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(((i - 1 < 0) || (i - 1 >= 0 && matrix[i - 1][j] <= matrix[i][j]))
                && ((j - 1 < 0) || (j - 1 >= 0 && matrix[i][j - 1] <= matrix[i][j]))
                && ((i + 1 >= m) || (i + 1 < m && matrix[i + 1][j] <= matrix[i][j]))
                && ((j + 1 >= n) || (j + 1 < n && matrix[i][j + 1] <= matrix[i][j]))){
                    // mat[i][j] 比四周的大
                    dp[i][j] = 1;
                }else list.add(new int[]{i, j});
            }
        }
        
        Collections.sort(list, (a, b) -> matrix[b[0]][b[1]] - matrix[a[0]][a[1]]);
        int ret = 1;
        for(int[] pos : list){
            int i = pos[0], j = pos[1];
            if(i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + 1);
            if(j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] + 1);
            if(i + 1 < m && matrix[i + 1][j] > matrix[i][j]) dp[i][j] = Math.max(dp[i][j], dp[i + 1][j] + 1);
            if(j + 1 < n && matrix[i][j + 1] > matrix[i][j]) dp[i][j] = Math.max(dp[i][j], dp[i][j + 1] + 1);
            ret = Math.max(ret, dp[i][j]);
        }
        return ret;
    }
}
