package revise_problemList;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
边权: Math.max(0, moveTime[nx][ny] - curTime) + ((i + j) % 2 == 0 ? 1 : 2);
 */
public class LC3342 {
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};

    public int minTimeToReach(int[][] moveTime) {
        int m = moveTime.length, n = moveTime[0].length, INF = 0x3f3f3f3f;
        int[][] dist = new int[m][n];
        for(int i = 0;i < m;i++) Arrays.fill(dist[i], INF);
        dist[0][0] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        minHeap.offer(new int[]{0, 0, 0});
        boolean[][] done = new boolean[m][n];
        while (!minHeap.isEmpty()) {
            int[] poll = minHeap.poll();
            if(done[poll[0]][poll[1]]) continue;
            done[poll[0]][poll[1]] = true;
            for(int k = 0;k < 4;k++){
                int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
                if(nx >= 0 && ny >= 0 && nx < m && ny < n && !done[nx][ny]){
                    int time = dist[poll[0]][poll[1]] + Math.max(0, moveTime[nx][ny] - dist[poll[0]][poll[1]]) + ((poll[0] + poll[1]) % 2 == 0 ? 1 : 2);
                    if(time < dist[nx][ny]){
                        dist[nx][ny] = time;
                        minHeap.offer(new int[]{nx, ny, time});
                    }
                }
            }
        }
        return dist[m - 1][n - 1];
    }
}
