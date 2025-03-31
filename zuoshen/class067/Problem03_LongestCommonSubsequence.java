package zuoshen.class067;

/**
题目3
最长公共子序列
给定两个字符串text1和text2
返回这两个字符串的最长 公共子序列 的长度
如果不存在公共子序列，返回0
两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列
测试链接 : https://leetcode.cn/problems/longest-common-subsequence/


 */
public class Problem03_LongestCommonSubsequence {
    public int longestCommonSubsequence(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = Math.max(dp[i - 1][j - 1], Math.max(dp[i - 1][j], dp[i][j - 1]));
                if(s1.charAt(i - 1) == s2.charAt(j - 1)) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
            }
        }
        return dp[m][n];
    }
}
