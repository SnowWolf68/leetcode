package problemList.dp.solution;

/**
求一次最大和(非空), 求一次最小和(可为空)
 */
public class LC918 {
    public int maxSubarraySumCircular(int[] nums) {
        int n = nums.length;
        int f = nums[0], g = nums[0], sum = nums[0], maxSum = f, minSum = g;
        for(int i = 1;i < n;i++){
            f = Math.max(f + nums[i], nums[i]);
            g = Math.min(g + nums[i], nums[i]);
            sum += nums[i];
            maxSum = Math.max(maxSum, f);
            minSum = Math.min(minSum, g);
        }
        minSum = Math.min(minSum, 0);
        return Math.max(maxSum, sum == minSum ? Integer.MIN_VALUE : sum - minSum);
    }
}
