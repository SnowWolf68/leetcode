package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
堆优化Dijkstra
堆优化dijkstra适用于稀疏图
时间复杂度: O(m * logm), 其中m是边数
空间复杂度: O(m), 其中m是边数   (因为使用的是邻接表存图)

思想: 使用小根堆来维护<disti, i>的关系, 并且按照disti来创建小根堆
每次取出堆顶元素, 即disti最小的节点, 并使用i这个节点来更新其所有的邻接点, 并且把更新的这些节点入堆

在堆优化dijkstra中, 可以像之前一样, 使用vis/done来标记dist[i]是否被更新过
但是这里还有一种更简单的方法, 即直接比较disti和dist[i]的关系即可, 如果disti > dist[i], 说明i这个节点在之前已经被更新过了, 不需要再更新了

由于我们每次都会将i的邻接点更新之后再入堆, 因此就有可能会将一些点重复加入到堆中, 但是由于这里我们做了disti > dist[i]的判断, 因此即使遇到重复的节点, 也不会有影响
 */
public class LC743_2 {
    public int networkDelayTime(int[][] times, int n, int k) {
        // 邻接表建图
        int m = times.length, INF = 0x3f3f3f3f;
        List<int[]>[] g = new ArrayList[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] row : times){
            g[row[0] - 1].add(new int[]{row[1] - 1, row[2]});
        }
        // 堆优化dijkstra
        int[] dist = new int[n];
        Arrays.fill(dist, INF); 
        dist[k - 1] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        minHeap.offer(new int[]{0, k - 1});
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            int disti = poll[0], i = poll[1];
            // 如果从小根堆中取出的disti比实际的dist[i]还大, 那么就没必要使用disti来更新了
            if(disti > dist[i]) continue;
            for(int[] e : g[i]){
                /**
                一个小细节, 这里只有当dist[i] + e[1] < dist[e[0]]时, 再将新更新出来的节点添加到小根堆当中去
                如果直接dist[e[0]] = Math.min(dist[e[0]], dist[i] + dist[i]);
                然后直接minHeap.offer(new int[]{dist[e[0]], e[0]}); 
                的话, 会导致不论是否更新到最短的, 都往小根堆中放, 那么此时如果图中存在环路, 那么就导致虽然dist[e[0]]一直保持不变, 
                但是会一直往小根堆中重复添加节点, 因此会导致死循环
                不理解的话可以考虑如下示例: 
                    times = [[1,2,1],[2,1,3]],   n = 2,    k = 2
                 */
                if(dist[e[0]] > dist[i] + e[1]){
                    dist[e[0]] = dist[i] + e[1];
                    minHeap.offer(new int[]{dist[e[0]], e[0]});
                }
            }
        }
        // 返回max(dist[i])
        int ret = -1;
        for(int i = 0;i < n;i++) ret = Math.max(ret, dist[i]);
        return ret == INF ? -1 : ret;
    }
}
