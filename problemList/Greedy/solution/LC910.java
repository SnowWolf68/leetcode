package problemList.Greedy.solution;

import java.util.Arrays;

/**
贪心
首先对数组升序排序
1. 如果数组所有元素都 + k (-k), 那么 max(nums) - min(nums) 不变
2. 如果让数组中大的数变大, 小的数变小, 那么 max(nums) - min(nums) 变大

因此只能让数组中 大的数变小, 小的数变大

接下来证明: 要变小的数 和 要变大的数 一定是nums中连续的两部分
假设此时nums已经升序排列
nums[0], nums[1], nums[2], nums[3], nums[4], nums[5]
  +k       +k       -k        +k      -k       -k
显然, 对于不连续的这部分 nums[2], nums[3] 来说, 显然还是让 大的(nums[3])变大, 而小的(nums[2])变小
                       -k        +k  
因此如果将+k, -k交换的话, 会更符合 让大的变小, 小的变大 的要求
因此变小的这部分和变大的这部分一定是数组中连续的两部分

nums[0] nums[1] nums[2] ... nums[i]  nums[i + 1] ... nums[n - 1]
  +k      +k      +k         +k         -k               -k
我们只需要找到最优的划分点i, 使得nums[0, i]区间 +k, nums[i + 1, n - 1]区间 -k

枚举i, 对于每一个i, 此时修改后的 max(nums) = max(nums[n - 1] - k, nums[i] + k)
            类似的, 修改后的   min(nums) = min(nums[0] + k, nums[i + 1] - k)
枚举i的过程中更新ret即可
 */
public class LC910 {
    public int smallestRangeII(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, ret = Integer.MAX_VALUE;
        for(int i = 0;i < n;i++){
            int max = Math.max(nums[n - 1] - k, nums[i] + k), min = Math.min(nums[0] + k, i + 1 < n ? nums[i + 1] - k : Integer.MAX_VALUE);
            ret = Math.min(ret, max - min);
        }
        return ret;
    }
}
