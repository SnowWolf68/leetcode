package problemList.dp.solution;

/**
状态表示优化: 
    假设和为k的子序列为S, 包含S的子序列为T, 其实就是需要求T的数目
    dp[i][j] 表示考虑[0, i]区间的子序列, 此时包含和为j的子序列S的子序列T的数目
        dp[i][j]: 针对nums[i], 有三种可能: 
            1. nums[i]在S中(也在T中): dp[i][j] = dp[i - 1][j - nums[i]];
            2. nums[i]不在S中, 但是在T中: dp[i][j] = do[i - 1][j];
            3. nums[i]既不在S中, 也不在T中: dp[i][j] = dp[i - 1][j];
        上面三种情况需要取一个和, 即dp[i][j] = dp[i - 1][j - nums[i]] + 2 * dp[i - 1][j];
    初始化: 针对i这个维度, 需要添加一行辅助节点, 第一行意味着当前没有任何元素, 那么此时dp[0][0] = 1, 其余位置都是0
    return dp[n][k];

时间复杂度: O(n * k)
 */
public class LC3082_2 {
    public int sumOfPower(int[] nums, int k) {
        int n = nums.length, MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= k;j++){
                dp[i][j] = (2 * dp[i - 1][j]) % MOD;
                if(j - nums[i - 1] >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - nums[i - 1]]) % MOD;
            }
        }
        return dp[n][k];
    }
}
