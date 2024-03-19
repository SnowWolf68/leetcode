package problemList.dp.solution;

/**
根据最后一个房屋偷还是不偷进行分类讨论: 
    1. 偷: [1, n - 3]区间的打家劫舍, 返回值 + nums[n - 1]
    2. 不偷: [0, n - 2]区间的打家劫舍
    上面两者取max
 */
public class LC213 {
    public int rob(int[] nums) {
        int n = nums.length;
        if(n == 1) return nums[0];
        if(n == 2) return Math.max(nums[0], nums[1]);
        int[] f = new int[n], g = new int[n];
        f[1] = nums[1];
        for(int i = 2;i <= n - 3;i++){
            f[i] = g[i - 1] + nums[i];
            g[i] = Math.max(f[i - 1], g[i - 1]);
        }
        int ret1 = Math.max(f[n - 3], g[n - 3]) + nums[n - 1];
        f[0] = nums[0];
        for(int i = 1;i <= n - 2;i++){
            f[i] = g[i - 1] + nums[i];
            g[i] = Math.max(f[i - 1], g[i - 1]);
        }
        int ret2 = Math.max(f[n - 2], g[n - 2]);
        return Math.max(ret1, ret2);
    }
}
