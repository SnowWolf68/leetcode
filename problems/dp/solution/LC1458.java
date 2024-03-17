package problems.dp.solution;

/**
关键细节: 如果一个数组全为正, 另一个全为负, 那么此时无法通过dp正确得到最大点积, 因为辅助节点都默认初始化成0
        在 辅助节点初始化成0 的情况下, 此时dp得到的结果一定是 >= 0的
        而除了这种特殊情况之外, 其余情况为了能够正确dp, 辅助节点还 必须 初始化成0
        因此我们可以特判这种特殊情况: 
            如果出现了一个数组全为正, 另一个全为负的情况, 那么此时显然任意组合的点乘积都为负, 因此我们只需要找到相乘最大的两个元素即可
            此时两个数组的最大点乘积就是这 相乘最大的两个元素 的乘积
 */
public class LC1458 {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        int max = Integer.MIN_VALUE;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                max = Math.max(max, nums1[i - 1] * nums2[j - 1]);
                dp[i][j] = Math.max(dp[i - 1][j - 1] + nums1[i - 1] * nums2[j - 1], Math.max(dp[i][j - 1], dp[i - 1][j]));
            }
        }
        return dp[m][n] > 0 ? dp[m][n] : max;
    }
}
