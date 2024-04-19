package problemList.dp.solution;

import java.util.Arrays;

/**
dp[i][j] 表示将nums数组的[0, j]区间的元素, 划分为i个连续子数组, 此时各个子数组和的最大值的最小值
    dp[i][j]: 枚举最后一个子数组的起始下标m
        dp[i][j] = Math.max(dp[i - 1][m - 1] + sum[m][j]), 其中sum[m][j]表示nums中[m, j]区间的元素和
        我们可以倒序遍历m, 这样就能够在遍历的同时使用一个变量sum计算得到sum[m, j]
    对于所有m的可能, 我们只需要取一个min即可
初始化: 由于i从1开始计数, 因此i == 0天然就是一个辅助节点, 第一行意味着当前没有划分成任何子数组, 那么只有dp[0][0] = 0, 其余位置都是非法, 初始化为INF
    第一列意味着此时没有任何元素, 类似的, 只有dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return dp[k][n - 1];
 */
public class LC410 {
    public int splitArray(int[] nums, int k) {
        int n = nums.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[k + 1][n + 1];
        Arrays.fill(dp[0], INF);
        for(int i = 0;i <= k;i++) dp[i][0] = INF;
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                int sum = 0;
                for(int m = j;m >= 1;m--){
                    sum += nums[m - 1];
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][m - 1], sum));
                }
            }
        }
        return dp[k][n];
    }
}