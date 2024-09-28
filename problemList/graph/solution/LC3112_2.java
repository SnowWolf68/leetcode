package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
朴素Dijkstra的时空复杂度都是O(n * 2), 这题点的数量级为5 * 10 ^ 4, O(n ^ 2)
 */
public class LC3112_2 {
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        int INF = 0x3f3f3f3f;
        List<int[]>[] g = new ArrayList[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            // 这里即使存在重复添加的可能, 但是其实也没有影响
            // 因为后面的更新过程能够保证最后得到的一定是最短的那个weight更新出来的结果
            g[e[0]].add(new int[]{e[1], e[2]});
            g[e[1]].add(new int[]{e[0], e[2]});
        }
        // dijkstra
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        minHeap.offer(new int[]{0, 0});
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            int distx = poll[0], x = poll[1];
            if(distx > dist[x]) continue;
            for(int[] nxt : g[x]){
                dist[nxt[0]] = Math.min(dist[x] + nxt[1], dist[nxt[0]]);
                if(dist[nxt[0]] >= disappear[nxt[0]]) dist[nxt[0]] = INF;
                if(dist[nxt[0]] != INF) minHeap.offer(new int[]{dist[nxt[0]], nxt[0]});
            }
        }
        for(int i = 0;i < n;i++){
            if(dist[i] == INF) dist[i] = -1;
        }
        return dist;
    }
}
