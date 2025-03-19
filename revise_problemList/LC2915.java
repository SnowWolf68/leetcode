package revise_problemList;

import java.util.List;

public class LC2915 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int n = nums.size(), INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][target + 1];
        for(int i = 1;i <= target;i++) dp[0][i] = -INF;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= target;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums.get(i - 1) >= 0) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - nums.get(i - 1)] + 1);
            }
        }
        // for(int i = 0;i <= n;i++){
        //     for(int j = 0;j <= target;j++){
        //         System.out.print(dp[i][j] + " ");       
        //     }
        //     System.out.println();
        // }
        return dp[n][target] < 0 ? -1 : dp[n][target];
    }
}
