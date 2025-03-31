package zuoshen.class068;

/**
题目2
编辑距离
给你两个单词 word1 和 word2
请返回将 word1 转换成 word2 所使用的最少代价
你可以对一个单词进行如下三种操作：
插入一个字符，代价a
删除一个字符，代价b
替换一个字符，代价c
测试链接 : https://leetcode.cn/problems/edit-distance/

注意：
测试里说的题意，只是编辑距离问题的一种情况，请掌握完整的编辑距离问题  -->  关注 minDistance1

 */
public class Problem02_EditDistance {
    public int minDistance(String s, String t) {
        int m = s.length(), n = t.length(), INF = 0x3f3f3f3f;
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0;i <= m;i++) dp[i][0] = i;
        for(int j = 0;j <= n;j++) dp[0][j] = j;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                if(s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                // 插入, 删除, 修改
                dp[i][j] = Math.min(dp[i][j], Math.min(dp[i][j - 1] + 1, Math.min(dp[i - 1][j] + 1, dp[i - 1][j - 1] + 1)));
            }
        }
        return dp[m][n];
    }

    /**
     * 如果插入, 删除, 修改三种操作的代价不同, 分别为cost[0], cost[1], cost[2]
     */
    public int minDistance1(String s, String t) {
        int m = s.length(), n = t.length(), INF = 0x3f3f3f3f;
        int[][] dp = new int[m + 1][n + 1];
        int[] cost = new int[]{1, 1, 1};
        for(int i = 0;i <= m;i++) dp[i][0] = i;
        for(int j = 0;j <= n;j++) dp[0][j] = j;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                if(s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                // 插入, 删除, 修改
                dp[i][j] = Math.min(dp[i][j], Math.min(dp[i][j - 1] + cost[0], Math.min(dp[i - 1][j] + cost[1], dp[i - 1][j - 1] + cost[2])));
            }
        }
        return dp[m][n];
    }
}
