package problemList.slidingWindow.solution;

public class LC209 {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length, sum = 0, left = 0, ret = Integer.MAX_VALUE;
        for(int i = 0;i < n;i++){
            sum += nums[i];
            while(left <= i && sum >= target){
                ret = Math.min(ret, i - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return ret == Integer.MAX_VALUE ? 0 : ret;
    }
}
