package problemList.dp.solution;

import java.util.*;

/**
空间优化
 */
public class LC2915_2 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int n = nums.size(), INF = 0x3f3f3f3f;
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -INF);
        dp[0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = target;j >= 0;j--){
                if(j - nums.get(i - 1) >= 0) dp[j] = Math.max(dp[j], dp[j - nums.get(i - 1)] + 1);
            }
        }
        return Math.max(-1, dp[target]);
    }
}
