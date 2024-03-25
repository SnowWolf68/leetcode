package problemList.dp.solution;

import java.util.*;

public class LC2328 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int countPaths(int[][] grid) {
        int m = grid.length, n = grid[0].length, MOD = (int)1e9 + 7;
        // <val, [i, j]> 这里由于同一个val可能对应多个[i, j], 因此第二个维度使用的是List
        Map<Integer, List<int[]>> map = new TreeMap<>((o1, o2) -> o2 - o1);
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                List<int[]> list = map.getOrDefault(grid[i][j], new ArrayList<>());
                list.add(new int[]{i, j});
                map.put(grid[i][j], list);
            }
        }
        int[][] dp = new int[m][n];
        for(int key : map.keySet()){
            List<int[]> list = map.get(key);
            for(int[] idx : list){
                int i = idx[0], j = idx[1];
                dp[i][j] = 1;
                for(int k = 0;k < 4;k++){
                    int ni = i + dx[k], nj = j + dy[k];
                    if(ni >= 0 && ni < m && nj >= 0 && nj < n && grid[i][j] < grid[ni][nj]){
                        dp[i][j] = (dp[i][j] + dp[ni][nj]) % MOD;
                    }
                }
            }
        }
        int ret = 0;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                ret = (ret + dp[i][j]) % MOD;
            }
        }
        return ret;
    }
}
