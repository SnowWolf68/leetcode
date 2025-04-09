package problemList.slidingWindow.solution;

/*
 * 另外一种写法, 仅供参考
 */
public class LC1658_2 {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;
        int sum = 0;
        for(int num :nums) sum += num;
        int target = sum - x, curSum = 0;
        if(target < 0) return -1;
        int left = 0, maxLen = 0;
        boolean flag = false;
        for(int i = 0;i < n;i++){
            curSum += nums[i];
            while(curSum > target) {
                curSum -= nums[left];
                left++;
            }
            if(curSum == target){
                maxLen = Math.max(maxLen, i - left + 1);
                flag = true;
            }
        }
        return !flag ? -1 : n - maxLen;
    }
}
