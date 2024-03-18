package problems.dp.solution;

import java.util.*;

/**
子序列的子序列, 考虑每个和为k的子序列的贡献: 
    假设和为k的子序列S的长度为c, 那么一共有2 ^ (n - c)个包含S的子序列, 其中n为nums数组的长度   (对于每个不在S中的元素, 其都有选或不选两种可能)
    因此我们可以认为: 每个和为k的子序列, 假设其长度为c, 那么其对答案的贡献为2 ^ (n - c)

    因此题目可以转化为求: 和为k, 并且长度为c (1 <= c <= n) 的子序列的数目
    这可以使用二维0-1背包解决: 每个元素为nums[i], 要求元素数量恰好为c, 并且元素的和恰好为k, 求此时一共有多少种选法

    dp[i][j][c] 表示考虑[0, i]区间的这些元素, 此时和为j, 并且当前元素数量为c, 此时所有选法的种类数
        dp[i][j][c]: 考虑最后一个元素选或不选
            1. 选: dp[i][j][c] = dp[i - 1][j - nums[i]][c - 1];
            2. 不选: dp[i][j][c] = dp[i - 1][j][c];
            上面两种情况取一个和即可
        初始化: j, c这两个维度不会越界, 只需要考虑i这个维度即可, 添加一面的辅助节点(i == 0), 这一面意味着当前没有元素, 那么显然只有dp[0][0][0] = 1这个位置合法, 其余位置都是非法, 都初始化为0
    
    假设和为k, 并且长度为c的子序列的数量为cnt, 那么这些子序列对答案的贡献为cnt * 2 ^ (n - c), 因此最终结果为dp[n - 1][k][c] * 2 ^ (n - c) 其中(1 <= c <= n)

    注: 这题的溢出也比较难处理, 具体如何结局看代码实现
 */
public class LC3082_1 {
    public int sumOfPower(int[] nums, int k) {
        int n = nums.length, MOD = (int)1e9 + 7;
        long[][][] dp = new long[n + 1][k + 1][n + 1];
        dp[0][0][0] = 1;
        for(int i = 1;i <= n;i++){
            // 注意: 这里的三维dp和二维dp的填表的起始位置不同, 需要特别注意
            // 这里只是初始化了i == 0的这一面, j和c都需要从0开始填表
            for(int j = 0;j <= k;j++){
                for(int c = 0;c <= n;c++){
                    dp[i][j][c] = dp[i - 1][j][c];
                    if(j - nums[i - 1] >= 0 && c - 1 >= 0) dp[i][j][c] = (dp[i][j][c] + dp[i - 1][j - nums[i - 1]][c - 1]) % MOD;
                }
            }
        }
        long ret = 0;
        long pow2 = 1;
        for(int c = n;c > 0;c--){
            ret = (ret + (dp[n][k][c] * pow2) % MOD) % MOD;
            pow2 = (pow2 * 2) % MOD;
        }
        return (int)ret;
    }
}
