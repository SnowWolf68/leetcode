package problemList.dp.solution;

/**
dp[i] 表示将nums[0, i]区间划分后, 能够得到的元素最大和
    dp[i]: 枚举最后一个子数组的起始位置j
        dp[i] = dp[j - 1] + max[j][i] * (i - j + 1), 其中max[j][i]表示nums[j, i]区间元素的最大值
        我们可以倒序遍历j, 这样就能够在遍历的同时, 使用一个max变量来维护max[j][i]
    对于所有可能的j, 只需要取一个max即可
初始化: j - 1可能越界, 需要添加一个辅助节点, 初始化dp[0] = 0
return dp[n - 1];
 */
public class LC1043 {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            int max = 0;
            for(int j = i;j > Math.max(0, i - k);j--){
                max = Math.max(max, arr[j - 1]);
                dp[i] = Math.max(dp[i], dp[j - 1] + max * (i - j + 1));
            }
        }
        return dp[n];
    }
}
