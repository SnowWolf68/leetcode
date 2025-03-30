package revise_problemList;

/**
关于 不超过 类型 背包的一点碎碎念
如果说LC474的 不超过 类型的背包还比较好理解, 那么本题的不超过类型背包就有一点抽象
首先显而易见的一点是: 使用不超过类型背包, 相比恰好装满类型的背包, 其能减少内层循环的循环次数, 从而降低时间复杂度


 */
public class LC1049_2 {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length, sum = 0;
        for(int num : stones) sum += num;
        boolean[][] dp = new boolean[n + 1][2 * sum + 1];
        dp[0][sum] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= 2 * sum;j++){
                if(j - stones[i - 1] >= 0) dp[i][j] |= dp[i - 1][j - stones[i - 1]];
                if(j + stones[i - 1] <= 2 * sum) dp[i][j] |= dp[i - 1][j + stones[i - 1]];
            }
        }
        for(int j = sum;j <= 2 * sum;j++){
            if(dp[n][j]) return j - sum;
        }
        return -1;
    }
}
