package problemList.dp.solution;

import java.util.Arrays;

/**
由工作的依赖关系可知, 每一天所需要做的工作必须是连续的, 并且我们只能从前往后依次安排每一天的工作, 这符合划分型dp的条件
约束: 划分个数为d, 求每一组内元素最大值的和的最小值

dp[i][j] 表示将[0, j]区间的工作分为i组, 此时每一组内最大值和的最小值
    dp[i][j]: 枚举最后一组工作的起始下标k
        dp[i][j] = dp[i - 1][k - 1] + max[k][j], 其中, max[k][j]表示[k, j]区间内的最大值
        我们可以倒序遍历k, 同时使用一个变量max维护[k, j]区间的最大值
    对于所有可能的k, 我们只需要取一个min即可
初始化: i == 0本身就是辅助节点, 此时分为0组, 那么显然dp[0][0] = 0, 其余位置都是非法, 初始化为INF
    第一列添加一列辅助节点, 第一列意味着当前没有任何工作, 那么dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return dp[d][n - 1];
 */
public class LC1335 {
    public int minDifficulty(int[] job, int d) {
        int n = job.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[d + 1][n + 1];
        Arrays.fill(dp[0], INF);
        for(int i = 0;i <= d;i++) dp[i][0] = INF;
        dp[0][0] = 0;
        for(int i = 1;i <= d;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                int max = 0;
                for(int k = j;k > 0;k--){
                    max = Math.max(max, job[k - 1]);
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k - 1] + max);
                }
            }
        }
        return dp[d][n] == INF ? -1 : dp[d][n];
    }
}
