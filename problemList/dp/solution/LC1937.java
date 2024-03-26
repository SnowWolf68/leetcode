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
-> 由于上面两种情况的统计, 当i确定时, 我们可以通过两次O(n)的遍历, 就可以填写出preMax[], sufMax[]两个数组, 因此我们可以在处理当前第j行之前预处理出来
    具体的: 使用preMax[j] 表示j左侧的preJ中, dp[i][preJ] + preJ的最大值
            使用sufMax[j] 表示j右侧(包括preJ == j的情况)的preJ中, dp[i][preJ] - preJ的最大值
    如果能够在填写dp[i]这一行之前, 预处理出preMax[], sufMax[]两个数组, 那么对于dp[i][j]的更新可以简化为: dp[i][j] = Math.max(preMax[j] - j + points[i][j], sufMax[j] + j + points[i][j]);
    如何计算preMax[j], sufMax[j]? -- 正序遍历, 倒序遍历

    同时, 为了更新preMax, sufMax方便, 这里我将preMax的范围改成 "j左侧(包括j)"
 */
public class LC1937 {
    public long maxPoints(int[][] points) {
        int m = points.length, n = points[0].length;
        long[][] dp = new long[m][n];
        for(int i = 0;i < n;i++) dp[0][i] = points[0][i];
        long[] preMax = new long[n], sufMax = new long[n];
        // 使用dp表第一行的数据填写preMax[], sufMax[]
        preMax[0] = dp[0][0];
        for(int j = 1;j < n;j++) preMax[j] = Math.max(preMax[j - 1], dp[0][j] + j);
        sufMax[n - 1] = dp[0][n - 1] - (n - 1);
        for(int j = n - 2;j >= 0;j--) sufMax[j] = Math.max(sufMax[j + 1], dp[0][j] - j);
        for(int i = 1;i < m;i++){
            for(int j = 0;j < n;j++){
                dp[i][j] = Math.max(preMax[j] - j + points[i][j], sufMax[j] + j + points[i][j]);
                // 更新preMax
                if(j != 0) preMax[j] = Math.max(preMax[j - 1], dp[i][j] + j);
                else preMax[j] = dp[i][0];
            }
            // 倒序更新sufMax
            sufMax[n - 1] = dp[i][n - 1] - (n - 1);
            for(int j = n - 2;j >= 0;j--) sufMax[j] = Math.max(sufMax[j + 1], dp[i][j] - j);
        }
        long ret = Integer.MIN_VALUE;
        for(int i = 0;i < n;i++) ret = Math.max(ret, dp[m - 1][i]);
        return ret;
    }
}
