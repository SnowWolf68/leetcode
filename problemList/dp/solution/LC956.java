package problemList.dp.solution;

import java.util.*;

/**
dp[i][j] 表示考虑[0, i]区间的钢筋, 此时两根支架的高度差(左边 - 右边)为j时, 两根支架高度和的最大值
由于这里j的范围是[-sum, sum], 因此这里整体向右平移sum, 范围变成[0, 2 * sum]
    dp[i][j]: 对于rods[i], 有选或不选两种选择
        1. 选: 
            1. 焊接到左边的支架上: dp[i][j] = dp[i - 1][j + rods[i]] + rods[i];
            2. 焊接到右边的支架上: dp[i][j] = dp[i - 1][j - rods[i]] + rods[i];
        2. 不选: dp[i][j] = dp[i - 1][j];
    对于上面几种情况, 取一个max
初始化: 只需要初始化i这个维度, 添加一行辅助节点, 第一行意味着当前没有任何钢筋, 那么此时只有dp[0][sum] = 0, 其余位置都是非法, 初始化为-INF
return dp[n][0];
 */
public class LC956 {
    public int tallestBillboard(int[] rods) {
        int n = rods.length, INF = 0x3f3f3f3f, sum = 0;
        for(int x : rods) sum += x;
        int[][] dp = new int[n + 1][2 * sum + 1];
        Arrays.fill(dp[0], -INF);
        dp[0][sum] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= 2 * sum;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - rods[i - 1] >= 0) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - rods[i - 1]] + rods[i - 1]);
                if(j + rods[i - 1] <= 2 * sum) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + rods[i - 1]] + rods[i - 1]);
            }
        }
        return dp[n][sum] / 2;
    }
}
