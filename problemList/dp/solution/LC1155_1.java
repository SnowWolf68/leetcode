package problemList.dp.solution;

/**
多重背包: 
dp[i][j] 表示考虑[0, i]区间的骰子, 组成和为j的方案数
    dp[i][j]: 枚举最后一个骰子的点数k
        dp[i][j] += dp[i - 1][j - k];
初始化: 初始化i这个维度, 添加辅助节点, 第一行意味着此时没有任何骰子, 那么此时和只能为0, 初始化dp[0][0] = 1, 其余位置都是非法, 初始化为0;
return dp[n - 1][target];

时间复杂度: O(n * k * target)
 */
public class LC1155_1 {
    public int numRollsToTarget(int n, int k, int target) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= target;j++){
                for(int p = 1;p <= k;p++){
                    if(j - p >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - p]) % MOD;
                }
            }
        }
        return dp[n][target];
    }
}
