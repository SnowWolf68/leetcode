package problemList.slidingWindow.solution;

import java.util.Arrays;

/*
 * 这种写法是第二次刷这题时的写法, 可以参考一下
 * 在处理cur的时候, 和第一种写法有些区别, 这也导致了后面会多需要一些判断
 */
public class LC1831_2 {
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long cur = 0;
        long[] prefix = new long[n + 1];
        for(int i = 0;i < n;i++){
            prefix[i + 1] = prefix[i] + nums[i];
        }
        int left = 0, maxLen = 0;
        for(int i = 0;i < n;i++){
            cur = (long)(i - left) * nums[i] - (prefix[i] - prefix[left]);
            while(cur > k && left < n - 1) {
                cur -= nums[i] - nums[left];
                left++;
            }
            if(cur <= k) maxLen = Math.max(maxLen, i - left + 1);
        }
        return maxLen;
    }
}
