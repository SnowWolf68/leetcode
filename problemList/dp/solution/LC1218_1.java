package problemList.dp.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
基于下标的写法
由于difference确定, 所以对于nums[i], 可以确定定差子序列中的前一个元素值为nums[i] - difference, 因此简单dp即可
注意nums中可能会有重复元素, 因此map的val需要是一个List
时间复杂度: O(n ^ 2)
 */
public class LC1218_1 {
    public int longestSubsequence(int[] nums, int difference) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i = 0;i < n;i++) {
            List<Integer> list = map.getOrDefault(nums[i], new ArrayList<>());
            list.add(i);
            map.put(nums[i], list);
        }
        int[] dp = new int[n];
        int ret = 1;
        for(int i = 0;i < n;i++){
            dp[i] = 1;
            List<Integer> list = map.getOrDefault(nums[i] - difference, new ArrayList<>());
            for(int idx : list){
                if(idx < i){
                    dp[i] = Math.max(dp[i], dp[idx] + 1);
                }
            }
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
}
