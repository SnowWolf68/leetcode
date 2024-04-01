package problemList.dp.solution;

import java.util.*;

/**
dp[i][j] 表示考虑[1, i]区间的数的平方, 组成和为j的最少数量
    dp[i][j]: 针对i ^ 2, 此时有多重选择: 
        1. 不选: dp[i][j] = dp[i - 1][j];
        2. 选一个/多个(优化后): dp[i][j] = dp[i][j - 2 * i] + 1;
    对于上面两种情况, 取一个min即可
初始化: 由于这里i从1开始, 因此i == 0就自然形成了一行辅助节点, 那么此时第一行意味着此时没有任何元素, 那么因此dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return dp[upper][n];
 */
public class LC279 {
    public int numSquares(int n) {
        int upper = (int)Math.sqrt(n), INF = 0x3f3f3f3f;
        int[][] dp = new int[upper + 1][n + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= upper;i++){
            for(int j = 0;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - i * i >= 0) dp[i][j] = Math.min(dp[i][j], dp[i][j - i * i] + 1);
            }
        }
        return dp[upper][n];
    }
}
