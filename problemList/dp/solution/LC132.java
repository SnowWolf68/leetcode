package problemList.dp.solution;

public class LC132 {
    public int minCut(String s) {
        int n = s.length(), INF = 0x3f3f3f3f;
        // 预处理s的每一个子串是不是回文串 -- 用dp进行预处理
        boolean[][] info = new boolean[n][n];
        for(int i = 0;i < n;i++){
            info[i][i] = true;
        }
        for(int i = n - 1;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                if(s.charAt(i) == s.charAt(j)) info[i][j] = (i + 1 == j) ? true : info[i + 1][j - 1];
                else info[i][j] = false;
            }
        }
        // dp
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = 1;j <= i;j++){
                if(info[j - 1][i - 1]){
                    dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                }
            }
        }
        return dp[n] - 1;
    }
}
