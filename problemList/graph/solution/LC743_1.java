package problemList.graph.solution;

import java.util.Arrays;

/**
朴素Dijkstra

朴素dijkstra适合用于稠密图
时间复杂度: O(n * 2), 其中n是顶点数
空间复杂度: O(n * 2), 其中n是顶点数

准确来说, 朴素dijkstra的时间复杂度是O(n ^ 2 + m), 只不过在稠密图中, n >> m, 因此时间复杂度为O(n ^ 2)

具体时间复杂度的分析可以看 OI Wiki: https://oi-wiki.org/graph/shortest-path/#dijkstra-%E7%AE%97%E6%B3%95
 */
public class LC743_1 {
    public int networkDelayTime(int[][] times, int n, int k) {
        // 邻接矩阵建图
        int m = times.length, INF = 0x3f3f3f3f;
        int[][] g = new int[n][n];
        for(int[] row : g) Arrays.fill(row, INF);
        for(int i = 0;i < m;i++){
            int from = times[i][0], to = times[i][1], weight = times[i][2];
            g[from - 1][to - 1] = weight;
        }
        // dijkstra
        int[] dist = new int[n];
        Arrays.fill(dist, INF); 
        dist[k - 1] = 0;
        boolean[] done = new boolean[n];
        for(int i = 0;i < n;i++){
            // 找到最小的dist[t]
            int t = -1;
            for(int j = 0;j < n;j++){
                if(!done[j] && (t < 0 || dist[j] < dist[t])) t = j;
            }
            // 标记t为已经更新过
            done[t] = true;
            // 使用t更新t的所有邻接点
            for(int j = 0;j < n;j++){
                dist[j] = Math.min(dist[j], dist[t] + g[t][j]);
            }
        }
        // 返回max(dist[i])
        int ret = -1;
        for(int i = 0;i < n;i++) ret = Math.max(ret, dist[i]);
        return ret == INF ? -1 : ret;
    }
}
