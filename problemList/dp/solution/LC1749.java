package problemList.dp.solution;

/**
转换成max(abs(最大子数组和), abs(最小子数组和))
这里使用空间优化, 只用两个变量滚动计算
 */
public class LC1749 {
    public int maxAbsoluteSum(int[] nums) {
        int n = nums.length;
        int dpMax = Math.max(0, nums[0]), dpMin = Math.min(0, nums[0]), ret = Math.max(0, Math.max(dpMax, -dpMin));
        for(int i = 1;i < n;i++){
            dpMax = Math.max(nums[i], dpMax + nums[i]);
            dpMin = Math.min(nums[i], dpMin + nums[i]);
            ret = Math.max(ret, Math.max(dpMax, -dpMin));
        }
        return ret;
    }
}
