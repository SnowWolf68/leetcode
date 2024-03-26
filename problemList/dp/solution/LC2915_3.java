package problemList.dp.solution;

import java.util.*;

/**
更快一点的写法 (方法不变, 只是优化常数)
 */
public class LC2915_3 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int INF = 0x3f3f3f3f;
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -INF);
        dp[0] = 0;
        for(int num : nums){
            for(int j = target;j >= 0;j--){
                if(j - num >= 0) dp[j] = Math.max(dp[j], dp[j - num] + 1);
            }
        }
        return Math.max(-1, dp[target]);
    }
}
