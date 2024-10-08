package problemList.unsorted;

import java.util.Arrays;

/**
dp[i][j] 表示考虑statuons[0, i]区间的加油站, 加油j次, 此时能够到达的最远距离
dp[i][j]: 针对下标为i的加油站, 此时有加或不加两种选择
    1. 不加油: dp[i][j] = dp[i - 1][j];
    2. 加油: 首先应该满足车能够开到stations[i]这个加油站, 即dp[i - 1][j - 1] >= stations[i][0]
            如果满足, 那么 dp[i][j] = dp[i - 1][j - 1] + stations[i][1]
    上面两种情况取max
初始化: 这里如果添加一行一列辅助节点的话, 第一列节点没有实际意义, 因此只添加一行辅助节点
    添加一行的辅助节点, 第一行意味着此时没有任何加油站, 那么此时只有dp[0][0]合法, 初始化dp[0][0] = startFuel
        第一行的其余位置都是非法, 初始化为-INF
    但是dp[i - 1][j - 1]中的j - 1有可能越界, 这里我们可以手动判断一下, 如果j == 0, 那么不要从dp[i - 1][j - 1]这里转移过来
填表顺序: 从上到下, 每一行内填表顺序无要求
return 满足dp[n][i] > target的最小的i   其中n = stations.length
 */
public class LC871 {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][n + 1];
        Arrays.fill(dp[0], -INF);
        dp[0][0] = startFuel;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                if(j != 0 && dp[i - 1][j - 1] >= stations[i - 1][0]){
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + stations[i - 1][1]);
                }
            }
        }
        for(int i = 0;i <= n;i++){
            if(dp[n][i] >= target) return i;
        }
        return -1;
    }
}
