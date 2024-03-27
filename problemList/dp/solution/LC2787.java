package problemList.dp.solution;

/**
dp[i][j] 表示考虑[1, i]区间的正整数, 用这些正整数的x次幂组成和为j的方案数
    dp[i][j]: 对于(i ^ x)这个数, 有两种选择
        1. 选: dp[i][j] += dp[i - 1][j - Math.pow(i, x)];
        2. 不选: dp[i][j] += dp[i - 1][j];
初始化: 直接初始化dp[1]这一行, 此时dp[1][1] = 1, 其余位置都是非法, 初始化为0
    同时, 初始化第一列为1, 这样的目的是为了保证dp[i][j] += dp[i - 1][j - pow]; 更新的正确性
        更具体的: 如果i == 4, j == 4, x == 1, n == 4, 那么此时pow == 4, 此时dp[4][4]会依赖dp[3][0]这个位置, 此时显然需要将第一列全都初始化成1
return dp[n][n];
 */
public class LC2787 {
    public int numberOfWays(int n, int x) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][n + 1];
        dp[1][1] = 1;
        for(int j = 0;j <= n;j++) dp[j][0] = 1;
        for(int i = 2;i <= n;i++){
            int pow = (int)Math.pow(i, x);
            for(int j = 0;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - pow >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - pow]) % MOD;
            }
        }
        return dp[n][n];
    }
}
