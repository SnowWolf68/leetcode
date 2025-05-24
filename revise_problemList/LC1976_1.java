package revise_problemList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
dist要用long
 */
public class LC1976_1 {
    public int countPaths(int n, int[][] roads) {
        int INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] r : roads){
            g[r[0]][r[1]] = r[2];
            g[r[1]][r[0]] = r[2];
        }
        boolean[] done = new boolean[n];
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE / 2);
        dist[0] = 0;
        for(int i = 0;i < n;i++){
            int t = -1;
            long minDis = Long.MAX_VALUE / 2;
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
        boolean[] vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            if(vis[poll]) continue;
            vis[poll] = true;
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
                    cnt[j] = (cnt[poll] + cnt[j]) % MOD;
                }
            }
        }
        return cnt[n - 1];
    }
}
