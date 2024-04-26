package problemList.dp.solution;

/**
第二种刷表法: 
    dp[i] 表示考虑[0, i]区间的问题, 此时能够获得的最大分数
        dp[i]: 对于下标为i的这个问题, 此时有两种选择: 选或不选
            1. 不选: 那么可以用dp[i]来刷新dp[i + 1], 即dp[i + 1] = Math.max(dp[i + 1], dp[i]);
            2. 选: 下一个可以选择的下标范围为: [i + qs[i][1] + 1, n - 1], 但是由于dp[i]定义不要求i下标的问题一定被选择
                因此每次会使用dp[i]来更新dp[i + 1], 因此我们在刷表的时候, 只需要使用dp[i]来刷新dp[i + qs[i][1] + 1]这一个位置的dp值即可, 即
                    dp[i + qs[i][1] + 1] = Math.max(dp[i + qs[i][1] + 1], dp[i] + qs[i][0]);
        初始化: 由于我们需要使用dp[i]来刷新后续的值, 因此我们还是需要保证dp[i]能够被正确填写, 这里我们不需要初始化, 只需要保证dp[0] = 0即可
            但是这里i + qs[i][1] + 1, i + 1都有可能发生越界, 因此我们需要考虑越界情况如何处理, 对于越界的情况来说, 此时意味着当前dp[i]无法刷新后面的值
            但是需要注意的是, 此时dp[i]有可能是最后的结果, 因此我们此时可以直接让dp[i] + qs[i][0], dp[i]更新到结果ret中即可
            ps: 这里灵神的处理方式是, 在dp表最后添加一个辅助节点dp[n], 这样i + 1的越界情况就不需要处理(前提i的范围是[0, n - 1]), 此时如果i + qs[i][1] + 1越界, 那么只需要刷新dp[n]即可
        return ret;

时间复杂度: 这种状态表示下, 每次dp[i]只需要刷新后面某一个位置的dp值即可, 因此时间复杂度是O(n);
 */
public class LC2140_2 {
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n];
        long ret = 0;
        for(int i = 0;i < n;i++){
            if(i + 1 < n) dp[i + 1] = Math.max(dp[i + 1], dp[i]);
            else ret = Math.max(ret, dp[i]);
            if(i + questions[i][1] + 1 < n) dp[i + questions[i][1] + 1] = Math.max(dp[i + questions[i][1] + 1], dp[i] + questions[i][0]);
            else ret = Math.max(ret, dp[i] + questions[i][0]);
        }
        return ret;
    }
}
