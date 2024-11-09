package problemList.graph.solution;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
首先需要读懂题目, 需要注意, 题目中说的是 "moveTime[i][j] 表示在这个时刻 以后 你才可以 开始 往这个房间 移动"
因此我们需要在dijkstra的板子的基础上, 加一些改动
具体来说, 在更新dist[i][j]的时候, 普通dijkstra的更新方式是 dist[i][j] = dist[x][y] + 1      (当前位置是(x, y), (x, y)的邻接点是(i, j))
但是这里我们要求只有在 moveTime[i][j] 之后才可以往 (i, j) 这个格子移动, 因此这里上面的更新方式需要加以修改, dist[i][j] = Math.max(dist[x][y], moveTime[i][j]) + 1;
我们可以这样理解这里的 Math.max(dist[x][y], moveTime[i][j]): 如果想要走到(i, j)这个点, 但是此时花费的时间还不到moveTime[i][j], 那么我们就可以在(x, y)这个点原地等待, 
直到达到了moveTime[i][j]之后, 再向(i, j)这个点移动

需要注意的是, 这里由于是在网格图上跑dijkstra, 因此原来的一维dist需要拓展到二维, 即原来的dist[i]变成dist[i][j]
*/
public class LC3341 {
    private int[] dx = new int[]{0, 1, 0, -1}, dy = new int[]{1, 0, -1, 0};
    public int minTimeToReach(int[][] moveTime) {
        // m * n
        int m = moveTime.length, n = moveTime[0].length, INF = 0x3f3f3f3f;
        int[][] dist = new int[m][n];
        for(int[] row : dist) Arrays.fill(row, INF);
        dist[0][0] = 0;
        // [disti, i, j]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        minHeap.offer(new int[]{0, 0, 0});
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            int curDist = poll[0], x = poll[1], y = poll[2];    // 当前位置 (x, y)
            for(int i = 0;i < 4;i++){
                int nx = x + dx[i], ny = y + dy[i];
                if(nx >= 0 && nx < m && ny >= 0 && ny < n){
                    if(curDist >= dist[nx][ny]) continue;
                    int nxtDist = Math.max(curDist, moveTime[nx][ny]) + 1;
                    if(nxtDist < dist[nx][ny]){
                        dist[nx][ny] = nxtDist;
                        minHeap.offer(new int[]{nxtDist, nx, ny});
                    }
                }
            }
        }
        return dist[m - 1][n - 1];
    }
}
