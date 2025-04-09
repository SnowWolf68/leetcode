package problemList.BFS.solution;

import java.util.LinkedList;
import java.util.Queue;

/*
 * 思路参考三叶姐的题解: https://leetcode.cn/problems/shortest-path-to-get-all-keys/solutions/1960544/by-ac_oier-5gxc/
 * 
 * 暴力bfs
 * 
 * hint: 你真的会用bfs吗?
 * hint: 分层图bfs
 * 
 */
public class LC864 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length, n = grid[0].length();
        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] vis = new boolean[m][n][1 << 6];
        int cnt = 0;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(grid[i].charAt(j) >= 'a') cnt++;
                if(grid[i].charAt(j) == '@') queue.offer(new int[]{i, j, 0, 0});
            }
        }
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            if(vis[poll[0]][poll[1]][poll[2]]) continue;
            vis[poll[0]][poll[1]][poll[2]] = true;
            if(poll[2] == (1 << cnt) - 1) return poll[3];
            for(int k = 0;k < 4;k++){
                int nx = poll[0] + dx[k], ny = poll[1] + dy[k];
                if(nx >= 0 && ny >= 0 && nx < m && ny < n){
                    if(grid[nx].charAt(ny) == '#') continue;
                    else if(grid[nx].charAt(ny) == '.') queue.offer(new int[]{nx, ny, poll[2], poll[3] + 1});
                    else if(grid[nx].charAt(ny) >= 'a' && grid[nx].charAt(ny) <= 'z'){
                        // a
                        queue.offer(new int[]{nx, ny, poll[2] | (1 << (grid[nx].charAt(ny) - 'a')), poll[3] + 1});
                    }else if(grid[nx].charAt(ny) >= 'A' && grid[nx].charAt(ny) <= 'Z'){
                        // A
                        if(((poll[2] >> (grid[nx].charAt(ny) - 'A')) & 1) != 0){
                            queue.offer(new int[]{nx, ny, poll[2], poll[3] + 1});
                        }
                    }else{
                        // @
                        queue.offer(new int[]{nx, ny, poll[2], poll[3] + 1});
                    }
                }
            }
        }
        return -1;
    }
}
