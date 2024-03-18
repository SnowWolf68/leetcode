package problems.dp.solution;

import java.util.*;

/**
状态表示优化: 
    假设和为k的子序列为S, 包含S的子序列为T, 那么这题其实就是要求nums中所有T的数量
    定义dp[i][j] 表示考虑nums[0, i]区间的元素, 此时包含 和为j的子序列S 的 子序列T 的数量
        dp[i][j]: 考虑最后一个元素
            1. 如果最后一个元素即不在序列S中, 也不在序列T中: dp[i][j] = dp[i - 1][j];
            2. 如果最后一个元素不在序列S中, 但是在序列T中: dp[i][j] = dp[i - 1][j];
            3. 如果最后一个元素在序列S中: dp[i][j] = dp[i - 1][j - nums[i]];
        对上面三种情况求一个和即可
    初始化: 第二维不会越界, 只需要考虑第一维的初始化即可, 添加一行辅助节点
        第一行: 表示此时没有元素, 那么此时显然子序列和只能是0, 即dp[0][0] = 1, 其余位置都是非法, 初始化为0
    return dp[n - 1][k];
 */
public class LC3082_2 {
    public int sumOfPower(int[] nums, int k) {
        int n = nums.length, MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= k;j++){
                dp[i][j] = (2 * dp[i - 1][j]) % MOD;
                if(j - nums[i - 1] >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - nums[i - 1]]) % MOD;
            }
        }
        return dp[n][k];
    }
}
