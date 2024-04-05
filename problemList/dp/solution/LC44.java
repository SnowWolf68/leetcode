package problemList.dp.solution;

/**
dp[i][j] 表示使用s串[0, i]区间能否和p串[0, j]区间匹配
    dp[i][j]: 针对p[j]的多种字符可能, 分类讨论
        1. p[j] == '?': dp[i][j] = dp[i - 1][j - 1];
        2. p[j] == '*': 针对一个'*'能够匹配的字符数量, 此时有多重情况, 优化后的状态转移方程为: dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
        3. p[j]是小写英文字母: dp[i][j] = dp[i - 1][j - 1] && (s[i] == p[j]);
初始化: 添加一行一列的辅助节点, 第一行意味着当前s串为空串, 那么此时dp[0][0]一定是true, 对于其余位置, 此时由于 '*' 可以匹配空字符序列, 因此如果p的前缀有连续的 '*' , 那么此时对应的位置也都是true
        第一列: 此时意味着p串为空串, 那么此时dp[0][0]还是一定为true, 对于其余位置, 都是false
return dp[m - 1][n - 1];    // m, n分别是s, p串的长度
 */
public class LC44 {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        int idx = 0;
        while(idx < n && p.charAt(idx) == '*'){
            dp[0][idx + 1] = true;
            idx++;
        }
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(p.charAt(j - 1) == '?'){
                    dp[i][j] = dp[i - 1][j - 1];
                }else if(p.charAt(j - 1) == '*'){
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }else{
                    dp[i][j] = dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1));
                }
            }
        }
        return dp[m][n];
    }
}
