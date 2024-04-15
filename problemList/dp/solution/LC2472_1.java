package solution;

/**
这题和LC132的区别在于: 1) 不需要将整个s都分割成回文串, 可能存在某一段不是回文串的情况  2) 划分出来的回文串有长度限制

dp预处理每一个子串是否回文 + 划分型dp

时间复杂度: O(n ^ 2)
 */
public class LC2472_1 {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] info = new boolean[n][n];
        for(int i = 0;i < n;i++) info[i][i] = true;
        for(int i = n - 1;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                if(s.charAt(i) == s.charAt(j)) info[i][j] = i + 1 == j ? true : info[i + 1][j - 1];
                else info[i][j] = false;
            }
        }
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = dp[i - 1];
            for(int j = 1;j <= Math.max(0, i - k + 1);j++){
                if(info[j - 1][i - 1]) dp[i] = Math.max(dp[i], dp[j - 1] + 1);
            }
        }
        return dp[n];
    }
}
