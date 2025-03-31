package zuoshen.class068;

/**
题目3
交错字符串
给定三个字符串 s1、s2、s3
请帮忙验证s3是否由s1和s2交错组成
测试链接 : https://leetcode.cn/problems/interleaving-string/

 */
public class Problem03_InterleavingString {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if(m + n != s3.length()) return false;
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for(int i = 1;i <= m && s1.charAt(i - 1) == s3.charAt(i - 1);i++) dp[i][0] = true;
        for(int j = 1;j <= n && s2.charAt(j - 1) == s3.charAt(j - 1);j++) dp[0][j] = true;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s1.charAt(i - 1) == s3.charAt(i + j - 1)) dp[i][j] |= dp[i - 1][j];
                if(s2.charAt(j - 1) == s3.charAt(i + j - 1)) dp[i][j] |= dp[i][j - 1];
            }
        }
        return dp[m][n];
    }
}
