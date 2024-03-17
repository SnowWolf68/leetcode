package problems.dp.solution;

/**
dp[i][j] 表示往arr[0, j]区间插入若干元素后, 使得target[0, i]区间的子数组成为arr[0, j]区间的一个子序列, 此时最少需要插入多少个元素
    dp[i][j]: 分析target和arr的最后一个元素
        1. target[i] == arr[j]: dp[i][j] = dp[i - 1][j - 1];
        2. target[i] != arr[j]: dp[i][j] = min(dp[i][j - 1], dp[i - 1][j] + 1);     // 此时有两种选择: 1. 插入一个元素 2. 使用arr[0, j - 1]区间来进行匹配
    初始化: 辅助节点, 第一行: target为空数组, 此时插入元素数量0, 第一列: 此时arr为空数组, 此时插入数量为target对应区间的元素数量

    
 */
public class LC1713_MLE {
    public int minOperations(int[] target, int[] arr) {
        int m = target.length, n = arr.length;
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 1;i <= m;i++) dp[i][0] = i;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(target[i - 1] == arr[j - 1]) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j] + 1);
            }
        }
        return dp[m][n];
    }
}
