package problemList.slidingWindow.solution;

public class LC1004 {
    public int longestOnes(int[] nums, int k) {
        int n = nums.length, ret = 0, cnt = 0, left = 0;
        for(int i = 0;i < n;i++){
            cnt += (1 - nums[i]);
            while(cnt > k){
                cnt -= (1 - nums[left]);
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
