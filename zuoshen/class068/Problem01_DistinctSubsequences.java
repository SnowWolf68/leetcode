package zuoshen.class068;

/**
题目1
不同的子序列
给你两个字符串s和t
统计在s的所有子序列中
有多少个子序列等于t
测试链接 : https://leetcode.cn/problems/distinct-subsequences/

 */
public class Problem01_DistinctSubsequences {
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length(), MOD = (int)1e9 + 7;
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0;i <= m;i++) dp[i][0] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
                else dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[m][n];
    }
}
