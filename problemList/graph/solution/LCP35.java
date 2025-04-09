package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 分层图最短路
 * 
 * dist[i][j]: 在i节点, 电量为j, 此时的最短时间
 * 
 */
public class LCP35 {
    public int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
        int n = charge.length, INF = 0x3f3f3f3f;
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : paths){
            g[e[0]].add(new int[]{e[1], e[2]});
            g[e[1]].add(new int[]{e[0], e[2]});
        }
        int[][] dist = new int[n][cnt + 1];
        for(int i = 0;i < n;i++) Arrays.fill(dist[i], INF);
        dist[start][0] = 0;
        boolean[][] vis = new boolean[n][cnt + 1];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        minHeap.offer(new int[]{start, 0, 0});  // [node, battery, time]
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(vis[poll[0]][poll[1]]) continue;
            vis[poll[0]][poll[1]] = true;

            // 这个剪枝挺有意思, 不加这个剪枝 998ms, 加上149ms, 一开始我没想到这个剪枝
            if(poll[0] == end) return poll[2];

            // charge
            for(int i = poll[1] + 1;i <= cnt;i++){
                if(dist[poll[0]][poll[1]] + charge[poll[0]] * (i - poll[1]) < dist[poll[0]][i]){
                    dist[poll[0]][i] = dist[poll[0]][poll[1]] + charge[poll[0]] * (i - poll[1]);
                    minHeap.offer(new int[]{poll[0], i, dist[poll[0]][i]});
                }
            }
            // move
            for(int[] nxt : g[poll[0]]){
                if(poll[1] >= nxt[1] && dist[poll[0]][poll[1]] + nxt[1] < dist[nxt[0]][poll[1] - nxt[1]]){
                    dist[nxt[0]][poll[1] - nxt[1]] = dist[poll[0]][poll[1]] + nxt[1];
                    minHeap.offer(new int[]{nxt[0], poll[1] - nxt[1], dist[nxt[0]][poll[1] - nxt[1]]});
                }
            }
        }
        // int ret = INF;
        // for(int i = 0;i <= cnt;i++) ret = Math.min(ret, dist[end][i]);
        // return ret;

        // 有了上面那个剪枝, 这里最后的循环也不需要了, 如果上面没有return的话, 说明走不到end
        return -1;
    }
}
