package revise_problemList;

import java.util.List;

public class LC2915 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int n = nums.size(), INF = 0x3f3f3f3f;
        int[] dp = new int[target + 1];
        for(int i = 1;i <= target;i++) dp[i] = -INF;
        for(int i = 1;i <= n;i++){
            for(int j = target;j >= 1;j--){
                dp[j] = dp[j];
                if(j - nums.get(i - 1) >= 0) dp[j] = Math.max(dp[j], dp[j - nums.get(i - 1)] + 1);
            }
        }
        return dp[target] < 0 ? -1 : dp[target];
    }
}