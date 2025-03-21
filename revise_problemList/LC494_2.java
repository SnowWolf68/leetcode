package revise_problemList;

/**
positiveNum + negativeNum = target
positiveNum - negativeNum = sum
--> positiveNum = (target + sum) / 2
--> 最基本的背包问题
 */
public class LC494_2 {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length, sum = 0;
        for(int num : nums) sum += num;
        int positiveNum = (target + sum) / 2;
        if((target + sum) % 2 != 0 || positiveNum < 0) return 0;    // 几种特殊情况判断一下
        int[][] dp = new int[n + 1][positiveNum + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= positiveNum;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums[i - 1] >= 0) dp[i][j] += dp[i - 1][j - nums[i - 1]];
            }
        }
        return dp[n][positiveNum];
    }
}
