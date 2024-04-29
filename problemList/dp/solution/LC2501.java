package problemList.dp.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
dp[i] 表示以下标i结尾的最长方波的长度
    dp[i]: 对于题目中方波的定义而言, 如果确定了其中的某一个元素, 那么其前一个元素也可以唯一确定
        因此我们可以确定当前元素nums[i]的前一个元素为Math.sqrt(nums[i]), 我们只需要判断sqrt(nums[i])是否存在
        如果存在, 假设其下标为j, 那么有dp[i] = dp[j] + 1;
初始化: 不需要初始化
return max(dp[i]);

这题中虽然nums可能会有重复元素, 但是由于这里我们需要对nums排序, 并且题目要求nums[i] >= 2, 因此sqrt(nums[i])一定不等于nums[i]
所以对于nums中的重复元素来说, map中保存其中任意一个下标均可
 */
public class LC2501 {
    public int longestSquareStreak(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0;i < n;i++) map.put(nums[i], i);
        int[] dp = new int[n];
        int ret = 1;
        for(int i = 0;i < n;i++){
            dp[i] = 1;
            if(Math.sqrt(nums[i]) == (int)Math.sqrt(nums[i])){
                int j = map.getOrDefault((int)Math.sqrt(nums[i]), -1);
                if(j != -1) dp[i] = dp[j] + 1;
            }
            ret = Math.max(ret, dp[i]);
        }
        return ret >= 2 ? ret : -1;
    }
}
