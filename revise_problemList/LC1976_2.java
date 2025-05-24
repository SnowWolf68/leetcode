package revise_problemList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
灵神的解法: 在计算最短路的同时dp
原理: dijkstra计算最短路的过程, 对于可能的最短路而言, dijkstra的顺序, 就是多条最短路的拓扑序
更新方式: 
    1. 如果拓展到的j找到一条新的最短路时, cnt[j] = cnt[t];
    2. 如果当前这条路就是j的最短路, cnt[j] += cnt[t]
 */
public class LC1976_2 {
    public int countPaths(int n, int[][] roads) {
        int INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] r : roads){
            g[r[0]][r[1]] = r[2];
            g[r[1]][r[0]] = r[2];
        }
        boolean[] done = new boolean[n];
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE / 2);
        dist[0] = 0;
        int[] cnt = new int[n];
        cnt[0] = 1;
        for(int i = 0;i < n;i++){
            int t = -1;
            long minDis = Long.MAX_VALUE / 2;
            for(int j = 0;j < n;j++){
                if(!done[j] && dist[j] < minDis){
                    minDis = dist[j];
                    t = j;
                }
            }
            if(t == -1) break;
            done[t] = true;
            for(int j = 0;j < n;j++){
                if(!done[j] && g[t][j] != INF && dist[t] + g[t][j] < dist[j]){
                    dist[j] = dist[t] + g[t][j];
                    cnt[j] = cnt[t];
                }
                else if(!done[j] && g[t][j] != INF && dist[t] + g[t][j] == dist[j]) cnt[j] = (cnt[t] + cnt[j]) % MOD;
            }
        }
        return cnt[n - 1];
    }
}
