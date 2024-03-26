package problemList.dp.solution;

/**
positiveSum - negativeSum = target
-> positiveSum = target + negativeSum
positiveSum + negativeSum = sum

--> target + 2 * negativeSum = sum
即存在多少个子集, 子集和为negativeSum满足negativeSum = (sum - target) / 2
 */
public class LC494 {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length, sum = 0;
        for(int x : nums) sum += x;
        if((sum - target) % 2 != 0 || (sum - target) < 0) return 0;
        int targetSum = (sum - target) / 2;
        int[][] dp = new int[n + 1][targetSum + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= targetSum;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums[i - 1] >= 0) dp[i][j] += dp[i - 1][j - nums[i - 1]];
            }
        }
        return dp[n][targetSum];
    }
}
