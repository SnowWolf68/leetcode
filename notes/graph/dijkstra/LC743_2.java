package notes.graph.dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LC743_2 {
    public int networkDelayTime(int[][] times, int n, int k) {
        int INF = 0x3f3f3f3f;
        k--;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] t : times){
            g[t[0] - 1][t[1] - 1] = t[2];
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        boolean[] done = new boolean[n];
        dist[k] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{k, 0});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(done[poll[0]]) continue;
            done[poll[0]] = true;
            for(int j = 0;j < n;j++){
                if(g[poll[0]][j] != INF && dist[poll[0]] + g[poll[0]][j] < dist[j]){
                    dist[j] = dist[poll[0]] + g[poll[0]][j];
                    minHeap.offer(new int[]{j, dist[j]});
                }
            }
        }
        int max = 0;
        for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
        return max == INF ? -1 : max;
    }
}
