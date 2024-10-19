package problemList.dp.solution;

import java.util.Arrays;

/**
这题也可以使用 前缀和优化 , 将时间复杂度从 O(n ^ 3)优化到O(n ^ 2)
 */
public class LC903_2 {
    public int numPermsDISequence(String s) {
        int n = s.length(), MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;
        int[] preSum = new int[n + 2];
        Arrays.fill(preSum, 1, n + 1, 1);
        for(int i = 1;i <= n;i++){
            int[] nxtSum = new int[n + 2];
            if(s.charAt(i - 1) == 'D'){
                for(int j = 0;j <= n;j++){
                    // for(int k = j;k < i;k++) {
                    //     dp[i][j] = (dp[i][j] + dp[i - 1][k]) % MOD;
                    // }
                    dp[i][j] = (preSum[i] - preSum[j] + MOD) % MOD;
                    nxtSum[j + 1] = (nxtSum[j] + dp[i][j]) % MOD;
                }
            }else{
                for(int j = 0;j <= n;j++){
                    // for(int k = 0;k < j;k++) {
                    //     dp[i][j] = (dp[i][j] + dp[i - 1][k]) % MOD;
                    // }
                    dp[i][j] = preSum[j];
                    nxtSum[j + 1] = (nxtSum[j] + dp[i][j]) % MOD;
                }
            }
            preSum = nxtSum;
        }
        int ret = 0;
        for(int i = 0;i <= n;i++) ret = (ret + dp[n][i]) % MOD;
        return ret;
    }
}
