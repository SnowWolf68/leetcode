package problemList.dp.solution;

/**
arr1[i] + arr2[i] == nums[i]
-> arr2[i] = nums[i] - arr1[i]
枚举arr1[i], arr2[i]就可以确定下来
从前往后一起填写arr1, arr2, 对于每一个下标i, 此时要填的arr1[i]需要满足:
arr1[i] >= arr1[i - 1]
arr2[i] = nums[i] - arr1[i] <= arr2[i - 1]  --> arr1[i] >= nums[i] - arr2[i - 1]

因此在dp的过程中, 还需要记录 上一个位置的arr1[i - 1], arr2[i - 1] 的值

dp[i][j][k]: 表示考虑 [0, i] 区间的下标, 并且arr1[i - 1] = j, arr2[i - 1] = k, 此时单调数组对的数目
dp[i][j][k]: 
    首先枚举下标i
        然后枚举arr1[i]的所有可能: 
            这里arr1[i]的范围: [Math.max(arr1[i - 1], nums[i] - arr2[i - 1]), nums[i]]
            对于每一个可能的arr1[i], 这里假设枚举到的值为 p , 那么
            首先能够计算出来此时 arr2[i] = nums[i] - p
            dp[i][p][]

 */
public class LC3250_1 {
    public int countOfPairs(int[] nums) {
        int max = 0, n = nums.length, MOD = (int)1e9 + 7;
        for(int x : nums) max = Math.max(max, x);
        int[][][] dp = new int[n][max + 1][max + 1];
        for(int i = 0;i <= nums[0];i++){
            dp[0][i][nums[0] - i] = 1;
        }
        for(int i = 1;i < n;i++){
            for(int j = 0;j <= nums[i];j++){
                for(int prev = 0;prev <= Math.min(j, nums[i - 1]);prev++){
                    if(nums[i] - j <= nums[i - 1] - prev) dp[i][j][nums[i] - j] = (dp[i][j][nums[i] - j] + dp[i - 1][prev][nums[i - 1] - prev]) % MOD;
                }
            }
        }
        int ret = 0;
        for(int j = 0;j <= max;j++){
            for(int k = 0;k <= max;k++){
                ret = (ret + dp[n - 1][j][k]) % MOD;
            }
        }
        return ret;
    }
}
