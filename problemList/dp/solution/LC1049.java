package problemList.dp.solution;

/**
这题实际上就是 "目标和" 那题的变形
假设stones累加和为sum, 要想让最后剩下的石子重量最小, 实际上也就是选出一组石头, 使得这堆石头的重量和为 小于等于sum / 2的最大值
因此这里的背包需要满足的条件就从 "恰好装满" 变成 "不超过xxx"
dp[i][j] 表示考虑[0, i]区间的stones, 石头重量和不超过j的最大值
    dp[i][j]: 考虑stones[i]选或不选
        1. 选: dp[i][j] = dp[i - 1][j - stones[i]] + stones[i];
        2. 不选: dp[i][j] = dp[i - 1][j];
    上面两种情况只需要取一个max
初始化: 初始化i这个维度, 添加一行辅助节点, 第一行意味着此时没有任何石头, 那么此时由于dp[i][j]表示的是 "不超过" 类型的背包, 因此第一行全部初始化成0
return dp[n][sum / 2]
 */
public class LC1049 {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length, sum = 0;
        for(int x : stones) sum += x;
        int[][] dp = new int[n + 1][sum / 2 + 1];
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= sum / 2;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - stones[i - 1] >= 0) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - stones[i - 1]] + stones[i - 1]);
            }
        }
        return (int)(((double)sum / 2 - dp[n][sum / 2]) * 2);
    }
}
