package problemList.dp.solution;

/**
朴素DP的话时间复杂度是 O(n ^ 3), 这里n可以到1e3, 因此会T
考虑优化: 

 */
public class LC3202_TLE {
    public int maximumLength(int[] nums, int k) {
        int n = nums.length;
        // dp[i][j] 表示子序列最后两个元素的下标分别为i, j时, 此时最长有效子序列的长度
        int[][] dp = new int[n][n];
        int ret = 0;
        for(int i = 1;i < n;i++){
            for(int j = i + 1;j < n;j++){
                for(int p = 0;p < i;p++){
                    if((nums[p] + nums[i]) % k == (nums[i] + nums[j]) % k){
                        dp[i][j] = Math.max(dp[p][i] == 0 ? 3 : dp[p][i] + 1, dp[i][j]);
                    }
                }
                ret = Math.max(ret, dp[i][j]);
            }
        }
        return ret == 0 ? 2 : ret;
    }
}
