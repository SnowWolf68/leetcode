package problemList.dp.solution;

/**
    读懂题目
 */
public class LC377 {
    public int combinationSum4(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int i = 1;i <= target;i++){
            for(int j = 0;j < n;j++){
                if(i - nums[j] >= 0) dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }
}
