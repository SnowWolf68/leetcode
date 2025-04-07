package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
bfs不加vis, 时间复杂度为O(n ^ 2), 有卡n ^ 2的数据
 */
public class LC3123 {
    public boolean[] findAnswer(int n, int[][] edges) {
        int INF = 0x3f3f3f3f, m = edges.length;
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
            g[e[1]].add(new int[]{e[0], e[2]});
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{0, 0});
        boolean[] vis = new boolean[n];
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(vis[poll[0]]) continue;
            vis[poll[0]] = true;
            for(int[] nxt : g[poll[0]]){
                if(!vis[nxt[0]] && dist[poll[0]] + nxt[1] < dist[nxt[0]]){
                    dist[nxt[0]] = dist[poll[0]] + nxt[1];
                    minHeap.offer(new int[]{nxt[0], dist[nxt[0]]});
                }
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        Map<Integer, Set<Integer>> map = new HashMap<>();
        vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            if(vis[poll]) continue;
            vis[poll] = true;
            for(int[] nxt : g[poll]){
                if(dist[poll] == dist[nxt[0]] + nxt[1]) {
                    Set<Integer> set = map.getOrDefault(poll, new HashSet<>());
                    set.add(nxt[0]);
                    map.put(poll, set);
                    queue.offer(nxt[0]);
                }
            }
        }
        boolean[] ans = new boolean[m];
        for(int i = 0;i < m;i++){
            int[] e = edges[i];
            if(map.getOrDefault(e[0], new HashSet<>()).contains(e[1]) || map.getOrDefault(e[1], new HashSet<>()).contains(e[0])) ans[i] = true;
        }
        return ans;
    }
}
