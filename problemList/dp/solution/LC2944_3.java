package problemList.dp.solution;

/**
这题还有一种从后往前dp的做法
    dp[i] 表示得到[i, n - 1]区间的水果, 此时所需要的最小花费
        dp[i]: 在这种状态表示的定义下, 显然对于下标为i的这个水果来说, 此时只能买, 那么我们需要枚举下一个买的水果的下标j
            这里j需要满足: 2 * i + 1 >= j - 1, 并且此时有: 
            dp[i] = dp[j] + prices[i];
        对于所有可能的j, 此时我们只需要取一个min即可
    初始化: 这里j的范围是[i + 1, 2 * i + 2], 显然j有可能越界, 如果j越界, 意味着i后面的这些水果都可以免费得到, 那么此时我们直接让dp[i] = prices[i]即可
        因此我们需要初始化2 * i + 2越界的这些i即可, 2 * i + 2 >= n 解得i的范围是: i >= ceil((n - 2) / 2)
    return dp[0];

时间复杂度: O(n ^ 2)
 */
public class LC2944_3 {
    public int minimumCoins(int[] prices) {
        int n = prices.length, INF = 0x3f3f3f3f;
        int[] dp = new int[n];
        int lower = (int)Math.ceil((double)(n - 2) / 2);
        for(int i = lower;i < n;i++) dp[i] = prices[i];
        for(int i = lower - 1;i >= 0;i--){
            dp[i] = INF;
            for(int j = i + 1;j <= 2 * i + 2;j++){
                dp[i] = Math.min(dp[i], dp[j] + prices[i]);
            }
        }
        return dp[0];
    }
}
