package problemList.slidingWIndows.solution;

/**
至少型滑动窗口板子 & 滑动窗口计数板子
 */
public class LC2962 {
    public long countSubarrays(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        for(int x : nums) max = Math.max(max, x);
        long ret = 0;
        int left = 0, n = nums.length, cnt = 0;
        for(int i = 0;i < n;i++){
            if(nums[i] == max) cnt++;
            while(cnt >= k){
                if(nums[left] == max) cnt--;
                left++;
            }
            ret += left;
        }
        return ret;
    }
}
