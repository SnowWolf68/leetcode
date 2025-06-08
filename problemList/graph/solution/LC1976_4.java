package problemList.graph.solution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
LC1976_1用的是逆拓扑序, 这里使用正拓扑序
 */
public class LC1976_4 {
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
        boolean[] vis = new boolean[n];
        PriorityQueue<long[]> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        minHeap.offer(new long[]{0, 0});
        while (!minHeap.isEmpty()) {
            long[] poll = minHeap.poll();
            if(vis[(int)poll[0]]) continue;
            vis[(int)poll[0]] = true;
            for(int i = 0;i < n;i++){
                if(g[(int)poll[0]][i] != INF && dist[(int)poll[0]] + g[(int)poll[0]][i] < dist[i]){
                    dist[i] = dist[(int)poll[0]] + g[(int)poll[0]][i];
                    minHeap.offer(new long[]{i, dist[i]});
                }
            }
        }
        // 拓扑序dp
        // 这里我的想法是将所有最短路径重新构造一个有向图, 在这个有向图上求拓扑序, 并dp
        int[][] topoG = new int[n][n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            if(vis[poll]) continue;
            vis[poll] = true;
            for(int i = 0;i < n;i++){
                if(g[poll][i] != INF && dist[poll] + g[poll][i] == dist[i]) {
                    topoG[poll][i] = 1;
                    queue.offer(i);
                }
            }
        }
        int[] in = new int[n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                if(topoG[i][j] == 1) in[j]++;
            }
        }
        int[] dp = new int[n];
        dp[0] = 1;
        queue.clear();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int i = 0;i < n;i++){
                if(topoG[poll][i] == 1) {
                    if(--in[i] == 0) queue.offer(i);
                    dp[i] = (dp[i] + dp[poll]) % MOD;
                }
            }
        }
        return dp[n - 1];
    }
}
