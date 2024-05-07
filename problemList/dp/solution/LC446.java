package problemList.dp.solution;

import java.util.HashMap;
import java.util.Map;

/**
dp[i][j] 表示考虑下标不超过i的所有数, 以nums[i]结尾, 并且差为j的等差子序列的数目(这里不限制子序列的长度)
其中dp表第二个维度可以使用HashMap
    dp[i]: 遍历下标i前面的所有数, 假设其下标为k, 那么
        dp[i].put(nums[i] - nums[k], dp[i].getOrDefault(nums[i] - nums[k], 0) + dp[k].getOrDefault(nums[i] - nums[k], 0) + 1);
初始化: 不需要初始化
return dp表中所有元素的和 - 长度为2的等差序列的数目

其中, 长度为2的等差序列的数目 = (n * (n - 1)) / 2

时间复杂度: O(n ^ 2)
 */
public class LC446 {
    public int numberOfArithmeticSlices(int[] nums) {
        int n = nums.length;
        Map<Long, Integer>[] dp = new HashMap[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<>();
        }
        for (int i = 1; i < n; i++) {
            for(int k = 0; k < i; k++){
                long diff = (long)nums[i] - nums[k];
                int prevCnt = dp[k].getOrDefault(diff, 0);
                int curCnt = dp[i].getOrDefault(diff, 0);
                dp[i].put(diff, prevCnt + curCnt + 1);
            }
        }
        int ret = 0;
        for(int i = 0; i < n; i++){
            for(long key : dp[i].keySet()){
                ret += dp[i].get(key);
            }
        }
        return ret - (n * (n - 1)) / 2;
    }
}
