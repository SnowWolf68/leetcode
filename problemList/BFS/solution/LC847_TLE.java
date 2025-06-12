package solution;

import java.util.LinkedList;
import java.util.Queue;

/**
状态压缩 + bfs
 */
public class LC847_TLE {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length, mask = 1 << n;
        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0;i < n;i++){
            queue.offer(new int[]{i, 1 << i, 0});    // i, state, step
        }
        boolean[][] vis = new boolean[n][mask];
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            if(poll[1] == mask - 1){
                return poll[2];
            }
            vis[poll[0]][poll[1]] = true;
            for(int nxt : graph[poll[0]]){
                if(((poll[1] >> nxt) & 1) == 1 && !vis[nxt][poll[1]]){
                    queue.offer(new int[]{nxt, poll[1], poll[2] + 1});
                }else{
                    if(!vis[nxt][poll[1] | (1 << nxt)]){
                        queue.offer(new int[]{nxt, poll[1] | (1 << nxt), poll[2] + 1});
                    }
                }
            }
        }
        return -1;
    }
}
