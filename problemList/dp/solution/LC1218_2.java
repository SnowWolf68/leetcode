package problemList.dp.solution;

import java.util.HashMap;
import java.util.Map;

/**
基于值域的写法
第一种解法之所以复杂度到了O(n ^ 2), 关键在于对于重复元素的处理
由于nums中可能会有重复元素, 因此在计算dp[i]的时候, 就需要遍历所有等于nums[i] - difference的下标
因此我们需要考虑优化这一个过程
我们可以基于值域进行dp, 具体的:
    首先我们的前提是从前往后遍历, 此时dp[i]表示在元素i前面的这些元素中, 以元素i结尾的最长定差子序列的长度
        dp[i]: 此时可以确定子序列的前一个元素为i - difference, 由于dp[i]的定义就是在所有重复出现的元素i中, 定差子序列最长的长度
            因此dp[i]可以直接由dp[i - difference]转移而来, 即dp[i] = dp[i - difference] + 1
        特别的, 由于nums[i]的范围在[-1e4, 1e4]之间, 因此我们需要使用哈希表代替数组作为dp表

时间复杂度: 此时只需要一次遍历, 因此时间复杂度为O(n)
 */
public class LC1218_2 {
    public int longestSubsequence(int[] nums, int difference) {
        Map<Integer, Integer> dp = new HashMap<>();
        int ret = 1;
        for(int x : nums){
            int prev = x - difference, curLen = 1;
            if(dp.containsKey(prev)) curLen = Math.max(curLen, dp.get(prev) + 1);
            dp.put(x, curLen);
            ret = Math.max(ret, curLen);
        }
        return ret;
    }
}
