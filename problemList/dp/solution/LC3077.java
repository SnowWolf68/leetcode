package problemList.dp.solution;

/**
dp[i][j] 表示从[0, j]区间中选出i个子数组, 此时最大的能量值
    dp[i][j]: 由于选出来的子数组不需要覆盖整个原数组, 所以这里对于下标为j的元素来说, 其有选或不选两种可能: 
        1. 不选: dp[i][j] = dp[i][j - 1];
        2. 选: 枚举最后一个子数组的起始位置m
            dp[i][j] = dp[i - 1][m - 1] + sum[m, j] * (k - i + 1) * (-1) ^ (i + 1); // 其中sum[m, j]表示nums中, [m, j]区间的元素和
            这里时间复杂度限制在O(n * k), 那么这里计算dp[i][j], 显然需要优化到O(1)的时间内
            dp[i][j] = max(dp[i - 1][m - 1] + sum[m, j] * (k - i + 1) * (i % 2 == 0 ? -1 : 1));
            假设我们有前缀和数组sum[], 其中sum[j + 1]表示nums[0, j]区间的和, 这里sum[]数组由于只和nums有关, 因此我们可以预处理得到, 那么有: 
            -> max(dp[i - 1][m - 1] + (sum[j + 1] - sum[m]) * (k - i + 1) * (i % 2 == 0 ? -1 : 1));
            将和m相关项提取出来: 
            -> max(dp[i - 1][m - 1] - sum[m] * (k - i + 1) * (i % 2 == 0 ? -1 : 1) + sum[j + 1] * (k - i + 1) * (i % 2 == 0 ? -1 : 1));
            值得注意的是: 和m相关项: dp[i - 1][m - 1] - sum[m] * (k - i + 1) * (i % 2 == 0 ? -1 : 1) 中, 不包含j, 也就是和j无关
            并且m的范围: [0, j], 那么我们可以在遍历j的时候, 使用一个变量记录 "和m相关项" 的最大值max
            那么dp[i][j]的更新可以简化为: dp[i][j] = max + sum[j + 1] * (k - i + 1) * (i % 2 == 0 ? -1 : 1);
            特别的: 由于在实际的dp中, j这里我们添加了一列辅助节点, 因此我们需要考虑sum[]的下标映射关系, 即: 
                和m相关项变成: dp[i - 1][j - 1] - sum[j - 1] * (k - i + 1) * (i % 2 == 0 ? -1 : 1)
                dp[i][j]的更新变成: dp[i][j] = max + sum[j] * (k - i + 1) * (i % 2 == 0 ? -1 : 1);
    对于所有的可能, 只需要取一个max即可
初始化: i == 0本身就是辅助节点, i == 0此时意味着选不出任何子数组, 由于我们不需要让选出来的子数组覆盖整个原数组, 所以此时第一行的dp值都是0
    第一列添加一列辅助节点, j == 0此时意味着当前没有任何元素, 那么此时dp[0][0] = 0, 其余位置都是非法, 初始化为-INF
return dp[k][n - 1];
 */
public class LC3077 {
    public long maximumStrength(int[] nums, int k) {
        int n = nums.length;
        long INF = Long.MAX_VALUE / 2;
        long[][] dp = new long[k + 1][n + 1];
        for(int i = 1;i <= k;i++) dp[i][0] = -INF;
        long[] sum = new long[n + 1];
        for(int i = 1;i <= n;i++) sum[i] = sum[i - 1] + nums[i - 1];
        for(int i = 1;i <= k;i++){
            long max = -INF;
            for(int j = 1;j <= n;j++){
                dp[i][j] = dp[i][j - 1];
                max = Math.max(max, dp[i - 1][j - 1] - sum[j - 1] * (k - i + 1) * (i % 2 == 0 ? -1 : 1));
                dp[i][j] = Math.max(dp[i][j], max + sum[j] * (k - i + 1) * (i % 2 == 0 ? -1 : 1));
            }
        }
        return dp[k][n];
    }
}
