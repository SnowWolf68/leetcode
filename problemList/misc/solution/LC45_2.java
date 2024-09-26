package problemList.misc.solution;

/**
dp的时间复杂度是O(n ^ 2), 有没有时间复杂度更低一点的做法呢
    -- 答案是可以的, 可以做到O(n)的时间复杂度

思路见灵神题解: https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/solutions/2123855/yi-zhang-tu-miao-dong-pythonjavacgo-by-e-wqry/

这里说一下我自己的理解: 
    这题的dp做法可以看做是一个暴力的解法, 但是其实这个问题可以使用贪心来解决
    这题的贪心策略可能不是那么好想, 但是给出贪心策略之后, 其实还是很好理解的
    贪心策略: 在可以选的桥中, 选择右端点最大的桥, 因为它可以让你跳的更远

具体实现: 贪心策略的实现方法也有一些技巧
首先需要使用i遍历所有位置, 在遍历的同时, 维护若干个变量
    1. 维护nxtR, 表明在[0, i]区间的所有节点中, 能够建造的桥的最右端点
    2. 维护curR, 表明当前已经建造的桥的最右端点
    3. 维护cnt, 即当前建造的桥的个数
在遍历的过程中, 只要i != curR, 说明还没走到当前的桥的最右端, 目前还不需要建桥
如果i == curR, 那么说明接下来需要建桥, 此时要建的这个桥, 根据贪心策略, 应该是[0, i]区间内能够建的桥的最长的那个, 即右端点是nxtR
    因此更新curR = nxtR, 同时建的桥的数目cnt++  
    如果更新之前curR == nxtR, 说明此时无法建右端点更远的桥, 因此此时无解
 */
public class LC45_2 {
    public int jump(int[] nums) {
        int n = nums.length, nxtR = 0, curR = 0, cnt = 0;
        // 一开始肯定要在0处建一个桥
    }
}
