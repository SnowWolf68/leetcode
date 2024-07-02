package solution;

/**
DP优化主要就是两个方面: 
    1. 状态个数优化: 通过优化状态表示来减少状态个数
    2. 单个状态计算时间复杂度优化: 
        1. 通过优化单个状态计算的方式, 来优化单个状态的计算复杂度, 比如一些数据结构优化DP
        2. 也可以通过改变单个状态计算的逻辑, 从而降低复杂度, 比如接下来要介绍的将 "枚举2个" 变为 "枚举1个"
这里我们优化 状态个数
通过之前的转化, 我们已经将问题转化成了: 一共有 2 * numSlots 个桶, 每个桶有 放或不放 两种选择
在之前的状压DP中, 我们都是这样理解 "排列" : 保持桶的位置不变, 将所有的元素进行排列
这样思考的原因是, 在不进行转化之前, 一个桶内可以放0个, 1个或2个元素, 因此我们只能将 元素 进行排列, 并且和桶进行匹配
但是我们转化成 2 * numSlots 个桶之后, 就可以思考一下其他的方式
因为这里对于每个桶, 只有 放或不放 两种选择, 因此我们可以将 2 * numSlots 个桶中的前 n (n = nums.length) 个桶进行排列, 同时保持元素的位置不动, 让桶的排列来匹配元素
这样的好处是: 因为我们只匹配前 n 个桶, 即只给前 n 个桶中放入 1个 元素, 后面这 2 * numSlots - n 个桶中都不放元素
因此只要桶的某一个排列确定了, 那么放入的元素个数也就确定了, 我们只需要枚举最后一个元素放入的是前 n 个桶中的哪个桶即可, 因此只需要O(2 ^ (2 * numSlots) * n)的时间复杂度

这里的返回值需要说一下, 由于在状态转移中, 对于 bitCount(state) > n 的这些桶的排列来说, 此时我们是不进行计算的, 因此最后的返回值就需要对所有dp[state]取一个max
 */
public class LC2172_2 {
    public int maximumANDSum(int[] nums, int numSlots) {
        int n = nums.length, mask = 1 << (2 * numSlots), INF = 0x3f3f3f3f;
        int[] dp = new int[mask];
        int ret = -INF;
        for(int state = 1;state < mask;state++){
            dp[state] = -INF;
            int bitCount = Integer.bitCount(state);
            // 如果当前state集合中篮子的数量超过了 n , 那么说明此时放入元素的桶的数量已经超过了 n , 根据上面的分析, 后面的这 2 * numSlots - n 个桶中不需要放入元素, 因此不需要计算对应的dp[state]值
            if(bitCount > n) continue;
            for(int i = 0;i < 2 * numSlots;i++){
                if(((state >> i) & 1) == 0) continue;
                // 这里i指的是下标, 因此映射关系为 (i + 2) / 2
                dp[state] = Math.max(dp[state], dp[state & (~(1 << i))] + (((i + 2) / 2) & nums[bitCount - 1]));
                ret = Math.max(ret, dp[state]);
            }
        }
        return ret;
    }
}
