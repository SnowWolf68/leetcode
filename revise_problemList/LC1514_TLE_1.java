package revise_problemList;

import java.util.PriorityQueue;

public class LC1514_TLE_1 {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        double[][] g = new double[n][n];
        for(int i = 0;i < succProb.length;i++){
            g[edges[i][0]][edges[i][1]] = succProb[i];
            g[edges[i][1]][edges[i][0]] = succProb[i];
        }
        double[] dist = new double[n];
        dist[start_node] = 1;
        PriorityQueue<double[]> minHeap = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
        minHeap.offer(new double[]{start_node, 1});
        while (!minHeap.isEmpty()) {
            double[] poll = minHeap.poll();
            int cur = (int)poll[0];
            for(int i = 0;i < n;i++){
                if(g[cur][i] != 0 && dist[cur] * g[cur][i] > dist[i]){
                    dist[i] = dist[cur] * g[cur][i];
                    minHeap.offer(new double[]{i, dist[i]});
                }
            }
        }
        return dist[end_node];
    }
}
