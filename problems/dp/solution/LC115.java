package problems.dp.solution;

import java.util.*;

/**
这题的题目要读明白, 可以看看示例, 明白要求什么数目
    dp[i][j] 表示考虑s[0, i]区间的子序列中, 有多少个t[0, j]区间的子串出现过
        dp[i][j]: 考虑s[0, i]区间的子序列是否包括s[i]
        1. s[0, i]区间的子序列包括s[i]: 
            1. s[i] == t[j]: dp[i][j] = dp[i - 1][j - 1];   // 意味着在s[0, i - 1]区间出现的所有t[0, j - 1]区间的子串, 都可以拼接上s[i], 变成一个t[0, j]区间的子串
            2. s[i] != t[j]: dp[i][j] = 0;
        2. s[0, i]区间的子序列不包括s[j]: dp[i][j] = dp[i - 1][j];
    初始化: 添加一行一列的辅助节点, 第一行: s串为空串, 此时dp[0][0] = 1, 其余位置都是0
                                第一列: t为空串, 此时第一列全都是1, 因为空串在s包括空字符的前提下, 都出现了一次
 */
public class LC115 {
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0;i <= m;i++) dp[i][0] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                if(s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] += dp[i - 1][j - 1];
            }
        }
        return dp[m][n];
    }
}
