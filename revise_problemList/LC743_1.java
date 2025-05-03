package revise_problemList;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
注意这是有向图

对于堆优化的dijkstra, 最好的存图方式是邻接表, 如果用邻接矩阵虽然可以, 但是时间复杂度要大于O(e * loge)
 */
public class LC743_1 {
    public int networkDelayTime(int[][] times, int n, int k) {
        k--;
        int INF = 0x3f3f3f3f;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] time : times){
            g[time[0] - 1][time[1] - 1] = time[2];
        }
        int[] dist = new int[n];
        boolean[] done = new boolean[n];
        Arrays.fill(dist, INF);
        dist[k] = 0;
        done[k] = true;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for(int i = 0;i < n;i++){
            if(g[k][i] != INF) {
                minHeap.offer(new int[]{i, g[k][i]});
                dist[i] = g[k][i];
            }
        }
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(done[poll[0]]) continue;
            done[poll[0]] = true;
            for(int i = 0;i < n;i++){
                if(g[poll[0]][i] != INF){
                    if(dist[poll[0]] + g[poll[0]][i] < dist[i]){
                        dist[i] = dist[poll[0]] + g[poll[0]][i];
                        minHeap.offer(new int[]{i, dist[i]});
                    }
                }
            }
        }
        int max = 0;
        for(int dis : dist) max = Math.max(max, dis);
        return max >= INF ? -1 : max;
    }
}
