package problemList.dp.solution;

/**
dp[i][j] 表示考虑[0, i]区间的元素, 能否选出元素和为j的子集
    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i]];
初始化: 添加一行辅助节点, 第一行意味着此时没有任何元素, 那么dp[0][0] = true, 其余位置都是false
return dp[n - 1][target];
 */
public class LC416 {
    public boolean canPartition(int[] nums) {
        int n = nums.length, sum = 0;
        for(int x : nums) sum += x;
        if(sum % 2 != 0) return false;
        int target = sum / 2;
        boolean[][] dp = new boolean[n + 1][target + 1];
        dp[0][0] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= target;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums[i - 1] >= 0) dp[i][j] |= dp[i - 1][j - nums[i - 1]];
            }
        }
        return dp[n][target];
    }
}
