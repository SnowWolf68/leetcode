package problemList.graph.solution;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
首先有一个细节需要注意, 本题中的moveTime[i][j] (或者说grid[i][j]) 表示的是 访问 (i, j) 这个节点的时间至少是 moveTime[i][j] (或者说grid[i][j])
而LC3341中的moveTime[i][j], 指的是在 moveTime[i][j] 之后才能向 (i, j) 这个格子移动
这两种叙述是有区别的, 一定一定一定要注意!!!

这题的约束和LC3341很像, 不同点在于, 这题不能在某一个格子上停留
换句话说, 如果某一个方向的格子现在暂时不能走, 那么此时不能在当前格子上停留直到要走的格子可以走, 而是必须选择一个能走的格子

这里有一个很巧妙的转化:
    1. 如果moveTime[0][1] > 1 && moveTime[1][0] > 1, 那么显然一开始一步都走不了, 那么此时显然走不到 m - 1, n - 1 这个格子
    2. 如果moveTime[0][1] 或 moveTime[1][0] 有一个 <= 1, 那么此时可以推导得到: 所有的节点都是可以走到的
        为什么? 这里有一个很有意思的技巧: 
        假设此时在后续的路径中, 某一个状态位于 (x, y) 这个点, 想要往 (nx, ny) 这个点走, 但是此时 moveTime[nx][ny] > curDist + 1
        也就是此时不能立即往 (nx, ny) 这个状态走, 但是此时根据题目要求, 又不能在 (x, y) 这个节点停留, 那么此时应该怎么办?
        有一种很巧妙的思路, 我们可以在起始节点 (0, 0) 和 起始节点的相邻节点 (0, 1) 或 (1, 0) (这两个点选一个即可, 取决于哪一个能走) 中选一个
        在这两个节点之间反复横跳, 直到将时间拖延到 moveTime[nx][ny] 所规定的时间即可

        那么此时我们可以直接用 nxtDist = Math.max(curDist + 1, moveTime[nx][ny]); 来更新nxtDist吗?
        显然是不行的, 这里有一个细节需要注意, 假设当我们进入 (nx, ny) 这个点的时间是 4, 而 moveTime[nx][ny] = 5
        意思是我们必须再等待1时间时候, 才能进入 (nx, ny) 这个格子, 但是由于我们不能原地等待, 只能通过反复横跳来等待, 但是由于横跳的规则规定了
        通过横跳来获取的等待时间, 只能是2的倍数, 换句话说, 在当前这种情况下, 虽然只需要等到1个时间就可以进入 (nx, ny) 但是由于我们需要反复横跳, 因此只能等2个时间之后才能到 (nx, ny)
        类似的, 如果我们需要等待的时间是 3, 5, 7, ... 那么我们都需要等待 4, 6, 8, ... 之后才能到 (nx, ny)
        因此最终的计算方式是: nxtDist = Math.max(curDist + 1, moveTime[nx][ny]) + (diff > 0 ? (diff % 2 == 0 ? 0 : 1) : 0);
 */
public class LC2577 {
    private int[] dx = new int[]{0, 1, 0, -1}, dy = new int[]{1, 0, -1, 0};
    public int minimumTime(int[][] moveTime) {
        if(!(moveTime[0][1] <= 1 || moveTime[1][0] <= 1)) return -1;
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
                    int diff = moveTime[nx][ny] - curDist - 1;
                    int nxtDist = Math.max(curDist + 1, moveTime[nx][ny]) + (diff > 0 ? (diff % 2 == 0 ? 0 : 1) : 0);
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
