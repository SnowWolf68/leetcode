package problemList.dp.solution;

/**
从前往后的dp
    dp[i][j]表示得到[0, i]区间的水果, j == 0表示不购买第i个水果, j == 1表示购买第i个水果, 此时所需要的最少金币数
        1. dp[i][0]: 枚举上一个购买的位置(这里指的是下标), 假设为k, 这里k需要满足: 2 * k + 1 >= i, 解得k的范围: k >= ceil((i - 1) / 2)
            因此遍历所有可能的k, 那么有: 
                dp[i][0] = dp[k][1];
            对于所有可能的k, dp[i][0]只需要取一个min即可
        2. dp[i][1]: 枚举上一个购买的位置, 假设为k, 这里k需要满足: 2 * k + 1 >= i - 1, 解得k的范围: k >= ceil((i - 2) / 2)
            同理遍历所有可能的k, 那么有: 
                dp[i][1] = dp[k][1] + price[i];
            对于所有可能的k, dp[i][1]只需要取一个min即可
    初始化: 这里ceil((i - 1) / 2), ceil((i - 2) / 2)有可能出现负数, 因此我们需要考虑初始化
        我们只需要直接初始化第一个节点即可, 因为如果i从1开始遍历, 那么上面两个式子的最小值都是0, 此时就不会出现越界, 因此直接初始化dp[0][0], dp[0][1]这两个位置
        显然, 我们应该初始化: dp[0][0] = INF; dp[0][1] = prices[i];
    return min(dp[n - 1][0], dp[n - 1][1]);

    时间复杂度: O(n ^ 2)

    在代码实现中, 由于lower1 = ceil((i - 1) / 2)和lower2 = ceil((i - 2) / 2)只有两种情况, 要么相等, 要么lower2 = lower1 - 1
    因此我们可以首先让k在[lower1, i - 1]区间遍历, 同时更新dp[i][0], dp[i][1], 然后判断此时lower1和lower2是否相等, 如果相等, 那么就不需要额外更新dp[i][1]
    如果不相等, 那么意味着lower2 = lower1 - 1, 那么此时我们再额外更新一次dp[i][1]即可
 */
public class LC2944_1 {
    public int minimumCoins(int[] prices) {
        int n = prices.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n][2];
        dp[0][0] = INF; dp[0][1] = prices[0];
        for(int i = 1;i < n;i++){
            dp[i][0] = dp[i][1] = INF;
            int lower1 = (int)Math.ceil((double)(i - 1) / 2);
            for(int k = lower1;k < i;k++){
                dp[i][0] = Math.min(dp[i][0], dp[k][1]);
                dp[i][1] = Math.min(dp[i][1], dp[k][1] + prices[i]);
            }
            int lower2 = (int)Math.ceil((double)(i - 2) / 2);
            if(lower2 != lower1) dp[i][1] = Math.min(dp[i][1], dp[lower2][1] + prices[i]);
        }
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }
}
