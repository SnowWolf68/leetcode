package problemList.graph.solution;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
这题最关键的是如何处理: 第一次花费1秒, 第二次花费2秒 这个条件
这里灵神使用了一个很巧妙的方法: 联系国际棋盘, 发现其实花费的时间和所处的位置的行列和的奇偶性有关
具体可以看: https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/solutions/2134200/er-fen-da-an-bfspythonjavacgo-by-endless-j10w/
假设我们用i, j表示网格中的某一个位置, 那么使用上面的方法, 不难得出这样的规律: 从(i, j)这个格子出发, 所需要花费的时间time = ((i + j) % 2 == 0 ? 1 : 2)
剩下的问题就和LC3341完全一样了
 */
public class LC3342 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int minTimeToReach(int[][] moveTime) {
        int m = moveTime.length, n = moveTime[0].length, INF = 0x3f3f3f3f;
        int[][] dist = new int[m][n];
        for(int[] row : dist) Arrays.fill(row, INF);
        dist[0][0] = 0;
        // [curDist, x, y]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        minHeap.offer(new int[]{0, 0, 0});
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            int curDist = poll[0], x = poll[1], y = poll[2];
            if(curDist > dist[x][y]) continue;
            int time = (x + y) % 2 == 0 ? 1 : 2;
            for(int i = 0;i < 4;i++){
                int nx = x + dx[i], ny = y + dy[i];
                if(nx >= 0 && nx < m && ny >= 0 && ny < n){
                    int nxtDist = Math.max(moveTime[nx][ny], curDist) + time;
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
