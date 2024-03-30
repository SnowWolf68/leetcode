package problemList.dp.solution;

/**
贡献法: 考虑每一个和为k的子序列对答案的贡献
假设和为k的子序列的长度为c, 那么其对答案的贡献为2 ^ (n - c), 这里n = nums.length
因此题目转化为求: 和为k, 并且长度为c的子序列的数目, 然后对每一个符合要求的子序列, 将其对答案的贡献2 ^ (n - c)累加即可
dp[i][j][k] 表示考虑[0, i]区间的子序列, 和为j, 长度为k的子序列的数目
    dp[i][j][k]: 对于nums[i], 有选或不选两种可能
        1. 选: dp[i][j][k] += dp[i - 1][j - nums[i]][k - 1];
        2. 不选: dp[i][j][k] += dp[i - 1][j][k];
初始化: i这个维度添加一面辅助节点, 此时意味着当前没有任何元素, 那么此时只有dp[0][0][0] = 1, 其余位置都是0

时间复杂度: O(n ^ 2 * k)
 */
public class LC3082_1 {
    public int sumOfPower(int[] nums, int k) {
        int n = nums.length, MOD = (int)1e9 + 7;;
        int[][][] dp = new int[n + 1][k + 1][n + 1];
        dp[0][0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= k;j++){
                for(int p = 0;p <= n;p++){
                    dp[i][j][p] = dp[i - 1][j][p];
                    if(j - nums[i - 1] >= 0 && p - 1 >= 0) dp[i][j][p] = (dp[i][j][p] + dp[i - 1][j - nums[i - 1]][p - 1]) % MOD;
                }
            }
        }
        int ret = 0, pow = 1;
        for(int c = n;c >= 0;c--){
            ret = (ret + (int)(((long)dp[n][k][c] * pow) % MOD)) % MOD;
            pow = (pow * 2) % MOD;
        }
        return ret;
    }
}
