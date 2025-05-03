package revise_problemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 邻接表 + 堆优化dijkstra
 * 
 * 时间复杂度: O(e * loge)
 * 分析过程: 
 *      1. 堆中最多有e个元素, 因此 int[] poll = minHeap.poll(); 至多执行O(e)次, 每次都需要O(loge)的时间
 *      2. for(int[] nxt : g[poll[0]]) 这个循环至多执行e次, 每次循环中可能调用一次 offer , 每次offer的时间复杂度为O(loge)
 * 因此总的复杂度就是 O(e * loge)
 * 
 * 通过上面的分析可以看出, 这个时间复杂度仅限于邻接表存图, 如果对于邻接矩阵存图, 那么每次使用某个确定最短距离的点松弛时, 都要花费O(n)的时间来遍历邻接边, 那么时间复杂度就不是O(e * loge)
 */
public class LC743_2 {
    public int networkDelayTime(int[][] times, int n, int k) {
        k--;
        int INF = 0x3f3f3f3f;
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : times){
            g[e[0] - 1].add(new int[]{e[1] - 1, e[2]});
        }
        int[] dist = new int[n];
        boolean[] done = new boolean[n];
        Arrays.fill(dist, INF);
        dist[k] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{k, 0});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(done[poll[0]]) continue;
            done[poll[0]] = true;
            for(int[] nxt : g[poll[0]]){
                int i = nxt[0], w = nxt[1];
                if(dist[poll[0]] + w < dist[i]){
                    dist[i] = dist[poll[0]] + w;
                    minHeap.offer(new int[]{i, dist[i]});
                }
            }
        }
        int max = 0;
        for(int dis : dist) max = Math.max(max, dis);
        return max >= INF ? -1 : max;
    }
}
