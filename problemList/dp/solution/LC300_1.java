package problemList.dp.solution;

/**
O(n ^ 2) 的普通dp写法
 */
public class LC300_1 {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int ret = 1;
        for(int i = 1;i < n;i++){
            dp[i] = 1;
            for(int j = 0;j < i;j++){
                if(nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
}
