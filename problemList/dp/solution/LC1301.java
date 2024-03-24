package problemList.dp.solution;

import java.util.*;

public class LC1301 {
    public int[] pathsWithMaxScore(List<String> board) {
        int m = board.size(), n = board.get(0).length(), MOD = (int)1e9 + 7;
        char[][] grid = new char[m][n];
        for(int i = 0;i < m;i++){
            char[] row = board.get(i).toCharArray();
            for(int j = 0;j < n;j++){
                grid[i][j] = row[j];
            }
        }
        if(m == 2 && n == 2) return new int[]{Math.max(grid[0][1] == 'X' ? 0 : grid[0][1] - '0', grid[1][0] == 'X' ? 0 : grid[1][0] - '0'), 1};
        long[][] sum = new long[m][n], cnt = new long[m][n];
        cnt[m - 1][n - 1] = 1;
        for(int i = m - 1;i >= 0;i--){
            long max = 0;
            for(int j = n - 1;j >= 0;j--){
                if(grid[i][j] != 'X'){
                    if(i + 1 < m && grid[i + 1][j] != 'X') {
                        if(grid[i + 1][j] == 'S') sum[i][j] = Math.max(sum[i][j], grid[i][j] - '0');
                        else sum[i][j] = Math.max(sum[i][j], sum[i + 1][j] + (grid[i][j] - '0'));
                    }
                    if(i + 1 < m && j + 1 < n && grid[i + 1][j + 1] != 'X') {
                        if(grid[i + 1][j + 1] == 'S') sum[i][j] = Math.max(sum[i][j], grid[i][j] - '0');
                        else sum[i][j] = Math.max(sum[i][j], sum[i + 1][j + 1] + (grid[i][j] - '0'));
                    }
                    if(j + 1 < n && grid[i][j + 1] != 'X') {
                        if(grid[i][j + 1] == 'S') sum[i][j] = Math.max(sum[i][j], grid[i][j] - '0');
                        else sum[i][j] = Math.max(sum[i][j], sum[i][j + 1] + (grid[i][j] - '0'));
                    }
                    if(i + 1 < m && grid[i + 1][j] != 'X' && (sum[i][j] == sum[i + 1][j] + grid[i][j] - '0' || (grid[i + 1][j] == 'S' && sum[i][j] == grid[i][j]))) cnt[i][j] = (cnt[i][j] + cnt[i + 1][j]) % MOD;
                    if(i + 1 < m && j + 1 < n && grid[i + 1][j + 1] != 'X' && (sum[i][j] == sum[i + 1][j + 1] + grid[i][j] - '0' || (grid[i + 1][j + 1] == 'S' && sum[i][j] == grid[i][j]))) cnt[i][j] = (cnt[i][j] + cnt[i + 1][j + 1]) % MOD;
                    if(j + 1 < n && grid[i][j + 1] != 'X' && (sum[i][j] == sum[i][j + 1] + grid[i][j] - '0' || (grid[i][j + 1] == 'S' && sum[i][j] == grid[i][j]))) cnt[i][j] = (cnt[i][j] + cnt[i][j + 1]) % MOD;
                }
                max = Math.max(max, cnt[i][j]);
            }
            if(max == 0) return new int[]{0, 0};
        }
        return new int[]{(int)((sum[0][0] - ('E' - '0')) % MOD), (int)(cnt[0][0] % MOD)};
    }
}
