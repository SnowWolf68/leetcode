package problemList.dp.solution;

import java.util.Arrays;

/**
优化: 状态转移优化
    这里我们可以从 当前房屋和前一个房屋是否在同一个街区 出发考虑, 这样的状态转移方程在计算dp[i][j][k]的时候就不再需要遍历p, 从而降低时间复杂度
具体来说: 
    dp[i][j][k] 表示将[0, j]区间的房屋分成i个街道, 并且最后一个房屋刷的颜色是第k种颜色, 此时的最小总花费
        dp[i][j][k]: 根据当前房屋和前面一个房屋是否在同一街区来分类, 在这之前, 还需要按照当前房屋是否已经被涂色进行分类
            1. houses[j] == 0, 即当前房屋没有被涂色: 那么首先我们可以枚举当前房屋需要被涂的颜色k
                1. 如果当前房屋和前面一个房屋在同一个街区: dp[i][j][k] = dp[i][j - 1][k] + cost[j][k];
                2. 如果当前房屋和前面一个房屋不在同一个街区: dp[i][j][k] = min(dp[i - 1][j - 1][q != k]) + cost[j][k];
            2. houses[j] != 0, 即当前房屋已经被涂色: 也就是相当于k == houses[j] - 1, 那么首先对于那些 dp[i][j][ != k] 的位置, 首先应该都初始化为非法, 即INF
                1. 如果当前房屋和前面一个房屋在同一个街区: dp[i][j][k] = dp[i][j - 1][k];
                2. 如果当前房屋和前面一个房屋不在同一个街区: dp[i][j][k] = min(dp[i - 1][j - 1][q != k]);
            特别的, 对于min(dp[i - 1][j - 1][q != k])的处理, 由于其只能在 最小 以及 次小 中取, 因此我们可以预处理一次遍历得到最小/次小, 然后就可以O(1)地知道q != k时的最小值
        对于所有可能的情况, 只需要取一个min即可
    初始化: i, j可能越界, i == 0本来就相当于辅助节点, 此时意味着没有任何街道, 那么i == 0时, dp[0][0][..] = 0, 其余位置都为INF
        j添加一面辅助节点, 同理j == 0时, dp[0][0][..] = 0, 其余位置都为INF
    return min(dp[target][m - 1][..]);

    时间复杂度: O(target * m * n)
 */
public class LC1473_2 {
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int INF = 0x3f3f3f3f;
        int[][][] dp = new int[target + 1][m + 1][n];
        for(int j = 0;j <= m;j++){
            for(int k = 0;k < n;k++) dp[0][j][k] = INF;
        }
        for(int i = 0;i <= target;i++){
            for(int k = 0;k < n;k++) dp[i][0][k] = INF;
        }
        Arrays.fill(dp[0][0], 0);
        for(int i = 1;i <= target;i++){
            for(int j = 1;j <= m;j++){
                int min = INF, secondMin = INF;
                for(int q = 0;q < n;q++){
                    if(dp[i - 1][j - 1][q] < min){
                        secondMin = min;
                        min = dp[i - 1][j - 1][q];
                    }else if(dp[i - 1][j - 1][q] < secondMin){
                        secondMin = dp[i - 1][j - 1][q];
                    }
                }
                if(houses[j - 1] == 0){
                    for(int k = 0;k < n;k++) dp[i][j][k] = Math.min(dp[i][j - 1][k], j == 1 ? min : (dp[i - 1][j - 1][k] == min ? secondMin : min)) + cost[j - 1][k];
                }else{
                    int k = houses[j - 1] - 1;
                    Arrays.fill(dp[i][j], INF);
                    dp[i][j][k] = Math.min(dp[i][j - 1][k], j == 1 ? min : (dp[i - 1][j - 1][k] == min ? secondMin : min));
                }
            }
        }
        int ret = INF;
        for(int k = 0;k < n;k++){
            ret = Math.min(ret, dp[target][m][k]);
        }
        return ret == INF ? -1 : ret;
    }
}
