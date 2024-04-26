package problemList.dp.solution;

/**
dp[i] 表示考虑[0, i]区间的问题, 并且此时解决下标为i的问题, 此时得到的最大分数
    dp[i]: 枚举上一个解决的问题的下标j, 需要满足j + qs[j][1] + 1 <= i
        dp[i] = dp[j] + qs[i][0];
    特别的: 如果不存在符合要求的j, 那么dp[i] = qs[i][0];
    对于所有可能的j, 只需要取一个max即可
初始化: 这里能够保证j不越界, 因此不需要进行初始化
return max(dp[i]);

代码实现中, 我们可以首先遍历j找dp[j]的最大值, 然后在j的循环结束之后, 令dp[i] += qs[i][0], 这样就能够解决j不存在的问题

时间复杂度: O(n ^ 2)
这题的数据范围给到了1e5, n ^ 2的复杂度显然超时, 需要进行优化
 */
public class LC2140_TLE_1 {
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        int[] dp = new int[n];
        int ret = 0;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < i;j++){
                if(j + questions[j][1] + 1 <= i) dp[i] = Math.max(dp[i], dp[j]);
            }
            dp[i] += questions[i][0];
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
}
