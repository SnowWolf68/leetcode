package revise_problemList;

import java.util.Arrays;

/**
划分型dp
dp[i][j] 表示将以i结尾的子数组划分为j个非空的连续子数组, 此时这些子数组各自和的最大值
dp[i][j]: 枚举最后一个划分的起始位置, 假设为p
    dp[i][j] = Math.min(sum(nums[p] ~ nums[i]), dp[p - 1][j - 1])
初始化: p - 1, j - 1可能越界, 添加一行一列辅助节点 (第一列本身就自带一列辅助节点)
    第一行: 此时没有任何元素, 那么dp[0][0] = 0, 其余位置都是非法, 初始化为INF
    第一列: 此时划分为0个子数组, 那么dp[0][0] = 0, 其余位置也是非法, 初始化为INF
return: dp[n][k]
 */
public class LC410 {
    public int splitArray(int[] nums, int k) {
        int n = nums.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][k + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++) dp[i][0] = INF;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= k;j++){
                dp[i][j] = INF;
                int sum = 0;
                for(int p = i;p >= 1;p--){
                    sum += nums[p - 1];
                    dp[i][j] = Math.min(dp[i][j], Math.max(sum, dp[p - 1][j - 1]));
                }
            }
        }
        return dp[n][k];
    }
}
