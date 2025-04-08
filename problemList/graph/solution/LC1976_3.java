package problemList.graph.solution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
TODO: 灵神题解: https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/solutions/2668041/zai-ji-suan-zui-duan-lu-de-tong-shi-dpfu-g4f3/
在计算最短路的同时dp, 这种情况下就只能从前往后dp
 */
public class LC1976_3 {
    public int countPaths(int n, int[][] roads) {
        int INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] e : roads){
            g[e[0]][e[1]] = e[2];
            g[e[1]][e[0]] = e[2];
        }
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        int[] dp = new int[n];
        dp[0] = 1;  // dp[i]: 0 ~ i的最短路条数
        boolean[] done = new boolean[n];
        for(int i = 0;i < n;i++){
            int t = -1;
            long min = Long.MAX_VALUE;
            for(int j = 0;j < n;j++){
                if(!done[j] && dist[j] < min){
                    min = dist[j];
                    t = j;
                }
            }
            done[t] = true;
            for(int j = 0;j < n;j++){
                if(g[t][j] != INF){
                    // 这种dp的状态转移方式是不是有点熟悉?
                    if(dist[t] + g[t][j] < dist[j]){
                        dist[j] = dist[t] + g[t][j];
                        dp[j] = dp[t];
                    }else if(dist[t] + g[t][j] == dist[j]) dp[j] = (dp[j] + dp[t]) % MOD;
                }
            }
        }
        return dp[n - 1];
    }
}
