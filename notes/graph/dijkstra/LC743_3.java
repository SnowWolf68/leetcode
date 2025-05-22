package notes.graph.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class LC743_3 {
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
        for(int i = 0;i < n;i++){
            int t = -1, minDis = INF;
            for(int j = 0;j < n;j++){
                if(!done[j] && dist[j] < minDis){
                    minDis = dist[j];
                    t = j;
                }
            }
            if(t == -1) break;
            done[t] = true;
            for(int[] nxt : g[t]){
                if(dist[t] + nxt[1] < dist[nxt[0]]){
                    dist[nxt[0]] = dist[t] + nxt[1];
                }
            }
        }
        int max = 0;
        for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
        return max == INF ? -1 : max;
    }
}
