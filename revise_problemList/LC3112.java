package revise_problemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
点的数量级 5 * 10^4 , 边的数量级 10^5 , 算是稀疏图, 用邻接矩阵会爆内存, 得换邻接表
 */
public class LC3112 {
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            // 后面的计算过程保证, 如果有多条边, 最终的结果一定是最短的那一条更新出来的结果
            g[e[0]].add(new int[]{e[1], e[2]});
            g[e[1]].add(new int[]{e[0], e[2]});
        }
        int INF = 0x3f3f3f3f;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        boolean[] done = new boolean[n];
        done[0] = true;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for(int[] nxt : g[0]){
            if(nxt[1] < disappear[nxt[0]]){
                dist[nxt[0]] = Math.min(dist[nxt[0]], nxt[1]);
                minHeap.offer(new int[]{nxt[0], dist[nxt[0]]});
            }
        }
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            if(done[poll[0]]) continue;
            done[poll[0]] = true;
            for(int[] nxt : g[poll[0]]){
                if(dist[poll[0]] + nxt[1] < disappear[nxt[0]] && dist[poll[0]] + nxt[1] < dist[nxt[0]]){
                    dist[nxt[0]] = dist[poll[0]] + nxt[1];
                    minHeap.offer(new int[]{nxt[0], dist[nxt[0]]});
                }
            }
        }
        int[] answer = new int[n];
        for(int i = 0;i < n;i++){
            answer[i] = dist[i] == INF ? -1 : dist[i];
        }
        return answer;
    }
}
