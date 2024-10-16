package problemList.slidingWindow.solution;

/**
参考灵神题解: https://leetcode.cn/problems/maximize-win-from-two-segments/solutions/2093246/tong-xiang-shuang-zhi-zhen-ji-lu-di-yi-t-5hlh/

这题其实和 LC2271 https://leetcode.cn/problems/maximum-white-tiles-covered-by-a-carpet/
         LC2106 https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/description/
         都很像

这题的滑窗也是非常巧妙
按照常规想法, 一般来说会把每个下标位置有多少个奖品, 统计在单独的一个数组中, 即pos[i]表示i这个下标有多少个奖品
但是对于这题的数据范围来说, 下标的数量达到了1e9的级别, 因此无法将下标作为数组的下标来进行统计
其实, 由于这题题目中给数据的方式就是prizePositions[i]表示第i个奖品的下标位置, 也就暗示你要利用原有的这个prizePosition数组进行滑窗

虽然线段的长度固定, 看起来像是固定长度的滑窗, 但是其实由于这里我们是在prizePositions这个数组上滑窗, 因此在这种情况下, 这是一个窗口长度可变的滑窗

和普通滑窗不同的是, 这里有两条线段, 而滑窗只能计算其中一个窗口的相关信息, 那么这里如何处理?
注意到, 当我们选了右边的这条线段之后, 那么剩下的问题可以看成, 从选中的这条线段左边, 再选一条线段, 此时能够得到的最多的奖品数量
换句话说, 这两条线段的选择中, 存在 重复子问题
因此我们可以使用类似dp的思想, 在滑窗计算右边这条线段时, 记录下此时这条线段的信息, 那么在计算左边这条线段时, 就可以直接使用右边这条线段的信息来得到左边这条线段的信息

具体来说: 
    首先分析右边这条线段的滑窗计算过程
    
    首先需要明确, 我们是在prizePositions上进行的滑窗 或者也可以看作是在奖品下标上进行的滑窗, 而不是在奖品放置的数轴上的下标进行的滑窗
    由于线段的长度有限, 超过线段长度的范围不能被线段覆盖, 因此这其实是一个 越短越合法 的滑窗
    由于prizePositions[i]表示的是第i个奖品的下标位置, 因此这里判断窗口是否合法也很简单, 只需要比较 prizePositions[right] - prizePositions[left] 和 线段长度k 的关系即可
    并且此时我们计算这个窗口中包括的奖品数量也很简单, 直接 right - left + 1 即可得到窗口中的奖品数量

    因此我们可以写出如下的伪代码

    for(int i = 0;i < prizePositions.length;i++){       // right++
        while(prizePositions[i] - prizePositions[left] > k){
            left++;
        }
        更新当前窗口中奖品数的最大值
    }

由于左边的线段和右边的线段的过程是类似的, 因此我们可以在计算右边的线段的同时, 将计算的信息保存下来, 那么在计算左边的线段时, 就可以直接拿之前右边线段的信息来用
具体来说
    使用一个数组, dp[i]表示考虑[0, i]下标范围内的奖品, 使用一条线段, 此时能够获得的最多奖品数量
    那么有如下的递推式: 根据当前线段是否包含第i个奖品, 可以分为两种情况
        1. 当前线段包含第i个奖品: 计算过程可以通过两条线段中右边那条线段的滑窗过程得到
        2. 当前线段不包含第i个奖品: 问题转化为 考虑[0, i - 1]区间的奖品, 使用一条线段, 此时能够获得的最多的奖品数量
初始化: 由于i - 1可能会发生越界, 因此这里添加一个辅助节点, dp[0]表示不考虑任何奖品, 那么显然dp[0] = 0

接下来看一下在滑窗的过程中如何计算dp
    显然需要在 更新当前窗口中奖品数的最大值 的位置, 同时更新此时的dp值, 伪代码中的位置如下

    for(int i = 0;i < prizePositions.length;i++){       // right++
        while(prizePositions[i] - prizePositions[left] > k){
            left++;
        }
        更新当前窗口中奖品数的最大值
        更新dp[i + 1]
    }

    需要注意的是, 由于这里我们给dp表添加了一个辅助节点, 因此对于right == i时, 需要更新的dp表下标为i + 1

那么对于两个线段来说, 此时能够获得的奖品数的最大值 = 使用dp表计算出来的左边窗口中奖品的最大值 + 使用滑窗计算出来的右边窗口中的奖品的最大值

假设当前窗口范围是[left, right], 那么此时两个窗口中能获得的奖品的最大值 ret = dp[left - 1] + i - left + 1;

 */
public class LC2555 {
    public int maximizeWin(int[] prizePositions, int k) {
        int n = prizePositions.length, left = 0, ret = 0;
        int[] dp = new int[n + 1];
        for(int i = 0;i < n;i++){
            // 注意这里是线段长度, 因此不需要 + 1
            while(left <= i && prizePositions[i] - prizePositions[left] > k){
                left++;
            }
            dp[i + 1] = Math.max(dp[i], i - left + 1);
            ret = Math.max(ret, i - left + 1 + dp[left]);
        }
        return ret;
    }
}
