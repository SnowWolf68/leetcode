package solution;

public class LC2430 {
    public int deleteString(String s) {
        int n = s.length();
        // lcp[i][j] 表示以下标i开头的子串, 和以下标j开头的子串的最长公共前缀的长度
        int[][] lcp = new int[n][n + 1];
        for(int i = n - 2;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                if(s.charAt(i) == s.charAt(j)) lcp[i][j] = lcp[i + 1][j + 1] + 1;
            }
        }
        // dp[i] 表示删除[i:]的子串, 所需要的最多操作次数
        int[] dp = new int[n];
        for(int i = n - 1;i >= 0;i--){
            dp[i] = 1;
            for(int j = i + 1;j < n;j++){
                // 注意这里的判断是 >=
                if(lcp[i][j] >= j - i) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        return dp[0];
    }
}
