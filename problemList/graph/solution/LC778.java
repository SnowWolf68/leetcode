package problemList.graph.solution;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LC778 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int swimInWater(int[][] grid) {
        int n = grid.length, INF = 0x3f3f3f3f;
        int[][] dist = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(dist[i], INF);
        dist[0][0] = grid[0][0];    // 初始化需要注意一下
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        minHeap.offer(new int[]{0, 0, 0});
        boolean[][] vis = new boolean[n][n];
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            if(vis[poll[0]][poll[1]]) continue;
            vis[poll[0]][poll[1]] = true;
            for(int k = 0;k < 4;k++){
                int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
                if(nx >= 0 && ny >= 0 && nx < n && ny < n){
                    if(Math.max(dist[poll[0]][poll[1]], grid[nx][ny]) < dist[nx][ny]){
                        dist[nx][ny] = Math.max(dist[poll[0]][poll[1]], grid[nx][ny]);
                        minHeap.offer(new int[]{nx, ny, dist[nx][ny]});
                    }
                }
            }
        }
        return dist[n - 1][n - 1];
    }
}
