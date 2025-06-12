package problemList.BFS.solution;

import java.util.LinkedList;
import java.util.Queue;

/**
注意: 在BFS中, 当走到石堆时, 如果当前没有带石头, 那么显然选择带上石头一定是更优的

但是这样的状态压缩 + BFS会TLE

时间复杂度: O(n * m * 2 ^ cnt)
 */
public class LCP13_TLE {
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};
    public int minimalSteps(String[] maze) {
        int n = maze.length, m = maze[0].length(), cnt = 0;
        boolean[][][][] vis = new boolean[n][m][1 << 16][2];    // x, y, state, hasStone, step
        Queue<int[]> queue = new LinkedList<>();
        char[][] grid = new char[n][m];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                grid[i][j] = maze[i].charAt(j);
                if(maze[i].charAt(j) == 'S'){
                    vis[i][j][0][0] = true;
                    queue.offer(new int[]{i, j, 0, 0, 0});
                }else if(maze[i].charAt(j) == 'M'){
                    grid[i][j] = (char)(cnt + '0');
                    cnt++;
                }
            }
        }
        int mask = 1 << cnt;
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int x = poll[0], y = poll[1], state = poll[2], hasStone = poll[3], step = poll[4];
            for(int k = 0;k < 4;k++){
                int nx = x + dx[k], ny = y + dy[k];
                if(nx >= 0 && ny >= 0 && nx < n && ny < m){
                    char ch = grid[nx][ny];
                    int nState = state, nHasStone = hasStone;
                    if(ch == 'T' && state == mask - 1){
                        return step + 1;
                    }else if(ch == 'O' && hasStone == 0){
                        nHasStone = 1;
                    }else if(ch == '#'){
                        continue;
                    }else if(ch >= '0' && ch <= (char)(cnt + '0')){
                        int idx = ch - '0';
                        if(((state >> idx) & 1) == 0){
                            if(hasStone == 1){
                                nHasStone = 0;
                                nState |= (1 << idx);
                            };
                        }
                    }
                    if(!vis[nx][ny][nState][nHasStone]){
                        vis[nx][ny][nState][nHasStone] = true;
                        queue.offer(new int[]{nx, ny, nState, nHasStone, step + 1});
                    }
                }
            }
        }
        return -1;
    }
}
