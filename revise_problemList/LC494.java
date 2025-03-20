package revise_problemList;

// TODO 
public class LC494 {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= target;j++){
                if(j - nums[i - 1] >= 0) dp[i][j] += dp[i - 1][j - nums[i - 1]];
                if(j + nums[i - 1] <= target) dp[i][j] += dp[i - 1][j + nums[i - 1]];
            }
        }
        for(int i = 0;i <= n;i++){
            for(int j = 0;j <= target;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[n][target];
    }
}
