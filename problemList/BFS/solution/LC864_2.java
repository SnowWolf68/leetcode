package problemList.BFS.solution;

import java.util.LinkedList;
import java.util.Queue;

/**
状态压缩 + BFS
二刷的写法
这里的写法感觉比第一种写法要好
 */
public class LC864_2 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int shortestPathAllKeys(String[] grid) {
        int n = grid.length, m = grid[0].length(), k = 0;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] vis = new boolean[n][m][1 << 6];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                if(grid[i].charAt(j) == '@'){
                    vis[i][j][0] = true;
                    queue.offer(new int[]{i, j, 0, 0});
                }else if(grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'z') k++;
            }
        }
        int mask = 1 << k;
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            if(poll[2] == mask - 1){
                return poll[3];
            }
            for(int p = 0;p < 4;p++){
                int nx = poll[0] + dx[p], ny = poll[1] + dy[p];
                if(nx >= 0 && ny >= 0 && nx < n && ny < m){
                    char ch = grid[nx].charAt(ny);
                    int nState = poll[2];
                    if(ch >= 'a' && ch <= 'z'){
                        nState |= 1 << (ch - 'a');
                    }else if(ch >= 'A' && ch <= 'Z'){
                        if(((poll[2] >> (ch - 'A')) & 1) == 0){
                            continue;
                        }
                    }else if(ch == '#'){
                        continue;
                    }
                    if(!vis[nx][ny][nState]){
                        vis[nx][ny][nState] = true;
                        queue.offer(new int[]{nx, ny, nState, poll[3] + 1});
                    }
                }
            }
        }
        return -1;
    }
}
