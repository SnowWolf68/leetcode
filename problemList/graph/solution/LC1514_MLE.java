package problemList.graph.solution;


/**
这题的节点个数到了1e4, 朴素dijkstra会爆内存
 */
public class LC1514_MLE {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        double[][] g = new double[n][n];
        for(int i = 0;i < edges.length;i++){
            g[edges[i][0]][edges[i][1]] = succProb[i];
            g[edges[i][1]][edges[i][0]] = succProb[i];
        }
        // 朴素dijkstra
        double[] p = new double[n];
        p[start_node] = 1;
        boolean[] vis = new boolean[n];
        for(int i = 0;i < n;i++){
            int t = -1;
            for(int j = 0;j < n;j++){
                if(!vis[j] && (t < 0 || p[j] > p[t])) t = j;
            }
            vis[t] = true;
            for(int j = 0;j < n;j++){
                p[j] = Math.max(p[j], p[t] * g[t][j]);
            }
        }
        return p[end_node];
    }
}
