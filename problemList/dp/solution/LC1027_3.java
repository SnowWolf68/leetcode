package problemList.dp.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
优化: 虽然这里dp表的定义, 还是使用 最后一个元素下标i + 公差d 的定义方式, 但是状态转移这里, 我们不再是遍历所有可能的d
而是遍历前一个元素j, 这样d = nums[i] - nums[j], 这样就能够避免遍历所有d导致常数较大

这样做除了能够避免遍历d的范围过大, 还有其他好处: 
    由于这里我们遍历了前一个可能的元素, 因此dp[i][d]可以直接由dp[j][d]转移而来, 因此我们不再需要使用另外一个哈希表map来记录前面元素出现的下标信息
    也就不需要考虑如果某一个元素出现了多次, map中到底记录哪一个下标的问题
但是这里并不是意味着不需要考虑重复元素的问题, 由于这里nums[j]有可能会出现重复元素, 也就是意味着dp[i][d]在遍历j的过程中, 有可能被多次更新到, 因此每次更新都需要取一个max

虽然时间复杂度是O(n ^ 2), 但是由于这里dp表的第二个维度使用的是哈希表, 所以速度还是比较慢, 测试下来大约在600+ms
 */
public class LC1027_3 {
    public int longestArithSeqLength(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer>[] dp = new Map[n];
        Arrays.setAll(dp, idx -> new HashMap<>());
        int ret = 1;
        for(int i = 0;i < n;i++){
            for(int j = i - 1;j >= 0;j--){
                int d = nums[i] - nums[j];
                dp[i].put(d, Math.max(dp[i].getOrDefault(d, 1), dp[j].getOrDefault(d, 1) + 1));
                ret = Math.max(ret, dp[i].get(d));
            }
        }
        return ret;
    }
}
