package problemList.dp.solution;

/**
多重背包: 
    dp[i][j] 表示考虑[0, i]区间的题目, 当前得到j分的方法数
        dp[i][j]: 假设下标为i的题目做了k道, 这里k的范围: [0, types[i][0]]
            dp[i][j] += dp[i - 1][j - k * types[i][1]];
    初始化: 初始化i这个维度, 添加一行辅助节点, 第一行此时意味着当前没有任何题目, 此时得分只能是0, 初始化dp[0][0] = 1, 其余位置都是非法, 初始化为0
    return dp[n - 1][target];

    时间复杂度: O(n * target * max(types[i][0]))
 */
public class LC2585_1 {
    public int waysToReachTarget(int target, int[][] types) {
        int n = types.length, MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= target;j++){
                for(int k = 0;k <= types[i - 1][0];k++){
                    if(j - k * types[i - 1][1] >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - k * types[i - 1][1]]) % MOD;
                }
            }
        }
        return dp[n][target];
    }
}
