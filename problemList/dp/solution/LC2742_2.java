package problemList.dp.solution;

import java.util.*;

/**
转换成0-1背包

免费墙个数 + 付费墙个数 = n
付费墙时间 >= 免费墙时间
免费墙个数 = 免费墙时间
-> 付费墙时间 >= 免费墙个数
    免费墙个数 + 付费墙个数 = n
-> 付费墙时间 >= n - 付费墙个数
-> 付费墙时间 + 付费墙个数 >= n
假设付费墙下标为i1, i2, ...
那么上式即为: time[i1] + time[i2] + time[i3] + ... + 1 + 1 + 1 + ... >= n
-> (time[i1] + 1) + (time[i2] + 1) + (time[i3] + 1) + ... >= n
即 "至少" 型0-1背包问题
    物品体积time[i] + 1, 体积至少为n
    物品价值cost[i], 求价值和的最小值

做法1: 使用 "至少装满" 型背包
dp[i][j] 表示考虑[0, i]区间的物品, 当前体积至少为j时, 此时价值和的最小值
    dp[i][j]: 对于下标为i的这个物品, 此时有选或不选两种选择
        1. 选: dp[i][j] = dp[i - 1][Math.max(0, j - (time[i] + 1))] + cost[i];
        2. 不选: dp[i][j] = dp[i - 1][j];
    对于上面两种情况, 取一个min即可
初始化: 初始化i这个维度, 添加一行辅助节点, 第一行意味着此时没有任何物品, 此时体积只能是0, 价值和也只能是0, 即初始化dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return dp[n - 1][n];
 */
public class LC2742_2 {
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][n + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= n;j++){
                dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][Math.max(0, j - (time[i - 1] + 1))] + cost[i - 1]);
            }
        }
        return dp[n][n];
    }
}
