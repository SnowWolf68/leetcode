package notes.graph.dijkstra;

import java.util.Arrays;

public class LC743_1 {
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
            for(int j = 0;j < n;j++){
                if(g[t][j] != INF && dist[t] + g[t][j] < dist[j]){
                    dist[j] = dist[t] + g[t][j];
                }
            }
        }
        int max = 0;
        for(int i = 0;i < n;i++) max = Math.max(max, dist[i]);
        return max == INF ? -1 : max;
    }
}
