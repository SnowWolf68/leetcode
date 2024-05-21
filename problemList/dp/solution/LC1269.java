package problemList.dp.solution;

/**
考虑如何优化: 
    这里关键是arrLen的范围比较大, 所以会MLE, 但是其实可以想一下, 由于这里我们需要走steps步, 并且能够回到原点
    所以即使arrLen再大, 因为有steps的限制, 所以肯定走不到比steps / 2更远的下标, 因为走到了就回不来了
    因此这里我们可以把arrLen的范围缩小, 缩小到steps / 2 + 1, 这样就可以避免MLE了

    并且这样优化之后, 不仅能够不MLE, 其实还是对时间复杂度做常数的优化
 */
public class LC1269 {
    public int numWays(int steps, int arrLen) {
        arrLen = Math.min(arrLen, steps / 2 + 1);
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
