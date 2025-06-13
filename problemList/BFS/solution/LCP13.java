package problemList.BFS.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
还是没想出来, 看答案吧
 */
public class LCP13 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int minimalSteps(String[] maze) {
        int n = maze.length, m = maze[0].length();
        List<int[]> oList = new ArrayList<>(), mList = new ArrayList<>();
        int[] start = new int[2], target = new int[2];
        int oCnt = 0, mCnt = 0;
        int[][] oGrid = new int[n][m];
        int[][] mGrid = new int[n][m];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                if(maze[i].charAt(j) == 'S'){
                    start[0] = i;
                    start[1] = j;
                }else if(maze[i].charAt(j) == 'T'){
                    target[0] = i;
                    target[1] = j;
                }else if(maze[i].charAt(j) == 'O'){
                    oList.add(new int[]{i, j});
                    oGrid[i][j] = oCnt++;
                }else if(maze[i].charAt(j) == 'M'){
                    mList.add(new int[]{i, j});
                    mGrid[i][j] = mCnt++;
                }
            }
        }

        // 单独处理没有机关的情况
        if(mCnt == 0){
            Queue<int[]> queue = new LinkedList<>();
            boolean[][] vis = new boolean[n][m];
            queue.offer(new int[]{start[0], start[1], 0});
            vis[start[0]][start[1]] = true;
            while (!queue.isEmpty()) {
                int[] poll = queue.poll();
                int x = poll[0], y = poll[1], step = poll[2];
                if(maze[x].charAt(y) == 'T') return step;
                for(int k = 0;k < 4;k++){
                    int nx = x + dx[k], ny = y + dy[k];
                    if(nx >= 0 && ny >= 0 && nx < n && ny < m){
                        if(maze[nx].charAt(ny) == '#') continue;
                        if(!vis[nx][ny]){
                            vis[nx][ny] = true;
                            queue.offer(new int[]{nx, ny, step + 1});
                        }
                    }
                }
            }
            return -1;
        }

        int[] s2o = new int[oCnt];
        int INF = 0x3f3f3f3f;
        Arrays.fill(s2o, INF);;
        int[][] o2m = new int[oCnt][mCnt];
        for(int i = 0;i < oCnt;i++) Arrays.fill(o2m[i], INF);
        int[][] m2o = new int[mCnt][oCnt];
        for(int i = 0 ;i < mCnt;i++) Arrays.fill(m2o[i], INF);
        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0;i < oList.size();i++){
            boolean[][] vis = new boolean[n][m];
            vis[oList.get(i)[0]][oList.get(i)[1]] = true;
            queue.offer(new int[]{oList.get(i)[0], oList.get(i)[1], 0});
            while (!queue.isEmpty()) {
                int[] poll = queue.poll();
                int x = poll[0], y = poll[1], step = poll[2];
                if(maze[x].charAt(y) == 'S'){
                    s2o[i] = step;
                }else if(maze[x].charAt(y) == 'M'){
                    o2m[i][mGrid[x][y]] = step;
                    m2o[mGrid[x][y]][i] = step;
                }
                for(int k = 0;k < 4;k++){
                    int nx = x + dx[k], ny = y + dy[k];
                    if(nx >= 0 && ny >= 0 && nx < n && ny < m){
                        if(maze[nx].charAt(ny) == '#') continue;
                        if(!vis[nx][ny]){
                            vis[nx][ny] = true;
                            queue.offer(new int[]{nx, ny, step + 1});
                        }
                    }
                }
            }
        }
        int[] m2t = new int[mCnt];
        Arrays.fill(m2t, INF);
        for(int i = 0;i < mList.size();i++){
            boolean[][] vis = new boolean[n][m];
            queue.clear();
            vis[mList.get(i)[0]][mList.get(i)[1]] = true;
            queue.offer(new int[]{mList.get(i)[0], mList.get(i)[1], 0});
            while (!queue.isEmpty()) {
                int[] poll = queue.poll();
                int x = poll[0], y = poll[1], step = poll[2];
                if(maze[x].charAt(y) == 'T'){
                    m2t[i] = step;
                }
                for(int k = 0;k < 4;k++){
                    int nx = x + dx[k], ny = y + dy[k];
                    if(nx >= 0 && ny >= 0 && nx < n && ny < m){
                        if(maze[nx].charAt(ny) == '#') continue;
                        if(!vis[nx][ny]){
                            vis[nx][ny] = true;
                            queue.offer(new int[]{nx, ny, step + 1});
                        }
                    }
                }
            }
        }
        // 状压dp
        int mask = 1 << mCnt;
        int[][] dp = new int[mask][mCnt];
        for(int i = 0;i < mask;i++) Arrays.fill(dp[i], INF);
        for(int i = 0;i < mCnt;i++){
            for(int j = 0;j < oCnt;j++){
                dp[1 << i][i] = Math.min(dp[1 << i][i], s2o[j] + o2m[j][i]);
            }
        }
        for(int state = 0;state < mask;state++){
            for(int i = 0;i < mCnt;i++){
                if(((state >> i) & 1) == 0) continue;
                if(Integer.bitCount(state) <= 1) continue;
                for(int pre = 0;pre < mCnt;pre++){
                    if(((state >> pre) & 1) == 0 || pre == i) continue;
                    for(int j = 0;j < oCnt;j++){
                        if(dp[state ^ (1 << i)][pre] != INF && m2o[pre][j] != INF && o2m[j][i] != INF){
                            dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre] + m2o[pre][j] + o2m[j][i]);
                        }
                    }
                }
            }
        }
        int ret = INF;
        for(int i = 0;i < mCnt;i++){
            if(dp[mask - 1][i] != INF && m2t[i] != INF) ret = Math.min(ret, dp[mask - 1][i] + m2t[i]);
        }
        return ret == INF ? -1 : ret;
    }

    public static void main(String[] args) {
        // String[] maze = new String[]{"S#O", "M..", "M.T"};      // 16
        // String[] maze = new String[]{"S#O", "M.#", "M.T"};      // -1
        // String[] maze = new String[]{"S#O", "M.T", "M.."};   // 17
        String[] maze = new String[]{"##TOO#O#", "OO##O.S#", "###.O###", "#..O#O##"};    // 5
        System.out.println("ret = " + new LCP13().minimalSteps(maze));
    }
}
