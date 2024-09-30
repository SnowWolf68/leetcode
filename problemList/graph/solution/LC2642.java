package problemList.graph.solution;

import java.util.Arrays;

public class LC2642 {
    class Graph{
        private int n, INF = 0x3f3f3f3f;
        private int[][] g;
        public Graph(int _n, int[][] edges) {
            n = _n;
            g = new int[n][n];
            for(int[] row : g) Arrays.fill(row, INF);
            for(int[] e : edges){
                g[e[0]][e[1]] = e[2];
            }
        }
        
        public void addEdge(int[] edge) {
            g[edge[0]][edge[1]] = edge[2];
        }
        
        public int shortestPath(int node1, int node2) {
            // 稠密图, 用朴素dijkstra
            int[] dist = new int[n];
            Arrays.fill(dist, INF);
            dist[node1] = 0;
            boolean[] vis = new boolean[n];
            for(int i = 0;i < n;i++){
                int t = -1;
                for(int j = 0;j < n;j++){
                    if(!vis[j] && (t < 0 || dist[j] < dist[t])) t = j;
                }
                vis[t] = true;
                for(int j = 0;j < n;j++){
                    dist[j] = Math.min(dist[j], dist[t] + g[t][j]);
                }
            }
            for(int i = 0;i < n;i++) dist[i] = dist[i] == INF ? -1 : dist[i];
            return dist[node2];
        }
    }
    /**
     * Your Graph object will be instantiated and called as such:
     * Graph obj = new Graph(n, edges);
     * obj.addEdge(edge);
     * int param_2 = obj.shortestPath(node1,node2);
     */
}