package problemList.dp.solution;

/**
dp[i][j] 表示让s1[0, i]区间的子串和s2[0, j]区间的子串相等, 此时所需要的最小ASCII删除和
    dp[i][j]: 
        1. s1[i] == s2[j]: dp[i][j] = dp[i - 1][j - 1]; 
        2. s1[i] != s2[j]: dp[i][j] = Math.min(dp[i - 1][j] + s1[i], dp[i][j - 1] + s2[j]);
    初始化: 辅助节点, 第一行: s2对应区间的ASCII之和, 第一列同理    
 */
public class LC712 {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for(int j = 1;j <= n;j++) dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
        for(int i = 1;i <= m;i++) dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s1.charAt(i - 1) == s2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1]; 
                else dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));
            }
        }
        return dp[m][n];
    }
}
