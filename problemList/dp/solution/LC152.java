package problemList.dp.solution;

/**
对于nums[i]
    1. 如果nums[i] > 0, 那么此时以下标i结尾的乘积最大子数组 = max(以下标i - 1结尾的乘积 为正 的最大的子数组 * nums[i], nums[i])
    2. 如果nums[i] < 0, 那么此时以下标i结尾的乘积最大子数组 = max(以下标i - 1结尾的乘积 为负 的最大的子数组 * nums[i], nums[i])
因此我们需要两个dp数组, 分别记录以下标i结尾的 乘积为正/负 最大子数组的乘积
对于状态转移方程, 具体代码实现中, 为了避免对nums[i]的分类讨论, 可以每次都同时使用上述两种方式更新两个dp数组, 这样可以保证一定能更新到正确的值

初始化: 这里对于两个dp数组的第一个元素, 可以直接初始化为nums[0], 而不分类讨论nums[0]的正负
    因为即使一开始初始值和f/g数组的要求不同, 比如g[0]一开始初始化成了一个正值, 在后续的更新中, 如果遇到了一个nums[i] < 0, 那么还是会将nums[i]赋值给g
    因此这样的初始化其实没有问题
 */
public class LC152 {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int f = nums[0], g = nums[0], max = f;
        for(int i = 1;i < n;i++){
            int preF = f;
            f = Math.max(nums[i], Math.max(f * nums[i], g * nums[i]));
            g = Math.min(nums[i], Math.min(preF * nums[i], g * nums[i]));
            max = Math.max(max, f);
        }
        return max;
    }
}
