package problemList.slidingWindow.solution;

/**
可以看成是两个 恰好型滑窗 的组合, 组合起来就是 三指针
TODO: 这题还有点问题, 还没过
 */
public class LC2367 {
    public int arithmeticTriplets(int[] nums, int diff) {
        int n = nums.length, ret = 0, left = 0, right = 0;
        for(int i = 0;i < n;i++){
            while(left <= i && nums[i] - nums[left] > diff) left++;
            while(right + 1 < n && nums[right + 1] - nums[i] < diff) right++;
            if(nums[i] - nums[left] == diff && nums[right] - nums[i] == diff) ret++;
        }
        return ret;
    }
}
