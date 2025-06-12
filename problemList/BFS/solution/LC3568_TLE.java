package problemList.BFS.solution;

import java.util.LinkedList;
import java.util.Queue;

/**
状态压缩 + BFS, 但是超时了
时间复杂度: O(n * m * 2 ^ cnt * energy)
 */
public class LC3568_TLE {
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};
    public int minMoves(String[] classroom, int energy) {
        int n = classroom.length, m = classroom[0].length(), cnt = 0;
        boolean[][][][] vis = new boolean[n][m][1 << 10][51];   // x, y, state, energy
        Queue<int[]> queue = new LinkedList<>();
        char[][] grid = new char[n][m];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < m;j++){
                if(classroom[i].charAt(j) == 'S'){
                    vis[i][j][0][energy] = true;
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
                    if(!vis[nx][ny][nState][nEnergy]){
                        vis[nx][ny][nState][nEnergy] = true;
                        queue.offer(new int[]{nx, ny, nState, nEnergy, step + 1});
                    }
                }
            }
        }
        return -1;
    }
}
