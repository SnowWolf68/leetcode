package problemList.dp.solution;

import java.util.*;

/**
"至少装满" 型0-1背包 转化成 "不超过" 型0-1背包
    物品体积: time[i] + 1, 物品体积和至少为n
    物品价值: cost[i], 求物品价值和的最小值
考虑 "剩余需要的体积", 即将原来dp表的j维度 "至少需要的体积" 改成 "剩余需要的体积"
确切的来说, 这里 "剩余需要的体积" 其实是一个 "不超过" 的概念, 即 "剩余需要的体积不超过j", 这其实变成了 "不超过" 类型的背包
dp[i][j] 表示考虑[0, i]区间的物品, 并且剩余需要的体积不超过j, 此时所有物品价值和的最小值
    dp[i][j]: 对于下标为i的物品, 此时有选或不选两种选择
        1. 选: dp[i][j] = dp[i - 1][j + (time[i] + 1)] + cost[i];
        2. 不选: dp[i][j] = dp[i - 1][j];
    对于上面两种情况, 取一个min即可
    关键: 这里和普通的 "不超过" 类型的0-1背包还有点差别
        普通的 "不超过" 类型的0-1背包, 其状态转移方程的j维度都是 - xxx
        而这里是 + xxx
        对于 - xxx 的状态转移方程来说, 一旦j - xxx < 0, 那么此时体积不超过一个负数, 显然是不合适的
        但是这里 + xxx 的状态转移方程来说, 即使j + xxx > n, 此时意味着剩余需要的体积不超过一个 > n 的数, 此时显然也是合法的, 由于剩余需要的体积最多就是n, 因此这里我们直接把j + xxx这个 > n 的数直接对n取min, 强制变成n即可
        因此这里的状态转移方程变为dp[i][j] = dp[i - 1][Math.min(n, j + (time[i] + 1))] + cost[i];
        并且不需要判断j + (time[i] + 1]) <= n
初始化: 初始化i这个维度, 添加一行辅助节点, 第一行意味着此时没有任何物品, 那么此时剩余需要的体积为n, 价值和为0,  即初始化dp[0][n] = 0, 其余位置都是非法, 初始化为INF
return dp[n - 1][0];
 */
public class LC2742_3 {
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][n + 1];
        Arrays.fill(dp[0], INF);
        dp[0][n] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= n;j++){
                dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][Math.min(n, j + (time[i - 1] + 1))] + cost[i - 1]);
            }
        }
        return dp[n][0];
    }
}
