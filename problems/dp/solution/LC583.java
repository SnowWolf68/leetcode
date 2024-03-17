package problems.dp.solution;

import java.util.*;

/**
dp[i][j] 表示使s1[0, i] 区间的子串和s2[0, j]区间的子串相等的最少步数
    dp[i][j]: 
        1. s1[i] == s2[j]: dp[i][j] = dp[i - 1][j - 1];
        2. s1[i] != s2[j]: dp[i][j] = min(dp[i][j - 1], dp[i - 1][j]) + 1;
    初始化: 添加一行一列辅助节点, 第一行: 此时s1为空串, 最小步数为s2的长度
                              第一列: 此时s2为空串, 最小步数为s1的长度
 */
public class LC583 {
    public int minDistance(String word1, String word2) {
        char[] s1 = word1.toCharArray(), s2 = word2.toCharArray();
        int m = s1.length, n = s2.length;
        int[][] dp = new int[m + 1][n + 1];
        for(int j = 0;j <= n;j++) dp[0][j] = j;
        for(int i = 0;i <= m;i++) dp[i][0] = i;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s1[i - 1] == s2[j - 1]) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + 1;
            }
        }
        return dp[m][n];
    }
}
