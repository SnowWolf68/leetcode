package problemList.dp.solution;

/**
"恰好装满" 类型背包, 物品体积: capacities[i] - 1, 背包容量: k
求背包恰好装满的方案数

dp[i][j] 表示考虑[0, i]区间的物品, 物品体积和为j, 此时的方案数
    dp[i][j]: 针对下标为i的物品, 此时有选或不选两种选择
        1. 选: dp[i][j] = dp[i - 1][j - capacities[i] + 1];
        2. 不选: dp[i][j] = dp[i - 1][j];
    上面两种选择只需要取一个和即可
初始化: 只需要初始化i这个维度, 添加一行辅助节点, 第一行意味着此时没有任何物品, 那么初始化dp[0][0] = 1, 其余位置都是非法, 初始化为0
return dp[n - 1][k];
 */
public class LCP47 {
    public int securityCheck(int[] capacities, int k) {
        int n = capacities.length, MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= k;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - (capacities[i - 1] - 1) >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - (capacities[i - 1] - 1)]) % MOD;
            }
        }
        return dp[n][k];
    }
}
