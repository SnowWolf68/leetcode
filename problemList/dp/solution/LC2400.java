package problemList.dp.solution;

/**
dp[i][j] 表示移动i步, 走到j下标, 此时所有可能的方案数
    dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j + 1]);
初始化: j - 1, j + 1我们可以通过判断来保证不越界, 这里我们只需要考虑i - 1的越界问题
    因此我们添加一行辅助节点, 第一行此时意味着走0步, 此时走到下标j, 此时所需要的方案数
    那么此时显然只有dp[0][startPos] == 1, 其余位置都是0
特别的: 这里由于下标我们可能会走到负数, 因此我们需要让startPos和endPos进行平移, 以此来避免出现负数的情况
定义diff = min(startPos, endPos) - k, 我们此时需要让startPos, endPos都向左平移diff个单位
    如果diff < 0, 那么此时就是向右平移
这样平移之后, 就能保证startPos, endPos都是非负, 并且在平移的过程中不会移动到负数下标
 */
public class LC2400 {
    public int numberOfWays(int startPos, int endPos, int k) {
        int diff = Math.min(startPos, endPos) - k;
        startPos -= diff; endPos -= diff;
        System.out.println(startPos + " " + endPos);
        int n = Math.max(startPos, endPos) + k;
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[k + 1][n + 1];
        dp[0][startPos] = 1;
        for(int i = 1;i <= k;i++){
            for(int j = 0;j <= n;j++){
                if(j - 1 >= 0) dp[i][j] = dp[i - 1][j - 1];
                if(j + 1 <= n) dp[i][j] = (dp[i][j] + dp[i - 1][j + 1]) % MOD;
            }
        }
        return dp[k][endPos];
    }
}
