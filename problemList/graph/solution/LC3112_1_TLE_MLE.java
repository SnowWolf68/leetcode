package problemList.graph.solution;

import java.util.Arrays;

/**
朴素Dijkstra的时空复杂度都是O(n * 2), 这题点的数量级为5 * 10 ^ 4, O(n ^ 2)
 */
public class LC3112_1_TLE_MLE {
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        int INF = 0x3f3f3f3f;
        int[][] g = new int[n][n];
        for(int[] row : g) Arrays.fill(row, INF);
        for(int[] e : edges) {
            // 因为给的数据中两个点之间可能有多条边, 因此这里需要去一个最小值
            g[e[0]][e[1]] = Math.min(g[e[0]][e[1]], e[2]);
            g[e[1]][e[0]] = Math.min(g[e[0]][e[1]], e[2]);
        }
        // dijkstra
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        boolean[] vis = new boolean[n];
        for(int i = 0;i < n;i++){
            int t = -1;
            for(int j = 0;j < n;j++){
                if(!vis[j] && (t < 0 || dist[j] < dist[t])) t = j;
            }
            vis[t] = true;
            for(int j = 0;j < n;j++){
                dist[j] = Math.min(dist[j], dist[t] + g[t][j]);
                // 和普通的dikjstra的区别就在这里, 如果到达当前这个点的时间(路径长度) 超过了disappear[j]
                // 说明在到达当前这个点之前, 这个点就消失了, 因此需要让dist[j]置为INF
                if(dist[j] >= disappear[j]) dist[j] = INF;
            }
        }
        for(int i = 0;i < n;i++){
            if(dist[i] == INF) dist[i] = -1;
        }
        return dist;
    }
}
