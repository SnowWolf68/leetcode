package problemList.dp.solution;

import java.util.*;

/**
"不超过" 类型背包
dp[i][j] 表示考虑[0, i]区间的元素, 当前元素和不超过j的方案数
    dp[i][j]: 对于nums[i], 有选或不选两种选择
        1. 选: dp[i][j] = dp[i - 1][j - nums[i]];
        2. 不选: dp[i][j] = dp[i - 1][j];
    上面两种情况取一个和即可
初始化: 初始化i这个维度, 添加辅助节点, 第一行意味着当前没有任何元素, 那么此时由于dp[i][j]的定义是元素和"不超过"j(即 < j), 因此初始化dp[0][0] = 0, 其余位置都是1
return dp[n][k];
 */
public class LC2518_2 {
    public int countPartitions(int[] nums, int k) {
        int n = nums.length, MOD = (int)1e9 + 7, sum = 0;
        for(int x : nums) sum = (sum + x) % MOD;
        if(sum < 2 * k) return 0;
        int[][] dp = new int[n + 1][k + 1];
        Arrays.fill(dp[0], 1);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= k;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums[i - 1] >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - nums[i - 1]]) % MOD;
            }
        }
        int tot = 1;
        for(int i = 0;i < n;i++){
            tot = (tot * 2) % MOD;
        }
        return (tot - (2 * dp[n][k]) % MOD + MOD) % MOD;
    }
}
