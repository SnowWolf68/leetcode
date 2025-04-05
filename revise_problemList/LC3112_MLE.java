package revise_problemList;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LC3112_MLE {
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        int[][] g = new int[n][n];
        int INF = 0x3f3f3f3f;
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] e : edges){
            if(e[2] < g[e[0]][e[1]]){
                g[e[0]][e[1]] = e[2];
                g[e[1]][e[0]] = e[2];
            }
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        boolean[] done = new boolean[n];
        done[0] = true;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for(int i = 0;i < n;i++){
            if(g[0][i] != INF && g[0][i] < disappear[i]){
                dist[i] = Math.min(dist[i], g[0][i]);
                minHeap.offer(new int[]{i, dist[i]});
            }
        }
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            if(done[poll[0]]) continue;
            done[poll[0]] = true;
            for(int i = 0;i < n;i++){
                if(g[poll[0]][i] != INF && dist[poll[0]] + g[poll[0]][i] < disappear[i]
                    && dist[poll[0]] + g[poll[0]][i] < dist[i]){
                    dist[i] = dist[poll[0]] + g[poll[0]][i];
                    minHeap.offer(new int[]{i, dist[i]});
                }
            }
        }
        int[] answer = new int[n];
        for(int i = 0;i < n;i++){
            answer[i] = dist[i] == INF ? -1 : dist[i];
        }
        return answer;
    }
}
