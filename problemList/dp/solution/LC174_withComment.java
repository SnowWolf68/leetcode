package problemList.dp.solution;

import java.util.Arrays;

/**
dp[i][j] 表示从[i, j]走到右下角, 所需要的最低初始健康点数
dp[i][j]: 有两种情况, 分别是向右或向下走
            1. 向右走: 根据dungeon[i][j]格子的元素的正负, 还需要分为两种情况
                1. dungeon[i][j] >= 0: dp[i][j] = Math.max(1, dp[i][j + 1] - dungeon[i][j])
                    这里之所以取一个max, 是因为需要保证骑士走到[i, j]位置时, 血量>= 1 (因为如果血量为0, 会立即死去)
                2. dungeon[i][j] < 0:  dp[i][j] = dp[i][j + 1] - dungeon[i][j];
            2. 向下走: 和上面那种情况类似
                1. dungeon[i][j] >= 0: dp[i][j] = Math.max(1, dp[i + 1][j] - dungeon[i][j])
                2. dungeon[i][j] < 0:  dp[i][j] = dp[i + 1][j] - dungeon[i][j];
填表顺序: 从下往上, 从右往左
初始化: 在最右边和最下边添加一行一列的辅助节点, dp[m - 1][n] = dp[m][n - 1] = 1, 其余位置都初始化为-INF
return dp[0][0];
 */
public class LC174_withComment {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length, INF = 0x3f3f3f3f;
        int[][] dp = new int[m + 1][n + 1];
        Arrays.fill(dp[m], INF);
        for(int i = 0;i <= m;i++) dp[i][n] = INF;
        dp[m - 1][n] = dp[m][n - 1] = 1;
        for(int i = m - 1;i >= 0;i--){
            for(int j = n - 1;j >= 0;j--){
                if(dungeon[i][j] >= 0){
                    dp[i][j] = Math.min(Math.max(1, dp[i][j + 1] - dungeon[i][j]), Math.max(1, dp[i + 1][j] - dungeon[i][j]));
                }else{
                    dp[i][j] = Math.min(dp[i][j + 1] - dungeon[i][j], dp[i + 1][j] - dungeon[i][j]);
                }
            }
        }
        return dp[0][0];
    }
}
