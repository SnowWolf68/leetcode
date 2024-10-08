package problemList.slidingWindow.solution;

import java.util.Arrays;

/**
排序 + 前缀和 + 滑窗

这题竟然也能用滑窗, 巧妙至极
 */
public class LC1838 {
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, left = 0, ret = 0;
        long curDiff = 0;
        long[] preSum = new long[n + 1];
        for(int i = 0;i < n;i++){
            preSum[i + 1] = preSum[i] + nums[i];
        }
        for(int i = 0;i < n;i++){
            curDiff = (long)(i - left + 1) * nums[i] - (preSum[i + 1] - preSum[left]);
            while(curDiff > k){
                left++;
                curDiff = (i - left + 1) * nums[i] - (preSum[i + 1] - preSum[left]);
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
