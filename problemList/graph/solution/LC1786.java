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
dp的顺序需要好好考虑
自己拿一个示例来模拟一下, 就可以发现这种顺序dp的正确性
 */
public class LC1786 {
    public int countRestrictedPaths(int n, int[][] edges) {
        int INF = Integer.MAX_VALUE, MOD = (int)1e9 + 7;    // 这题太有意思了, 甚至有一组卡INF = 0x3f3f3f3f的数据
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
        // dp顺序: 按照distanceToLastNode(i) 从小到大进行dp !!!
        List<int[]> list = new ArrayList<>();
        for(int i = 0;i < n;i++){
            list.add(new int[]{i, dist[i]});
        }
        Collections.sort(list, (a, b) -> a[1] - b[1]);
        int[] dp = new int[n];
        dp[n - 1] = 1;
        for(int i = 1;i < n;i++){   // 跳过n - 1这个节点, dist[n - 1] = 0
            for(int[] nxt : g[list.get(i)[0]]){
                if(dist[nxt[0]] < dist[list.get(i)[0]]) dp[list.get(i)[0]] = (dp[list.get(i)[0]] + dp[nxt[0]]) % MOD;
            }
        }
        return dp[0];
    }
}
