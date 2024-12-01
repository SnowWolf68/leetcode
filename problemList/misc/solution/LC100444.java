package problemList.misc.solution;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.cn/problems/identify-the-largest-outlier-in-an-array/description/

/**
以下思路来自灵神的周赛讲解
假设数组中特殊数字的和为s, 异常值为x, 数组所有元素的和为sum, 那么我们可以得到这样的等式: s + s + x = sum
    其中, 第一个s表示所有特殊数字的和, 第二个s表示剩下的两个元素中 表示特殊数字的和 的那个元素
由于这里sum显然是给定的, 那么我们可以把这题看成: 从数组中选出两个元素 s 和 x, 满足 2 * s + x = sum  (target)
如果我们将sum看作是target, 那么这题就变成了 "两数之和" 的变形

ps: 这个两数之和的变形我都写不出来...
 */
public class LC100444 {
    public int getLargestOutlier(int[] nums) {
        int sum = 0;
        int ret = Integer.MIN_VALUE;
        Map<Integer, Integer> map = new HashMap<>();
        for(int x : nums){
            sum += x;
            map.merge(x, 1, Integer::sum);
        }
        for(int s : nums){
            int x = sum - 2 * s;
            if((s != x && map.containsKey(x)) || map.getOrDefault(x, 0) > 1) ret = Math.max(ret, x);
        }
        return ret;
    }
}
