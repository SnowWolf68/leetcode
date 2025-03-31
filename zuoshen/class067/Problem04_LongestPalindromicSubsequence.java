package zuoshen.class067;

/**
题目4
最长回文子序列
给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度
测试链接 : https://leetcode.cn/problems/longest-palindromic-subsequence/


 */
public class Problem04_LongestPalindromicSubsequence {
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int i = 0;i < n;i++) dp[i][i] = 1;
        for(int i = n - 2;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                if(s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i + 1][j - 1] + 2;
                dp[i][j] = Math.max(dp[i][j], Math.max(dp[i + 1][j], dp[i][j - 1]));
            }
        }
        return dp[0][n - 1];
    }
}
