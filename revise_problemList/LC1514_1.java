package revise_problemList;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LC1514_1 {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<double[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < succProb.length;i++){
            g[edges[i][0]].add(new double[]{edges[i][1], succProb[i]});
            g[edges[i][1]].add(new double[]{edges[i][0], succProb[i]});
        }
        double[] dist = new double[n];
        dist[start_node] = 1;
        PriorityQueue<double[]> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b[1], a[1]));
        maxHeap.offer(new double[]{start_node, 1});
        boolean[] done = new boolean[n];
        while (!maxHeap.isEmpty()) {
            double[] poll = maxHeap.poll();
            int cur = (int)poll[0];
            if(done[cur]) continue;
            done[cur] = true;
            for(double[] nxtE : g[cur]){
                int nxt = (int)nxtE[0];
                double prob = nxtE[1];
                if(dist[cur] * prob > dist[nxt]){
                    dist[nxt] = dist[cur] * prob;
                    maxHeap.offer(new double[]{nxt, dist[nxt]});
                }
            }
        }
        return dist[end_node];
    }
}
