package problemList.dp.solution;

/**
dp预处理出所有子串是否是回文串, 然后枚举两个分割点即可, 复杂度O(n ^ 2)
TODO: 是不是还有O(n)的解法???
 */
public class LC1745 {
    public boolean checkPartitioning(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for(int i = 0;i < n;i++) dp[i][i] = true;
        for(int i = n - 2;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                if(s.charAt(i) == s.charAt(j) && i + 1 == j) dp[i][j] = true;
                else if(s.charAt(i) == s.charAt(j) && i + 1 != j) dp[i][j] = dp[i + 1][j - 1];
                else dp[i][j] = false;
            }
        }
        for(int i = 0;i < n;i++){
            for(int j = i + 1;j < n - 1;j++){
                if(dp[0][i] && dp[i + 1][j] && dp[j + 1][n - 1]) return true;
            }
        }
        return false;
    }
}
