package solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
首先通过反证法不难得到: 对于同一个工厂而言, 其修理的这些机器人, 其在下标上一定是 "连续的" , 这点也是下面进行dp的基础

dp[i][j] 表示考虑[0, i]区间的工厂, 修理[0, j]区间的机器人, 此时总的移动最小距离
    dp[i][j]: 此时对于下标为i的工厂, 有 修机器人 和 不修机器人 两种选择: 
    1. 不修机器人: dp[i][j] = dp[i - 1][j];
    2. 修理机器人: 枚举最后一个工厂修理哪一部分的机器人, 假设最后一个工厂修理[k, j]区间的机器人
        首先需要满足条件: j - k + 1 <= factory[i][1]
        然后有: dp[i][j] = dp[i - 1][k - 1] + distance[k][j]  其中distance[k][j]表示将[k, j]区间的机器人移动到下标为i的工厂, 此时移动的最小总距离
        对于所有的k, 此时dp[i][j]只需要取一个min即可
    对于上面的两种可能, 此时也需要让dp[i][j]取一个min即可
初始化: i - 1, k - 1都有可能越界, 因此需要添加一行一列的辅助节点
    第一行: 此时没有任何工厂, 那么除了dp[0][0] = 0之外, 其余位置显然都是非法, 那么初始化为INF
    第一列: 此时没有任何机器人, 那么此时初始化第一列为0

这里由于distance[k][j]和下标为i的工厂的位置有关, 因此预处理distance数组也不能降低复杂度
但是这里由于工厂的位置确定, 因此我们可以倒序从j遍历到k, 同时维护一个sum, 这样就能够在遍历的同时得到[k, j]区间的机器人到下标为i的工厂的距离的和
 */
public class LC2463 {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (o1, o2) -> o1[0] - o2[0]);
        int m = factory.length, n = robot.size();
        long INF = Long.MAX_VALUE / 2;
        long[][] dp = new long[m + 1][n + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                long sum = 0;
                for(int k = j;k >= Math.max(1, j - factory[i - 1][1] + 1);k--){
                    sum += Math.abs(robot.get(k - 1) - factory[i - 1][0]);
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k - 1] + sum);
                }
            }
        }
        return dp[m][n];
    }
}
