package revise_problemList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
超时原因: 按照最短路使用bfs建图的时候没加vis数组, bfs可能会死循环
 */
public class LC1976_TLE {
    public int countPaths(int n, int[][] roads) {
        int INF = 0x3f3f3f3f;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] r : roads){
            g[r[0]][r[1]] = r[2];
            g[r[1]][r[0]] = r[2];
        }
        boolean[] done = new boolean[n];
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
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
                if(!done[j] && g[t][j] != INF && dist[t] + g[t][j] < dist[j]){
                    dist[j] = dist[t] + g[t][j];
                }
            }
        }
        // 正序拓扑序dp
        int[][] topoG = new int[n][n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int i = 0;i < n;i++){
                if(dist[i] + g[i][poll] == dist[poll]) {
                    queue.offer(i);
                    topoG[i][poll] = 1;
                }
            }
        }
        int[] in = new int[n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                in[i] += topoG[j][i];
            }
        }
        queue.offer(0);
        int[] cnt = new int[n];
        cnt[0] = 1;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int j = 0;j < n;j++){
                if(topoG[poll][j] == 1) {
                    in[j]--;
                    if(in[j] == 0) queue.offer(j);
                    cnt[j] += cnt[poll];
                }
            }
        }
        return cnt[n - 1];
    }
}
