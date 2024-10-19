package problemList.dp.solution;

import java.util.Arrays;

/**
这题也可以使用前缀和优化, 具体方法和LC629一样

使用前缀和优化的时间复杂度: O(n * k)
 */
public class LC3193_2 {
    public int numberOfPermutations(int n, int[][] requirements) {
        int MOD = (int)1e9 + 7;
        int[] req = new int[n];
        Arrays.fill(req, -1);
        int m = 0;
        for(int[] requirement : requirements) {
            req[requirement[0]] = requirement[1];
            m = Math.max(m, requirement[1]);
        }
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 1;
        int[] preSum = new int[m + 2];
        Arrays.fill(preSum, 1);
        preSum[0] = 0;
        for(int i = 1;i <= n;i++){
            int[] nxtSum = new int[m + 2];
            dp[i][0] = dp[i - 1][0];
            if(dp[i - 1][0] == 1) Arrays.fill(nxtSum, 1);
            nxtSum[0] = 0;
            if(req[i - 1] == -1){
                for(int j = 1;j <= m;j++){
                    // for(int k = 0;k <= Math.min(i - 1, j);k++){
                    //     dp[i][j] = (dp[i - 1][j - k] + dp[i][j]) % MOD;
                    // }
                    dp[i][j] = (preSum[j + 1] - preSum[j - Math.min(i - 1, j)] + MOD) % MOD;
                    nxtSum[j + 1] = (nxtSum[j] + dp[i][j]) % MOD;
                }
            }else{
                dp[i][0] = 0;
                Arrays.fill(nxtSum, 0);
                int j = req[i - 1];
                // for(int k = 0;k <= Math.min(i - 1, j);k++){
                //     dp[i][j] = (dp[i - 1][j - k] + dp[i][j]) % MOD;
                // }
                dp[i][j] = (preSum[j + 1] - preSum[j - Math.min(i - 1, j)] + MOD) % MOD;
                Arrays.fill(nxtSum, j + 1, m + 2, dp[i][j]);
            }
            preSum = nxtSum;
        }
        return dp[n][req[n - 1]];
    }
}
