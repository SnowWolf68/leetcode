package problemList.dp.solution;

/**
实际上, dp表不需要三个维度, 两个维度即可
 */
public class LC3250_2 {
    public int countOfPairs(int[] nums) {
        int max = 0, n = nums.length, MOD = (int)1e9 + 7;
        for(int x : nums) max = Math.max(max, x);
        int[][] dp = new int[n][max + 1];
        for(int i = 0;i <= nums[0];i++){
            if(nums[0] - i >= 0) dp[0][i] = 1;
        }
        for(int i = 1;i < n;i++){
            for(int j = 0;j <= nums[i];j++){
                for(int prev = 0;prev <= Math.min(j, nums[i - 1]);prev++){
                    if(nums[i] - j <= nums[i - 1] - prev) dp[i][j] = (dp[i][j] + dp[i - 1][prev]) % MOD;
                }
            }
        }
        int ret = 0;
        for(int j = 0;j <= max;j++){
            ret = (ret + dp[n - 1][j]) % MOD;
        }
        return ret;
    }
}
