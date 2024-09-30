package problemList.graph.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
堆优化dijkstra
时间复杂度: O(m * logm)     其中m是边数

注意比较的细节, 要用Double.compare(), 否则会T
 */
public class LC1514_1 {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<double[]>[] g = new ArrayList[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < edges.length;i++){
            int from = edges[i][0], to = edges[i][1];
            g[from].add(new double[]{to, succProb[i]});
            g[to].add(new double[]{from, succProb[i]});
        }
        double[] p = new double[n];
        // PriorityQueue<double[]> maxHeap = new PriorityQueue<>((o1, o2) -> (int)(o2[0] - o1[0]));
        // 用上面那行就会T, 但是用Double.compare()就不会
        PriorityQueue<double[]> maxHeap = new PriorityQueue<>((o1, o2) -> Double.compare(o2[0], o1[0]));
        p[start_node] = 1;
        maxHeap.offer(new double[]{1, start_node});
        while(!maxHeap.isEmpty()){
            double[] poll = maxHeap.poll();
            double curP = poll[0];
            int x = (int)poll[1];
            if(curP < p[x]) continue;
            for(double[] nxt : g[x]){
                double newP = curP * nxt[1];
                if(newP > p[(int)nxt[0]]){
                    p[(int)nxt[0]] = newP;
                    maxHeap.offer(new double[]{newP, nxt[0]});
                }
            }
        }
        return p[end_node];
    }
}
