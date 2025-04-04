package zuoshen.class068;

import java.util.Arrays;

/**
题目4
有效涂色问题
给定n、m两个参数
一共有n个格子，每个格子可以涂上一种颜色，颜色在m种里选
当涂满n个格子，并且m种颜色都使用了，叫一种有效方法
求一共有多少种有效的涂色方法
1 <= n, m <= 5000
结果比较大请 % 1000000007 之后返回
对数器验证

好题, 琢磨琢磨

 */
public class Problem04_FillCellsUseAllColorsWays {
    private int MOD = (int)1e9 + 7;
    private int fact(int n){
        if(n == 1) return 1;
        return (int)(((long)n * fact(n - 1)) % MOD);
    }
    /**
       使用第二类斯特林数, 由于斯特林数中所分成的集合不考虑顺序, 而这里颜色需要考虑顺序, 因此最后需要乘以 m!  
     */
    public int FillCellsUseAllColorsWays_StirlingNumber(int n, int m){
        int MOD = (int)1e9 + 7;
        long[][] dp = new long[n + 1][m + 1];     // dp[i][j]: 使用[0, j]中的颜料涂[0, i]区间的格子, 此时的方案数
                                                  // dp[i][j]实际上计算的是斯特林数S(i, j)
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = ((dp[i - 1][j] * j) % MOD + dp[i - 1][j - 1]) % MOD;
            }
        }
        return (int)((dp[n][m] * fact(m)) % MOD);
    }

    /**
        不需要乘以 m! , 直接推出来的就是答案
     */
    public int FillCellsUseAllColorsWays(int n, int m){
        int MOD = (int)1e9 + 7;
        long[][] dp = new long[n + 1][m + 1];     // dp[i][j]: 使用 j种 颜料涂[0, i]区间的格子, 方案数
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                // 1. 用的旧颜色(之前用过)    2. 用的新颜色(之前没用过)
                dp[i][j] = ((dp[i - 1][j] * j) % MOD + (dp[i - 1][j - 1] * (m - j + 1)) % MOD) % MOD;
            }
        }
        return (int)dp[n][m];
    }

    /**
        对拍方法
     */
    private int[] path;
    private int cnt = 0;
    public int calcStd(int n, int m){
        path = new int[n];
        Arrays.fill(path, -1);
        dfs(0, n, m);
        return cnt;
    }

    private void dfs(int i, int n, int m){
        if(i == n){
            boolean[] vis = new boolean[m];
            for(int j = 0;j < n;j++){
                vis[path[j]] = true;
            }
            boolean flag = true;
            for(int j = 0;j < m;j++){
                if(!vis[j]) {
                    flag = false;
                    break;
                }
            }
            if(flag) cnt++;
            return;
        }
        for(int j = 0;j < m;j++){
            path[i] = j;
            dfs(i + 1, n, m);
        }
        path[i] = -1;
    }

    public static void main(String[] args) {
        int n = 6, m = 5;
        int ans1 = new Problem04_FillCellsUseAllColorsWays().FillCellsUseAllColorsWays_StirlingNumber(n, m);
        int ans2 = new Problem04_FillCellsUseAllColorsWays().FillCellsUseAllColorsWays(n, m);
        int ans3 = new Problem04_FillCellsUseAllColorsWays().calcStd(n, m);
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(ans3);
    }
}
