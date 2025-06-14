package problemList.bisect.solution;

/**
划分成若干个子数组, 不是子集合, check不要想的太复杂
 */
public class LC410 {
    public int splitArray(int[] nums, int k) {
        int n = nums.length, sum = 0, max = 0;
        for(int x : nums) {
            sum += x;
            max = Math.max(max, x);
        }
        int left = max, right = sum;
        while (left < right) {
            int mid = (left + right) >> 1;
            if(check(mid, nums, k)) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    private boolean check(int mid, int[] nums, int k){
        int cnt = 1, sum = 0;
        for(int x : nums){
            if(sum + x > mid) {
                cnt++;
                sum = x;
            }else sum += x;
        }
        return cnt <= k;
    }
}
