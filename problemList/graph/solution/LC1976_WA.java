package problemList.graph.solution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
虽然这种方法WA了, 但是这种错误的dp顺序是很值得思考的, 因此记录一下

为什么 bfs序 + 刷表法 错了?
使用刷表法时需要保证: 当我们用dp[poll]来刷dp[i]时, dp[poll]一定要已经被正确计算完毕
    但是使用bfs序进行dp时, 并不能够保证, 每次从queue中弹出一个poll时, dp[poll]一定被正确计算完毕
注: 即使把bfs中的vis数组去掉, 允许重复入队也不行, 原因还是: 当poll出来时, 不能保证dp[poll]已经被计算完毕
 */
public class LC1976_WA {
    public int countPaths(int n, int[][] roads) {
        int INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] e : roads){
            g[e[0]][e[1]] = e[2];
            g[e[1]][e[0]] = e[2];
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        boolean[] vis = new boolean[n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{0, 0});
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(vis[poll[0]]) continue;
            vis[poll[0]] = true;
            for(int i = 0;i < n;i++){
                if(g[poll[0]][i] != INF && dist[poll[0]] + g[poll[0]][i] < dist[i]){
                    dist[i] = dist[poll[0]] + g[poll[0]][i];
                    minHeap.offer(new int[]{i, dist[i]});
                }
            }
        }
        // bfs序, 刷表, bfs不要忘了加vis, 否则会错
        int[] dp = new int[n];
        dp[n - 1] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            if(vis[poll]) continue;
            vis[poll] = true;
            for(int i = 0;i < n;i++){
                if(g[poll][i] != INF && dist[i] + g[i][poll] == dist[poll]) {
                    dp[i] = (dp[i] + dp[poll]) % MOD;
                    queue.offer(i);
                }
            }
        }
        return dp[0];
    }

    /**
     * 使用long存dist
     */
    public int countPathsLong(int n, int[][] roads) {
        int INF = 0x3f3f3f3f, MOD = (int)1e9 + 7;
        int[][] g = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(g[i], INF);
        for(int[] e : roads){
            g[e[0]][e[1]] = e[2];
            g[e[1]][e[0]] = e[2];
        }
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        boolean[] vis = new boolean[n];
        PriorityQueue<long[]> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        minHeap.offer(new long[]{0, 0});
        while (!minHeap.isEmpty()) {
            long[] poll = minHeap.poll();
            if(vis[(int)poll[0]]) continue;
            vis[(int)poll[0]] = true;
            for(int i = 0;i < n;i++){
                if(g[(int)poll[0]][i] != INF && dist[(int)poll[0]] + g[(int)poll[0]][i] < dist[i]){
                    dist[i] = dist[(int)poll[0]] + g[(int)poll[0]][i];
                    minHeap.offer(new long[]{i, dist[i]});
                }
            }
        }
        // bfs序, 刷表, bfs不要忘了加vis, 否则会错
        int[] dp = new int[n];
        dp[n - 1] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n - 1);
        vis = new boolean[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            if(vis[poll]) continue;
            vis[poll] = true;
            for(int i = 0;i < n;i++){
                if(g[poll][i] != INF && dist[i] + g[i][poll] == dist[poll]) {
                    dp[i] = (dp[i] + dp[poll]) % MOD;
                    queue.offer(i);
                }
            }
        }
        return dp[0];
    }
}
