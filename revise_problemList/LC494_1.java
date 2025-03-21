package revise_problemList;

/**
这种解法是没有经过转化, 直接硬写的, 缺点是由于和可能是负数, 因此需要整体平移sum个单位

除此之外, 这里我们只对行做了初始化, 而没有对列进行初始化, 这是因为根据状态转移方程, 
    dp[i][j]会依赖于上一行中的某个位置, 因此只要将第一行正确初始化之后, 列就不需要进行任何的初始化操作
 */
public class LC494_1 {
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length, sum = 0;
        for(int num : nums) sum += num;
        if(target > sum || target < -sum) return 0;
        int[][] dp = new int[n + 1][2 * sum + 1];   // 0, ... sum - 1,   sum,   sum + 1, ... 2 * sum
        dp[0][sum] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= 2 * sum;j++){
                if(j - nums[i - 1] >= 0) dp[i][j] += dp[i - 1][j - nums[i - 1]];
                if(j + nums[i - 1] <= 2 * sum) dp[i][j] += dp[i - 1][j + nums[i - 1]];
            }
        }
        return dp[n][target + sum];
    }
}
