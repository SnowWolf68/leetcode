package solution;

/**
dp[i][j] 表示从i所代表的(x, y)这里, 可用步数不超过j时, 走出格子的路径数量
dp[i][j]: 假设i对应的格子位置是(x, y), 那么
        dp[i][j] = dp(x - 1, y)[j - 1] + dp(x + 1, y)[j - 1] + dp(x, y - 1)[j - 1] + dp(x, y + 1)[j - 1]
初始化: 
 */
public class LC576_2 {
    
}
