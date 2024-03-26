package problemList.dp.solution;

import java.util.*;

/**
dp[i][j] 表示考虑[0, i]区间的子序列, 和为j的情况下, 子序列长度的最大值
    dp[i][j]: 针对nums[i], 有选或不选两种可能
        1. 选: dp[i][j] = dp[i - 1][j - nums[i]];
        2. 不选: dp[i][j] = dp[i - 1][j];
初始化: 对i添加一行辅助节点, 第一行意味着当前没有任何元素, 那么此时dp[0][0] = 0, 其余位置都是非法, 初始化为-INF
return dp[n - 1][target]
 */
public class LC2915_1 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int n = nums.size(), INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][target + 1];
        Arrays.fill(dp[0], -INF);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= target;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums.get(i - 1) >= 0) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - nums.get(i - 1)] + 1);
            }
        }
        return Math.max(-1, dp[n][target]);
    }
}
