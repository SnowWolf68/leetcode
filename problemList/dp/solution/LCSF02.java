package problemList.dp.solution;

import java.util.*;

/**
dp[i][j] 表示当前考虑[0, i]区间的快递, 当前选择的快递容量为j, 此时剩余的最小容量
    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - N[i]] - N[i]);
初始化: 只需要初始化i这个维度, 添加一行辅助节点, 第一行意味着当前没有任何快递, 那么此时dp[0][0] = V, 其余位置都是非法, 初始化为INF
 */
public class LCSF02 {
    public int minRemainingSpace(int[] N, int V) {
        int n = N.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][V + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = V;
        int ret = INF;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= V;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - N[i - 1] >= 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - N[i - 1]] - N[i - 1]);
                if(i == n) ret = Math.min(ret, dp[i][j]);
            }
        }
        return ret;
    }
}
