package problemList.dp.solution;

public class LC72 {
    public int minDistance(String word1, String word2) {
        char[] s1 = word1.toCharArray(), s2 = word2.toCharArray();
        int m = s1.length, n = s2.length;
        // dp[i][j] 表示考虑s1中[0, i]区间的字符, s2[0, j]区间的字符, 此时的最小修改次数
        // 初始化: 添加辅助节点     或者这里也可以字符串前面都拼接一个空格, 这里我就不拼接了
        int[][] dp = new int[m + 1][n + 1];
        // 第一行: s1为空串, dp = 第二个串对应区间的长度
        for(int j = 0;j <= n;j++) dp[0][j] = j;
        // 第一列同理
        for(int i = 0;i <= m;i++) dp[i][0] = i;
        // dp
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s1[i - 1] == s2[j - 1]){
                    dp[i][j] = dp[i - 1][j - 1];
                }else{
                    // 插入, 删除, 替换 三种可能
                    dp[i][j] = Math.min(dp[i][j - 1] + 1, Math.min(dp[i - 1][j] + 1, dp[i - 1][j - 1] + 1));
                }
            }
        }
        return dp[m][n];
    }
}
