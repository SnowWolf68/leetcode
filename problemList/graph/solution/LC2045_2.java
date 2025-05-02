package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 对于示例2: n = 2, edges = [[1,2]], time = 3, change = 2
 * 只要设置second[0] = INF, 这样当更新到n - 1的最短路时, 如果n - 1的次短路还没有更新
 * 那么由于这里我们允许重复入堆, 那么n - 1的最短路也会入堆, 又会从n - 1的最短路dijkstra到0, 然后再从0dijkstra到n - 1
 * 这样算出来的次短路就符合示例2的要求
 */
public class LC2045_2 {
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
        // second[0] = 0;
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

        return second[n - 1];
    }
}
