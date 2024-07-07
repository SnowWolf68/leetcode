package solution;

import java.util.Arrays;

/**
dp[state][i] 表示当前已经走过的点的集合为state, 并且最后一个走过的点为i, 此时的最小路径
dp[state][i]: 枚举上一个走过的点, 假设为j
    dp[state][i] = min(dp[state & (~(1 << i))][j] + dist[j][i])     其中dist[j][i]表示j, i两点之间的距离, 可以用floyd得到
初始化: 初始化所有bitCount(state) == 1的行, 对于这些行, dp[1 << i][i] = 0, 其余位置都初始化为INF
return min(dp[mask - 1][i])

TODO: 再看一下floyd
 */
public class LC847 {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length, INF = 0x3f3f3f3f;
        int[][] dist = new int[n][n];
        for(int i = 0;i < n;i++){
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for(int i = 0;i < n;i++){
            for(int j = 0;j < graph[i].length;j++){
                dist[i][graph[i][j]] = 1;
                dist[graph[i][j]][i] = 1;
            }
        }
        // floyd
        for(int k = 0;k < n;k++){
            for(int i = 0;i < n;i++){
                for(int j = 0;j < n;j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        // 状压DP
        int mask = 1 << n;
        int[][] dp = new int[mask][n];
        for(int i = 0;i < n;i++){
            Arrays.fill(dp[1 << i], INF);
            dp[1 << i][i] = 0;
        }
        int ret = INF;
        for(int state = 1;state < mask;state++){
            if(Integer.bitCount(state) <= 1) continue;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state][i] = INF;
                for(int j = 0;j < n;j++){
                    if(i == j || ((state >> j) & 1) == 0) continue;
                    dp[state][i] = Math.min(dp[state][i], dp[state & (~(1 << i))][j] + dist[i][j]);
                }
                if(state == mask - 1) ret = Math.min(ret, dp[state][i]);
            }
        }
        return ret == INF ? 0 : ret;
    }
}
