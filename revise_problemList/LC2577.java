package revise_problemList;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LC2577 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int minimumTime(int[][] grid) {
        int m = grid.length, n = grid[0].length, INF = 0x3f3f3f3f;
        int[][] dist = new int[m][n];
        for(int i = 0;i < m;i++) Arrays.fill(dist[i], INF);
        if(grid[0][1] > 1 && grid[1][0] > 1) return -1;
        dist[0][0] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        minHeap.offer(new int[]{0, 0, 0});
        boolean[][] vis = new boolean[m][n];
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(vis[poll[0]][poll[1]]) continue;
            vis[poll[0]][poll[1]] = true;
            for(int k = 0;k < 4;k++){
                int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
                if(nx >= 0 && ny >= 0 && nx < m && ny < n){
                    int diff = grid[nx][ny] - (dist[poll[0]][poll[1]] + 1);
                    if(diff <= 0) {
                        if(dist[poll[0]][poll[1]] + 1 < dist[nx][ny]) {
                            dist[nx][ny] = dist[poll[0]][poll[1]] + 1;
                            minHeap.offer(new int[]{nx, ny, dist[nx][ny]});
                        }
                    }else{
                        diff = (diff % 2 == 0) ? diff : diff + 1;
                        if(dist[poll[0]][poll[1]] + 1 + diff < dist[nx][ny]) {
                            dist[nx][ny] = dist[poll[0]][poll[1]] + 1 + diff;
                            minHeap.offer(new int[]{nx, ny, dist[nx][ny]});
                        }
                    }
                }
            }
        }
        return dist[m - 1][n - 1];
    }
}
