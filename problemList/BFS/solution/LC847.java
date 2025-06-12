package solution;

import java.util.LinkedList;
import java.util.Queue;

/**
状态压缩 + bfs
之前超时的原因: 标记vis的时机不对, 之前是出队的时候才标记vis, 但是这样就会导致一些重复状态也会被遍历到
因此正确的做法是在入队时就进行标记
 */
public class LC847 {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length, mask = 1 << n;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] vis = new boolean[n][mask];
        for(int i = 0;i < n;i++){
            queue.offer(new int[]{i, 1 << i, 0});    // i, state, step
            vis[i][1 << i] = true;
        }
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            if(poll[1] == mask - 1){
                return poll[2];
            }
            // vis[poll[0]][poll[1]] = true;
            for(int nxt : graph[poll[0]]){
                if(((poll[1] >> nxt) & 1) == 1 && !vis[nxt][poll[1]]){
                    queue.offer(new int[]{nxt, poll[1], poll[2] + 1});
                    vis[nxt][poll[1]] = true;
                }else{
                    if(!vis[nxt][poll[1] | (1 << nxt)]){
                        queue.offer(new int[]{nxt, poll[1] | (1 << nxt), poll[2] + 1});
                        vis[nxt][poll[1] | (1 << nxt)] = true;
                    }
                }
            }
        }
        return -1;
    }
}
