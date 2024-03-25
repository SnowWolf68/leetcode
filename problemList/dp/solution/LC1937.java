package problemList.dp.solution;

/**
dp[i][j] = dp[i - 1][preJ] + Math.abs(preJ - j) + points[i][j];
O(m * n ^ 2)的做法很显然, 但是复杂度显然太高, 考虑如何优化

dp[i][j] = Math.max(dp[i][j], dp[i - 1][preJ] - Math.abs(preJ - j) + points[i][j])
-> 去绝对值号: 
    dp[i][j] = Math.max(dp[i][j], dp[i - 1][preJ] - (preJ - j) + points[i][j]), preJ > j
    dp[i][j] = Math.max(dp[i][j], dp[i - 1][preJ] + (preJ - j) + points[i][j]), preJ <= j
-> 将包含preJ的部分放在一起: 
    dp[i][j] = Math.max(dp[i][j], dp[i - 1][preJ] - preJ + j + points[i][j]), preJ > j
    dp[i][j] = Math.max(dp[i][j], dp[i - 1][preJ] + preJ - j + points[i][j]), preJ <= j
-> 观察上面两个式子, 找dp[i][j]的更新规律:
    对于preJ > j的这种情况, 我们只需要统计j右侧的dp[i - 1][preJ] - preJ的最大值max1, 然后使用max1 + j + points[i][j]来更新dp[i][j]即可
    对于preJ <= j的这种情况, 我们只需要统计j左侧(包含j)的dp[i - 1][preJ] + preJ的最大值max2, 然后使用max2 - j + points[i][j]来更新dp[i][j]即可
-> 由于上面两种情况的统计
 */
public class LC1937 {
    public long maxPoints(int[][] points) {
        int m = points.length, n = points[0].length;
        long[][] dp = new long[m][n];
        for(int i = 0;i < n;i++) dp[0][i] = points[0][i];
        for(int i = 1;i < m;i++){
            for(int j = 0;j < n;j++){
                for(int preJ = 0;preJ < n;preJ++){
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][preJ] - Math.abs(preJ - j) + points[i][j]);
                }
            }
        }
        long ret = Integer.MIN_VALUE;
        for(int i = 0;i < n;i++) ret = Math.max(ret, dp[m - 1][i]);
        return ret;
    }
}
