package problemList.dp.solution;

import java.util.Arrays;

/**
约束划分个数 "最多" 为k, 求子数组平均值的和的最大值
dp[i][j] 表示将nums[0, j]区间的元素划分为i个子数组, 此时子数组平均值的和的最大值
    dp[i][j]: 枚举最后一个划分出来的子数组的起始下标m
        dp[i][j] = dp[i - 1][m - 1] + avg[m][j], 其中avg[m][j]表示nums[m, j]区间的元素的平均值
        我们可以倒序遍历m, 这样就可以在遍历的同时使用一个变量sum维护[m, j]区间的元素和, 进而得到[m, j]区间的平均值
    对于所有m的可能, 我们只需要取一个max即可
初始化: i == 0本身就是一行辅助节点, 第一行意味着当前划分成0个子数组, 那么只有dp[0][0] = 0, 其余位置都是非法, 初始化为-INF
    第一列添加一列辅助节点, 第一列意味着当前数组中没有任何元素, 那么只有dp[0][0] = 0, 其余位置都是非法, 初始化为-INF
return dp[k][n - 1];
 */
public class LC813 {
    public double largestSumOfAverages(int[] nums, int k) {
        int n = nums.length, INF = 0x3f3f3f3f;
        double[][] dp = new double[k + 1][n + 1];
        Arrays.fill(dp[0], -INF);
        for(int i = 0;i <= k;i++) dp[i][0] = -INF;
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 0;j <= n;j++){
                int sum = 0;
                for(int m = j;m >= 1;m--){
                    sum += nums[m - 1];
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][m - 1] + (double)sum / (j - m + 1));
                }
            }
        }
        return dp[k][n];
    }
}
