package problemList.dp.solution;

import java.util.*;

/**
由于免费刷墙必须在付费刷墙的同时进行, 因此可以理解为: 免费刷墙需要 消耗 付费刷墙的时间
也就是说, 免费刷一面墙, 需要消耗1单位的付费刷墙时间, 而付费刷一面墙, 可以获得time[i]的付费刷墙时间
因此将 "付费刷墙时间" 作为dp状态的其中一个维度

dp[i][j] 表示考虑[0, i]区间的墙, 当前 付费刷墙的时间 为j, 此时的最小花费
分析j的范围: 由于这里j取到负数意味着当前付费刷墙的时间为负数, 即 "赊账" , 等到后面再使用付费刷墙来将欠下的时间补上, 这显然是可以的
因为只要最后刷完所有墙时, 付费刷墙的时间 >= 0即可, 即使其中有 "赊账" 的情况, 也可以通过调整刷墙顺序来保证免费刷墙能够完成, 因此过程中的j是可以 < 0 的
j的上下界受到两方面的限制, 一是n, 因为免费刷墙需要耗费1的时间, 总的墙数为n
    二是max(time[i]), 因为至少需要付费刷一面墙, 其中ma(time[i])表示time[]数组中的最大值
因此j的范围: [lower, upper], lower = -Math.max(n, max(time[i])), upper = Math.max(n, max(time[i]));
为了处理负数, 这里将j整体平移Math.max(n, max(time[i]))个单位, 即j的范围为[0, 2 * Math.max(n, max(time[i]))]
    dp[i][j]: 对于下标为i的这面墙, 当前有两种选择
        1. 免费刷: dp[i][j] = dp[i - 1][j + 1];
        2. 付费刷: dp[i][j] = dp[i - 1][j - time[i]] + cost[i];
    上面两种情况取一个min即可
初始化: i这个维度添加一行辅助节点, 第一行意味着此时没有任何墙可以刷, 那么此时付费墙时间只能是0, 即dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return min(dp[n - 1][j]); // 其中j范围: [0, upper]
 */
public class LC2742_1 {
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length, INF = 0x3f3f3f3f, max = 0;
        for(int x : time) max = Math.max(max, x);
        int m = Math.max(n, max);
        int[][] dp = new int[n + 1][2 * m + 1];
        Arrays.fill(dp[0], INF);
        dp[0][m] = 0;
        int ret = INF;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= 2 * m;j++){
                dp[i][j] = INF;
                if(j + 1 <= 2 * m) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1]);
                if(j - time[i - 1] >= 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - time[i - 1]] + cost[i - 1]);
                if(i == n && j >= m) ret = Math.min(ret, dp[i][j]);
            }
        }
        return ret;
    }
}
