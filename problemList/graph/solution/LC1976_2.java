package problemList.graph.solution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
注: 我这种写法其实是按照 逆拓扑序 从后往前dp 的, 也可以按照正拓扑序, 从前往后dp
具体见三叶姐的题解: https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/solutions/1641204/by-ac_oier-4ule/
 */
public class LC1976_2 {
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

        // 稠密图(m ~ n ^ 2), 使用朴素dijksra更快
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
                if(g[t][j] != INF && dist[t] + g[t][j] < dist[j]){
                    dist[j] = dist[t] + g[t][j];
                }
            }
        }

        // PriorityQueue<long[]> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        // minHeap.offer(new long[]{0, 0});
        // while (!minHeap.isEmpty()) {
        //     long[] poll = minHeap.poll();
        //     if(vis[(int)poll[0]]) continue;
        //     vis[(int)poll[0]] = true;
        //     for(int i = 0;i < n;i++){
        //         if(g[(int)poll[0]][i] != INF && dist[(int)poll[0]] + g[(int)poll[0]][i] < dist[i]){
        //             dist[i] = dist[(int)poll[0]] + g[(int)poll[0]][i];
        //             minHeap.offer(new long[]{i, dist[i]});
        //         }
        //     }
        // }

        // 拓扑序dp
        // 这里我的想法是将所有最短路径重新构造一个有向图, 在这个有向图上求拓扑序, 并dp
        int[][] topoG = new int[n][n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        boolean[] vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            if(vis[poll]) continue;
            vis[poll] = true;
            for(int i = 0;i < n;i++){
                if(g[poll][i] != INF && dist[i] + g[i][poll] == dist[poll]) {
                    topoG[i][poll] = 1;
                    queue.offer(i);
                }
            }
        }
        int[] out = new int[n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                if(topoG[i][j] == 1) out[i]++;
            }
        }
        int[] dp = new int[n];
        dp[n - 1] = 1;
        queue.clear();
        queue.offer(n - 1);
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int i = 0;i < n;i++){
                if(topoG[i][poll] == 1) {
                    if(--out[i] == 0) queue.offer(i);
                    dp[i] = (dp[i] + dp[poll]) % MOD;
                }
            }
        }
        return dp[0];
    }
}
