package solution;

import java.util.Arrays;

/**
DP优化主要就是两个方面: 
    1. 状态个数优化: 通过优化状态表示来减少状态个数
    2. 单个状态计算时间复杂度优化: 
        1. 通过优化单个状态计算的方式, 来优化单个状态的计算复杂度, 比如一些数据结构优化DP
        2. 也可以通过改变单个状态计算的逻辑, 从而降低复杂度, 比如接下来要介绍的将 "枚举2个" 变为 "枚举1个"
这里首先优化 单个状态的计算时间复杂度

可以发现, 在计算dp[i][state]时, 需要O(n ^ 2)分别遍历最后一个篮子中所有可能放入的数, 这个复杂度还是比较高的
如果在不改变dp[i][state]的计算逻辑的前提下, 显然枚举最后的一个/两个元素是不可避免的
因此这里我们考虑能不能改变dp[i][state]的计算逻辑

题意优化: 
这里由于一个桶内可以放0个, 1个或2个元素, 因此如果我们将numSlots个桶扩展成2 * numSlots个桶, 那么就变成一个桶内只能放0个或1个元素
这样我们就只需要枚举最后一个元素即可, 单个状态的时间复杂度降为 O(n)

需要注意的是, 这里我们将 numSlots 个桶扩展成了 2 * numSlots 个桶, 那么在计算的时候我们是把第 i 个桶 和 第 i + 1 个桶看作是原来的一个桶 (i % 2 != 0) (前提是添加了一个辅助节点, 即i等于桶的下标 +1, 也可以理解为i表示第i个桶)
因此需要能够通过扩展之后的桶的编号: [0, 2 * numSlots] (0表示没有任何桶) 找到原来的桶的编号 [0, numSlots] (0同样表示没有任何桶)
不难想到这里的映射关系应该是 (i + 1) / 2 (i为拓展之后的桶号)

时间复杂度: 状态个数: 2 ^ n * numSlots * 2, *2 可以忽略, 单个状态的时间复杂度 O(n), 因此总的时间复杂度: O(2 ^ n * numSlots * n)
能过, 但是还是有点慢

这里其实对于 2 * numSlots 个桶的扩展, 有多种方式, 上面的方式中, 我们使用扩展后的第 i 个桶 和 第 i + 1 个桶 表示 扩展前的 第i个桶
也可以使用扩展后的第 i 个桶 和 第 i + numSlots 个桶 表示 扩展前的第 i 个桶
这种方式对应的映射关系为 (i - 1) % numSlots - 1  (i表示扩展后的桶下标, 添加辅助节点之后的i(i表示的桶的下标为i + 1), 并且这里numSlots是没有 *2 之前的桶数目)
这个式子也很好理解, 首先(i - 1)获取到的是桶的下标, 然后 % numSlots 得到的是处于[0, numSlots - 1]区间的下标, 同时因为添加了辅助节点, 因此需要 +1 , 即映射到[1, numSlots]区间
 */
public class LC2172_1 {
    public int maximumANDSum(int[] nums, int numSlots) {
        int n = nums.length, mask = 1 << n, INF = 0x3f3f3f3f;
        numSlots *= 2;
        int[][] dp = new int[numSlots + 1][mask];
        Arrays.fill(dp[0], -INF);
        dp[0][0] = 0;
        for(int i = 1;i <= numSlots;i++){
            for(int state = 0;state < mask;state++){
                dp[i][state] = dp[i - 1][state];
                for(int j = 0;j < n;j++){
                    if(((state >> j) & 1) == 0) continue;
                    dp[i][state] = Math.max(dp[i][state], dp[i - 1][state & (~(1 << j))] + ((i + 1) / 2 & nums[j]));
                    // 或者用下面这个也可
                    // dp[i][state] = Math.max(dp[i][state], dp[i - 1][state & (~(1 << j))] + (((i - 1) % (numSlots / 2) + 1) & nums[j]));
                }
            }
        }
        return dp[numSlots][mask - 1];
    }
}
