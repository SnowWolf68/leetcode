package revise_problemList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LC1976_WA {
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
        // bfs + 刷表法 是错误的
        int[] cnt = new int[n];
        cnt[n - 1] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int j = 0;j < n;j++){
                if(dist[j] + g[j][poll] == dist[poll]){
                    queue.offer(j);
                    cnt[j] += cnt[poll];
                }
            }
        }
        return cnt[0];
    }
}
