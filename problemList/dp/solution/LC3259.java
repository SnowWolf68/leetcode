package problemList.dp.solution;

/**
dp[i][0] 表示考虑[0, i]区间的时间, 并且最后一个小时喝的是A, 此时能够获得的最大总强化能量
dp[i][1]                            最后一个小时喝的是B
dp[i][2]                            最后一个小时不喝温和饮料
状态转移方程: 
    dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]) + energyDrinkA[i]
    dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][2]) + energyDrinkB[i]
    dp[i][2] = Math.max(dp[i - 1][0], dp[i - 1][1])
初始化: dp表比较简单, 不需要添加辅助节点, 直接初始化第一行即可
    第一行, 此时dp[0][0] = energyDrinkA[0], dp[0][1] = energyDrinkB[0], dp[0][2] = 0
return Math.max(dp[n - 1][0], Math.max(dp[n - 1][1], dp[n - 1][2]));
 */
public class LC3259 {
    public long maxEnergyBoost(int[] energyDrinkA, int[] energyDrinkB) {
        int n = energyDrinkA.length;
        long[][] dp = new long[n][3];
        dp[0][0] = energyDrinkA[0]; dp[0][1] = energyDrinkB[0];
        for(int i = 1;i < n;i++){
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]) + energyDrinkA[i];
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][2]) + energyDrinkB[i];
            dp[i][2] = Math.max(dp[i - 1][0], dp[i - 1][1]);
        }
        return Math.max(dp[n - 1][0], Math.max(dp[n - 1][1], dp[n - 1][2]));
    }
}
