package revise_problemList;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LC1631 {
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};

    public int minimumEffortPath1(int[][] heights) {
        int m = heights.length, n = heights[0].length, INF = 0x3f3f3f3f;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        minHeap.offer(new int[]{0, 0, 0});
        int[][] dist = new int[m][n];
        for(int i = 0;i < m;i++) Arrays.fill(dist[i], INF);
        dist[0][0] = 0;
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            for(int k = 0;k < 4;k++){
                int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
                if(nx >= 0 && ny >= 0 && nx < m && ny < n && Math.max(dist[poll[0]][poll[1]], Math.abs(heights[poll[0]][poll[1]] - heights[nx][ny])) < dist[nx][ny]){
                    dist[nx][ny] = Math.max(dist[poll[0]][poll[1]], Math.abs(heights[poll[0]][poll[1]] - heights[nx][ny]));
                    minHeap.offer(new int[]{nx, ny, dist[nx][ny]});
                }
            }
        }
        return dist[m - 1][n - 1];
    }

    /**
        优化不明显
     */
    public int minimumEffortPath2(int[][] heights) {
        int m = heights.length, n = heights[0].length, INF = 0x3f3f3f3f;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        minHeap.offer(new int[]{0, 0, 0});
        int[][] dist = new int[m][n];
        for(int i = 0;i < m;i++) Arrays.fill(dist[i], INF);
        dist[0][0] = 0;
        boolean[][] done = new boolean[m][n];
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            if(done[poll[0]][poll[1]]) continue;
            done[poll[0]][poll[1]] = true;
            for(int k = 0;k < 4;k++){
                int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
                if(nx >= 0 && ny >= 0 && nx < m && ny < n && !done[nx][ny] && Math.max(dist[poll[0]][poll[1]], Math.abs(heights[poll[0]][poll[1]] - heights[nx][ny])) < dist[nx][ny]){
                    dist[nx][ny] = Math.max(dist[poll[0]][poll[1]], Math.abs(heights[poll[0]][poll[1]] - heights[nx][ny]));
                    minHeap.offer(new int[]{nx, ny, dist[nx][ny]});
                }
            }
        }
        return dist[m - 1][n - 1];
    }
}
