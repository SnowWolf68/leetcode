package problemList.slidingWindow.solution;

import java.util.Arrays;

/**
求的最长长度和元素的顺序无关, 因此可以先进行排序

对数组排序, 使得子序列 -> 子数组, 进而使用滑动窗口解决
滑动窗口: 求 元素最大值和最小值之差不超过2 * k的最长长度

需要注意的是, 排序之后, 只要区间确定了, 那么区间左端点就是min, 右端点就是max (如果是升序排序的话)
 */
public class LC2779 {
    public int maximumBeauty(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, left = 0, ret = 1;
        for(int i = 1;i < n;i++){
            while(nums[i] - nums[left] > 2 * k) left++;
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
