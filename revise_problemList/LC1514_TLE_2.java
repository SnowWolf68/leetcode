package revise_problemList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class LC1514_TLE_2 {
    public static double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<double[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < succProb.length;i++){
            g[edges[i][0]].add(new double[]{edges[i][1], succProb[i]});
            g[edges[i][1]].add(new double[]{edges[i][0], succProb[i]});
        }
        double[] dist = new double[n];
        dist[start_node] = 1;
        PriorityQueue<double[]> minHeap = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
        minHeap.offer(new double[]{start_node, 1});
        // boolean[] done = new boolean[n];
        while (!minHeap.isEmpty()) {
            double[] poll = minHeap.poll();
            int cur = (int)poll[0];
            // if(done[cur]) continue;
            // done[cur] = true;
            for(double[] nxtE : g[cur]){
                int nxt = (int)nxtE[0];
                double prob = nxtE[1];
                if(dist[cur] * prob > dist[nxt]){
                    dist[nxt] = dist[cur] * prob;
                    minHeap.offer(new double[]{nxt, dist[nxt]});
                }
            }
        }
        return dist[end_node];
    }

    public static double maxProbability2(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
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

    /**
     * 对拍来自deepseek
     */
    // 对拍器核心逻辑
    public static void main(String[] args) {
        final int MAX_CASES = 1000;
        final double EPSILON = 1e-9;
        Random rand = new Random();

        for (int caseNum = 1; caseNum <= MAX_CASES; caseNum++) {
            // 生成随机测试用例
            int n = rand.nextInt(100) + 2; // 2-11个节点
            int[][] edges = generateEdges(n, rand);
            double[] succProb = generateProbs(edges.length, rand);
            int start = rand.nextInt(n);
            int end;
            do {
                end = rand.nextInt(n);
            } while (start == end);

            // 计算结果
            double original = maxProbability(n, edges, succProb, start, end);
            double correct = maxProbability2(n, edges, succProb, start, end);

            // 比较结果
            if (Math.abs(original - correct) > EPSILON) {
                System.out.println("差异发现！测试用例 #" + caseNum);
                printTestCase(n, edges, succProb, start, end);
                System.out.println("原版结果: " + original);
                System.out.println("正确结果: " + correct);
                return;
            }
        }
        System.out.println(MAX_CASES + " 个测试用例全部通过！");
    }

    // 生成随机边
    private static int[][] generateEdges(int n, Random rand) {
        Set<String> edgeSet = new HashSet<>();
        int maxEdges = n * (n - 1) / 2;
        int m = rand.nextInt(maxEdges + 1);

        while (edgeSet.size() < m) {
            int a = rand.nextInt(n);
            int b;
            do {
                b = rand.nextInt(n);
            } while (a == b);
            
            String edge1 = a + "," + b;
            String edge2 = b + "," + a;
            if (!edgeSet.contains(edge1) && !edgeSet.contains(edge2)) {
                edgeSet.add(edge1);
            }
        }

        int[][] edges = new int[m][2];
        int idx = 0;
        for (String edge : edgeSet) {
            String[] parts = edge.split(",");
            edges[idx][0] = Integer.parseInt(parts[0]);
            edges[idx][1] = Integer.parseInt(parts[1]);
            idx++;
        }
        return edges;
    }

    // 生成随机概率
    private static double[] generateProbs(int length, Random rand) {
        double[] probs = new double[length];
        for (int i = 0; i < length; i++) {
            probs[i] = rand.nextDouble();
        }
        return probs;
    }

    // 打印测试用例
    private static void printTestCase(int n, int[][] edges, double[] probs, int start, int end) {
        System.out.println("节点数: " + n);
        System.out.println("边列表:");
        for (int i = 0; i < edges.length; i++) {
            System.out.printf("%d-%d (概率: %.4f)\n", 
                edges[i][0], edges[i][1], probs[i]);
        }
        System.out.println("起点: " + start + "，终点: " + end);
    }
}
