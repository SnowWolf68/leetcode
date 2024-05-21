package problemList.dp.solution;

/**
使用类似LC2400的方法, 但是会MLE, 并且时间复杂度到了O(step * arrLen), 肯定也会T
 */
public class LC1269_MLE_TLE {
    public int numWays(int steps, int arrLen) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[steps + 1][arrLen];
        dp[0][0] = 1;
        for(int i = 1;i <= steps;i++){
            for(int j = 0;j < arrLen;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - 1 >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - 1]) % MOD;
                if(j + 1 < arrLen) dp[i][j] = (dp[i][j] + dp[i - 1][j + 1]) % MOD;
            }
        }
        return dp[steps][0];
    }
}
