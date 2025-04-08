一次偶然的Hack

题目链接: LC2577 https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/

代码如下: 

```java
private int[] dx = new int[]{1, 0, -1, 0};
private int[] dy = new int[]{0, 1, 0, -1};
public int minimumTime(int[][] grid) {
    // 因为最后一次check得到的startTime可能是-1, 因此需要一个lastStartTime记录最后一次成功的起始时间
    int m = grid.length, n = grid[0].length, high = (int)1e5 + m + n, low = m + n - 2, startTime = -1, lastStartTime = -1;
    while(low < high){
        int mid = (low + high) >> 1;
        startTime = check(mid, grid);
        if(startTime >= 0) {
            lastStartTime = startTime;
            high = mid;
        }
        else low = mid + 1;
    }
    return lastStartTime == 0 ? low : ((grid[0][1] > 1 && grid[1][0] > 1) ? -1 : (lastStartTime % 2 == 0 ? low : low + 1));     // 正确的
    // return lastStartTime % 2 == 0 ? low : ((grid[0][1] > 1 && grid[1][0] > 1) ? -1 : low + 1);   // 错误的, 但是能AC, 说明缺少测试用例
    /*
     * 补充测试用例: grid = [[0,2,4],[3,2,1],[1,0,6]]   预期结果: -1, 上面错误的返回语句结果: 6
     */
}

/*
 * 因为不知道反复横跳的时间, 因此需要从终点往起点bfs
 * 由于需要根据起始时间判断横跳的次数, 因此check的返回值应该是 起始时间
 */
private int check(int time, int[][] grid){
    int m = grid.length, n = grid[0].length;
    if(grid[m - 1][n - 1] > time) return -1;
    Queue<int[]> queue = new LinkedList<>();    // x, y, curTime
    boolean[][] vis = new boolean[m][n];
    queue.offer(new int[]{m - 1, n - 1, time});
    while (!queue.isEmpty()) {
        int[] poll = queue.poll();
        if(vis[poll[0]][poll[1]]) continue;
        vis[poll[0]][poll[1]] = true;
        if(poll[0] == 0 && poll[1] == 0) {
            return poll[2];
        }
        for(int k = 0;k < 4;k++){
            int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
            if(nx >= 0 && ny >= 0 && nx < m && ny < n){
                if(poll[2] - 1 >= grid[nx][ny]){
                    queue.offer(new int[]{nx, ny, poll[2] - 1});
                }
            }
        }
    }
    return -1;
}
```

解释: 一开始我写的时候, 使用了错误的return语句: `return lastStartTime % 2 == 0 ? low : ((grid[0][1] > 1 && grid[1][0] > 1) ? -1 : low + 1);` 在这种return语句下, 当`lastStartTime == 0`, 并且 `(grid[0][1] > 1 && grid[1][0] > 1)`时, 会返回low而不是-1, 但是这样的代码也能够AC

因此想到构造测试用例, 由题目给的示例2不难构造出 `grid = [[0,2,4],[3,2,1],[1,0,6]]`   预期结果: -1, 上面错误的返回语句结果: 6

因此需要添加如上的测试用例, 以卡掉错误程序