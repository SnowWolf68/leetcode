package problemList.dp.solution;

/*
 * 错解, 值得思考
 */
public class LC3504_WA {
    public int longestPalindrome(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for(int j = 0;j < n;j++){
            // dp[0][j] = t[j, n - 1]区间的子串是否是回文 ? [j, n - 1]区间子串长度 : 0
            dp[0][j] = check(t, j, n - 1) ? n - j : 0;
        }
        for(int i = 1;i <= m;i++){
            // dp[i][n] = s[0, i - 1]区间的子串是否是回文 ? [0, i - 1]区间子串长度 : 0
            dp[i][n] = check(s, 0, i - 1) ? i : 0;
        }
        for(int i = 1;i <= m;i++){
            for(int j = n - 1;j >= 0;j--){
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j + 1]);
                if(s.charAt(i - 1) == t.charAt(j)) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + 1] + 2);
            }
        }
        return dp[m][0];
    }

    // check s的[start, end]区间的子串是否是回文串
    private boolean check(String s, int start, int end){
        boolean flag = true;
        while(start < end){
            if(s.charAt(start) != s.charAt(end)) {
                flag = false;
                break;
            }
            start++;
            end--;
        }
        return flag;
    }
}
