package problemList.dp.solution;

/**
* 如何判断一条括号路径是合法路径?
    定义 "平衡度c" 如果遇到左括号, c + 1, 如果遇到右括号, c - 1
    那么在 "平衡度" 的定义下, 一条括号路径是合法路径, 等价于满足下面两个条件
        1. 对于路径上的每一个节点, 其c >= 0
        2. 对于路径的结束点, c == 0
dp[i][j][k] 表示从[0, 0] 到 [i, j] , 是否存在一条平衡度为k的路径
    dp[i][j][k]: 考虑[i, j]可以由哪些状态转移而来
        定义cur = grid[i][j] == '(' ? 1 : -1;
        dp[i][j][k] = dp[i][j - 1][k - cur] || dp[i - 1][j][k - cur];
初始化: 添加一行一列的辅助节点, 辅助节点的值首先全部初始化为false, 然后手动初始化dp[0][1][0] = true;
return dp[m - 1][n - 1][0];

考虑k的范围: 
    由于这里 "合法路径" 要求对于路径上的每个节点, 都需要有c >= 0, 因此对于k, 我们只需要考虑k >= 0的部分即可
    考虑k的上限: 如果全都是左括号, 那么k至多是m + n - 1, 因此这里k的范围是[0, m + n - 1]
 */
public class LC2267 {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length, kMax = m + n - 1;
        boolean[][][] dp = new boolean[m + 1][n + 1][kMax + 1];
        dp[0][1][0] = true;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                for(int k = 0;k <= kMax;k++){
                    int cur = grid[i - 1][j - 1] == '(' ? 1 : -1;
                    if(k - cur >= 0 && k - cur <= kMax) dp[i][j][k] = dp[i][j - 1][k - cur] | dp[i - 1][j][k - cur];
                }
            }
        }
        return dp[m][n][0];
    }
}
