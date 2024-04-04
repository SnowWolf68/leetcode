package problemList.dp.solution;

/**
关键: 通过s1, s2的区间, 可以推出s3的区间范围
dp[i][j] 表示考虑s1[0, i]区间和s2[0, j]区间的字符串, 是否可以交错构造出s3[0, i + j + 1]区间的字符串
    dp[i][j]: 考虑s3的最后一个字符, 即下标为i + j + 1的字符
    1. s3[i + j + 1] == s1[i]: dp[i][j] = dp[i - 1][j];
    2. s3[i + j + 1] == s2[j]: dp[i][j] = dp[i][j - 1];
    注: 1, 2两条可能同时满足
    3. s3[i + j + 1] != s1[i] && s3[i + j + 1] != s2[j]: dp[i][j] = false;
初始化: 添加一行一列辅助节点, 第一行: s1为空串, 那么此时取决于s2是否能拼出s3对应区间
                          第一列: s2为空串, 那么此时取决于s1是否能拼出s2对应区间
 */
public class LC97 {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if(m + n != s3.length()) return false;
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for(int j = 1;j <= n;j++){
            dp[0][j] = dp[0][j - 1] & (s2.charAt(j - 1) == s3.charAt(j - 1));
        }
        for(int i = 1;i <= m;i++){
            dp[i][0] = dp[i - 1][0] & (s1.charAt(i - 1) == s3.charAt(i - 1));
        }
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s3.charAt(i + j - 1) == s1.charAt(i - 1)) dp[i][j] |= dp[i - 1][j];
                if(s3.charAt(i + j - 1) == s2.charAt(j - 1)) dp[i][j] |= dp[i][j - 1];
            }
        }
        return dp[m][n];
    }
}
