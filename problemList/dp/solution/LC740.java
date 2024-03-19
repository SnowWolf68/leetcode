package problemList.dp.solution;

/**
获得nums[i]之后, 需要删除所有等于nums[i] + 1和nums[i] - 1的元素, 其实也就是删除在值域上和nums[i]相邻的元素
因此如果我们把nums[i]看作下标, 那么实际上这就变成了 "打家劫舍" 问题
有一点需要注意: 这题的nums中包含重复元素, 对于重复元素, 我们需要单独分析
    对于nums[i]来说, 如果其重复出现多次, 那么不难证明: 如果需要取nums[i]的话, 那么此时取所有的nums[i]是更优的
因此我们需要统计每一个nums[i]出现的次数cnt, 即cnt[nums[i]]表示nums[i]出现的次数
然后我们在cnt[]数组上进行一次打家劫舍即可, 不同的是, 这里每一次获得的点数不再是cnt, 而是nums[i] * cnt
 */
public class LC740 {
    public int deleteAndEarn(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int x : nums) max = Math.max(max, x);
        int[] cnt = new int[max + 1];
        for(int x : nums) cnt[x]++;
        int[] f = new int[max + 1], g = new int[max + 1];
        for(int i = 1;i <= max;i++){
            f[i] = g[i - 1] + cnt[i] * i;
            g[i] = Math.max(f[i - 1], g[i - 1]);
        }
        return Math.max(f[max], g[max]);
    }
}
