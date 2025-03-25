package revise_problemList;

public class LC1774 {
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        // 先考虑使用配料能组成的各种成本
        int n = toppingCosts.length, INF = 0x3f3f3f3f;
        boolean[][] dp = new boolean[n + 1][2 * target + 1];    // 只计算target长度不够用, 需要多算一点
        dp[0][0] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= 2 * target;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - toppingCosts[i - 1] >= 0) dp[i][j] |= dp[i - 1][j - toppingCosts[i - 1]];
                if(j - 2 * toppingCosts[i - 1] >= 0) dp[i][j] |= dp[i - 1][j - 2 * toppingCosts[i - 1]];
            }
        }
        int cost = INF;
        for(int base : baseCosts){
            if(base >= target && base - target < Math.abs(cost - target)) cost = base; 
            else {
                for(int j = 0;j <= 2 * target;j++){
                    if(dp[n][j] && (Math.abs(cost - target) > Math.abs(target - (base + j)) || (Math.abs(cost - target) == Math.abs(target - (base + j)) && base + j < cost))) cost = base + j;
                }
            }
        }
        return cost;
    }
}
