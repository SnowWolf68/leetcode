package problemList.dp.solution;

/**
显然可以使用前缀和优化
*/
public class LC3250_3 {
    public int countOfPairs(int[] nums) {
        int max = 0, n = nums.length, MOD = (int)1e9 + 7;
        for(int x : nums) max = Math.max(max, x);
        int[][] dp = new int[n][max + 1];
        for(int i = 0;i <= nums[0];i++){
            if(nums[0] - i >= 0) dp[0][i] = 1;
        }
        int[] sum = new int[max + 2];
        for(int i = 1;i <= max + 1;i++){
            sum[i] = sum[i - 1] + dp[0][i - 1];
        }
        for(int i = 1;i < n;i++){
            int[] newSum = new int[max + 2];
            for(int j = 0;j <= nums[i];j++){
                // for(int prev = 0;prev <= Math.min(j, Math.min(nums[i - 1], nums[i - 1] - nums[i] + j));prev++){
                //     dp[i][j] = (dp[i][j] + dp[i - 1][prev]) % MOD;
                // }
                int upper = Math.max(Math.min(j, Math.min(nums[i - 1], nums[i - 1] - nums[i] + j)), -1);
                dp[i][j] = sum[upper + 1];
                newSum[j + 1] = (newSum[j] + dp[i][j]) % MOD;
            }
            sum = newSum;
        }
        int ret = 0;
        for(int j = 0;j <= max;j++){
            ret = (ret + dp[n - 1][j]) % MOD;
        }
        return ret;
    }
}
