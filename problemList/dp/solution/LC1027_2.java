package problemList.dp.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
第一种dp的状态定义, 是直接确定了等差序列的最后两个元素, 那么还有一种方式也可以唯一确定一个等差序列, 就是确定最后一个元素以及公差
    dp[i][j] 表示以下标i的元素结尾, 并且当前公差为j, 此时最长等差序列的长度
    这里虽然nums[i]的范围是[0, 500], 那么公差的范围就是[-500, 500], 这个范围虽然比较小, 但是为了通用性, 这里还是将第二个维度使用哈希表来代替数组

其中处理重复元素的方式和第一种写法一样

但是这里由于d的值域很大, 在[-max, max]之间, 因此其实常数比较大, 实测下来需要跑1000ms-1200ms, 有一次评测机抖一抖甚至会T
因此还需要优化
 */
public class LC1027_2 {
    public int longestArithSeqLength(int[] nums) {
        int n = nums.length, max = Arrays.stream(nums).max().getAsInt();
        Map<Integer, Integer>[] dp = new Map[n];
        for(int i = 0;i < n;i++) dp[i] = new HashMap<>();
        Arrays.setAll(dp, idx -> new HashMap<>());
        Map<Integer, Integer> map = new HashMap<>();
        int ret = 1;
        for(int i = 0;i < n;i++){
            for(int d = -max;d <= max;d++){
                dp[i].put(d, 1);
                if(map.containsKey(nums[i] - d)){
                    dp[i].put(d, Math.max(dp[i].get(d), dp[map.get(nums[i] - d)].get(d) + 1));
                }
                ret = Math.max(ret, dp[i].get(d));
            }
            map.put(nums[i], i);
        }
        return ret;
    }
}
