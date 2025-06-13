package problemList.BFS.solution;

import java.util.LinkedList;
import java.util.Queue;

/**
状态压缩 + BFS, 但是超时了
时间复杂度: O(n * m * 2 ^ cnt * energy)

优化: (参考灵神题解)
    为了避免重复拓展一些无用的位置 (进行剪枝), 我们可以将vis数组优化为三维(n, m, mask), 并且vis[i][j][state]记录走到(i, j), 并且清理垃圾状态为state时, 
    此时的最大能量值
    那么在bfs过程中, 如果(nx, ny, nState)状态的能量值小于vis[nx][ny][nState], 说明此时这个状态没有必要继续拓展下去了, 可以剪枝

这样剪枝之后就跑的很快
灵神的这种剪枝方法还是很巧妙的, 一定要学会
 */
public class LC3568 {
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};
    public int minMoves(String[] classroom, int energy) {
        int n = classroom.length, m = classroom[0].length(), cnt = 0;
        // boolean[][][][] vis = new boolean[n][m][1 << 10][51];   // x, y, state, energy
        int[][][] vis = new int[n][m][1 << 10];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                for(int state = 0;state < 1 << 10;state++){
                    vis[i][j][state] = -1;
                }
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        char[][] grid = new char[n][m];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                if(classroom[i].charAt(j) == 'S'){
                    // vis[i][j][0][energy] = true;
                    vis[i][j][0] = energy;
                    queue.offer(new int[]{i, j, 0, energy, 0});  // x, y, state, energy, step
                    grid[i][j] = classroom[i].charAt(j);
                }else if(classroom[i].charAt(j) == 'L'){
                    grid[i][j] = (char)(cnt + 'a');
                    cnt++;
                }else grid[i][j] = classroom[i].charAt(j);
            }
        }
        int mask = 1 << cnt;
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int x = poll[0], y = poll[1], state = poll[2], curEnergy = poll[3], step = poll[4];
            if(state == mask - 1) return step;
            if(curEnergy == 0) continue;
            for(int k = 0;k < 4;k++){
                int nx = x + dx[k], ny = y + dy[k];
                if(nx >= 0 && ny >= 0 && nx < n && ny < m){
                    char ch = grid[nx][ny];
                    int nState = state, nEnergy = curEnergy;
                    if(ch >= 'a' && ch <= 'z'){
                        int idx = ch - 'a';
                        if(((state >> idx) & 1) == 0){
                            nState |= 1 << idx;
                        }
                        nEnergy--;
                    }else if(ch == 'R'){
                        nEnergy = energy;
                    }else if(ch == 'S' || ch == '.'){
                        nEnergy--;
                    }else if(ch == 'X'){
                        continue;
                    }
                    // if(nEnergy == -1) continue;
                    // if(!vis[nx][ny][nState][nEnergy]){
                    if(vis[nx][ny][nState] == -1 || nEnergy > vis[nx][ny][nState]){
                        // vis[nx][ny][nState][nEnergy] = true;
                        vis[nx][ny][nState] = nEnergy;
                        queue.offer(new int[]{nx, ny, nState, nEnergy, step + 1});
                    }
                }
            }
        }
        return -1;
    }
}
