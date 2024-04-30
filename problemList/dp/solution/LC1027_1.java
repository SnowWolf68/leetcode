package problemList.dp.solution;

import java.util.HashMap;
import java.util.Map;

/**
关键的两点: 
    1. 这里由于等差数列的差不确定, 因此dp表需要添加一个维度, 即使用i, j两个元素才能确定一个等差数列
    2. 还是重复元素的处理, 对于重复出现的两个元素来说, 前面那个元素所能组成的等差序列, 后面那个元素也能组成, 但是后面那个元素能够组成的等差序列, 前面那个元素不一定能组成
        因此我们得到: 当存在重复元素时, 只需要考虑后面那一个即可
        自然的一种想法, 我们只需要首先在dp之前, 遍历一次nums数组, 并且将<nums[i], i>添加到map中即可
        由于我们是从前往后遍历, 因此就能够保证最后map中存储的下标都是这个元素最后一次出现的下标
        但是这样做其实是有问题的, 虽然map中存储的下标, 都是最后一次出现的下标, 但是这也会导致一个问题, 假设有一个元素重复出现了两次, 其两次出现的下标分别为m, n, 其中m < n
        那么假设我们当前遍历到i, i满足m < i < n, 那么此时nums[m]这个元素在map中存储的下标就是n, 但是此时如果i能和m组成一个更长的等差序列, 但是map中存储的下标是n, 那么显然m的这个信息就被丢掉了
        因此这样做显然不对

        为了解决这个问题, 我们可以让map的信息不再是预先处理好, 而是在进行dp的时候再每次更新
        即遍历计算dp[i][j]的时候, 每次计算完当前的dp[i][j], 就将<nums[i], i>存入map中, 这样即能保证每次存入map中的下标都是这个元素"到目前为止"最后一次出现的下标
        同时还能保证不会出现上面存在的问题
 */
public class LC1027_1 {
    public int longestArithSeqLength(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int[][] dp = new int[n][n];
        int ret = 1;
        for(int i = 0;i < n;i++){
            for(int j = i + 1;j < n;j++){
                dp[i][j] = 2;
                if(map.containsKey(2 * nums[i] - nums[j])){
                    dp[i][j] = Math.max(dp[i][j], dp[map.get(2 * nums[i] - nums[j])][i] + 1);
                }
                ret = Math.max(ret, dp[i][j]);
            }
            map.put(nums[i], i);
        }
        return ret;
    }
}
