package problemList.dp.solution;

import java.util.*;

/**
不是严格递增的LIS
常规dp: O(n ^ 2)
 */
public class LC2826_1 {
    public int minimumOperations(List<Integer> nums) {
        int n = nums.size();
        int[] dp = new int[n];
        dp[0] = 1;
        int ret = 1;
        for(int i = 1;i < n;i++){
            dp[i] = 1;
            for(int j = 0;j < i;j++){
                if(nums.get(j) <= nums.get(i)){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ret = Math.max(ret, dp[i]);
        }
        return n - ret;
    }
}
