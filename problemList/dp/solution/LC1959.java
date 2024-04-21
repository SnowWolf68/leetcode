package problemList.dp.solution;

import java.util.Arrays;

/**
dp[i][j] 表示对[0, j]区间的元素来说, 调整i次, 此时浪费的最少空间
    dp[i][j]: 枚举最后一次调整的起始时间m
        dp[i][j] = dp[i - 1][m - 1] + waste[m, j], 其中waste[m, j]表示对[m, j]区间的元素来说, 进行一次调整, 此时浪费空间的最小值
            对于waste[m, j]的计算, 显然此时调整后的size = max(nums[m, j]), 那么我们可以倒序遍历k, 同时记录最大值, 并且使用一个变量sum记录[k, j]区间的和, 然后就可以计算[m, j]区间浪费的空间
    对于所有可能的k, 只需要取一个min即可
初始化: i == 0本身就是辅助节点, 第一行意味着当前没有任何调整, 那么此时dp[0][0] = 0, 其余位置都是INF
    第一列添加一列辅助节点, 初始化类似, dp[0][0] = 0, 其余位置都是INF
return dp[k][n - 1];

特别的: 由于一开始也能确定一个容量, 那么相当于一开始也算一次调整, 所以这里需要让调整次数 + 1
 */
public class LC1959 {
    public int minSpaceWastedKResizing(int[] nums, int k) {
        k++;
        int n = nums.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[k + 1][n + 1];
        Arrays.fill(dp[0], INF);
        for(int i = 0;i <= k;i++) dp[i][0] = INF;
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                int max = 0;
                int sum = 0;
                for(int m = j;m > 0;m--){
                    max = Math.max(max, nums[m - 1]);
                    sum += nums[m - 1];
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][m - 1] + max * (j - m + 1) - sum);
                }
            }
        }
        return dp[k][n];
    }
}
