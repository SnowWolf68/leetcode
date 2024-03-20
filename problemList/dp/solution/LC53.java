package problemList.dp.solution;

/**
dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
 */
public class LC53 {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int ret = dp[0];
        for(int i = 1;i < n;i++){
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
}
