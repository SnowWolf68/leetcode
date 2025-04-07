package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
先dijksra处理得到 distanceToLastNode(i)
然后dp
 */
public class LC1786 {
    public int countRestrictedPaths(int n, int[][] edges) {
        int INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0] - 1].add(new int[]{e[1] - 1, e[2]});
            g[e[1] - 1].add(new int[]{e[0] - 1, e[2]});
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[n - 1] = 0;
        boolean[] done = new boolean[n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{n - 1, 0});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(done[poll[0]]) continue;
            done[poll[0]] = true;
            for(int[] nxt : g[poll[0]]){
                if(dist[poll[0]] + nxt[1] < dist[nxt[0]]){
                    dist[nxt[0]] = dist[poll[0]] + nxt[1];
                    minHeap.offer(new int[]{nxt[0], dist[nxt[0]]});
                }
            }
        }
        // dist[i] = distanceToLastNode(i)
        // dp
        // dp[i]: 从i到n的首先路径数量
        // dp[i] += dp[nxt], nxt为i的邻接点
        // dp顺序: 从n - 1节点开始, 按照bfs顺序进行dp, 刷表法
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        int[] dp = new int[n];
        dp[n - 1] = 1;
        boolean[] vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            vis[poll] = true;
            for(int[] nxt : g[poll]){
                if(dist[nxt[0]] > dist[poll]) {
                    if(!vis[nxt[0]]) queue.offer(nxt[0]);
                    dp[nxt[0]] = (dp[nxt[0]] + dp[poll]) % MOD;
                }
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[0];
    }
}
