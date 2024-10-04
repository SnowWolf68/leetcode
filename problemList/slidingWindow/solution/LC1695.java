package problemList.slidingWindow.solution;

public class LC1695 {
    public int maximumUniqueSubarray(int[] nums) {
        int n = nums.length, left = 0, maxSum = 0, sum = 0;
        int[] cnt = new int[(int)1e5 + 1];
        for(int i = 0;i < n;i++){
            cnt[nums[i]]++;
            sum += nums[i];
            while(cnt[nums[i]] > 1){
                cnt[nums[left]]--;
                sum -= nums[left];
                left++;
            }
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
}
