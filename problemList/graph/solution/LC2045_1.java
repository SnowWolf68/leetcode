package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 边权的处理: 通过之前做的 LC3342, LC2577, 不难想出这题边权的处理方式
 * 严格次短路: 可以维护两个dist数组, dist1[]和dist2[], 在dijkstra的同时更新这两个数组的值
 * 需要注意的是, 这里由于我们维护了两个dist数组, 也就是同时维护了最短路和次短路两条路径
 * 因此对于同一个节点而言, 其可能同时出现在最短路和次短路中
 * 在最短路的dijkstra中, 一旦某个节点的最短路确定之后, 后续就不会再考虑这个节点
 * 但是这里由于次短路的存在, 因此这个节点还可能再
 * 
 * 注意: 这是稀疏图
 * why?  
 * 2 <= n <= 104
 * n - 1 <= edges.length <= min(2 * 1e4, n * (n - 1) / 2)
 * 当n较大时(接近1e4), 边数不会超过2 * 1e4, 边数和点数差不多是同一个数量级, 因此是稀疏图
 * 
 * 更新dist, second的时候需要注意: 这里说的次短路是 严格次短路 , 因此更新second的时候需要加一个条件: curTime + w != dist[i]
 */
public class LC2045_1 {
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        List<Integer>[] g = new List[n];
        int INF = 0x3f3f3f3f;
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0] - 1].add(e[1] - 1);
            g[e[1] - 1].add(e[0] - 1);
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        int[] second = new int[n];
        Arrays.fill(second, INF);
        second[0] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{0, 0});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            int curTime = poll[1];
            for(int i : g[poll[0]]){
                int w = time + ((curTime / change) % 2 == 0 ? 0 : change - (curTime % change));
                if(curTime + w < dist[i]){
                    second[i] = dist[i];
                    dist[i] = curTime + w;
                    minHeap.offer(new int[]{i, dist[i]});
                    minHeap.offer(new int[]{i, second[i]});
                }else if(curTime + w < second[i] && curTime + w != dist[i]){    // 必须要加curTime + w != dist[i], 这样才能保证其中的每一步都是严格第二大
                    second[i] = curTime + w;
                    minHeap.offer(new int[]{i, second[i]});
                }
            }
        }

        if(second[n - 1] == INF && dist[n - 1] != INF){
            System.out.println("aaa");
            int time1 = dijkstra(n, n - 1, 0, g, dist[n - 1], time, change);
            return dijkstra(n, 0, n - 1, g, time1, time, change);
        }

        return second[n - 1];
    }

    private int dijkstra(int n, int start, int end, List<Integer>[] g, int startTime, int time, int change){
        int INF = 0x3f3f3f3f;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[start] = startTime;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{start, startTime});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            int curTime = poll[1];
            for(int i : g[poll[0]]){
                int w = time + ((curTime / change) % 2 == 0 ? 0 : change - (curTime % change));
                if(curTime + w < dist[i]){
                    dist[i] = curTime + w;
                    minHeap.offer(new int[]{i, dist[i]});
                }
            }
        }
        return dist[end];
    }
}
