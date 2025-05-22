package notes.graph.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class LC743_4 {
    public int networkDelayTime(int[][] times, int n, int k) {
        int INF = 0x3f3f3f3f;
        k--;
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] t : times){
            g[t[0] - 1].add(new int[]{t[1] - 1, t[2]});
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
            for(int[] nxt : g[poll[0]]){
                if(dist[poll[0]] + nxt[1] < dist[nxt[0]]){
                    dist[nxt[0]] = dist[poll[0]] + nxt[1];
                    minHeap.offer(new int[]{nxt[0], dist[nxt[0]]});
                }
            }
        }
        int max = 0;
        for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
        return max == INF ? -1 : max;
    }
}
