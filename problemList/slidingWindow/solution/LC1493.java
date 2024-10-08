package problemList.slidingWindow.solution;

/**
题意: 至多包含一个0的最长子数组长度
 */
public class LC1493 {
    public int longestSubarray(int[] nums) {
        int n = nums.length, ret = 0, zeroCnt = 0, left = 0;
        for(int i = 0;i < n;i++){
            zeroCnt += (1 - nums[i]);
            while(zeroCnt > 1){
                zeroCnt -= (1 - nums[left]);
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret - 1;
    }
}
