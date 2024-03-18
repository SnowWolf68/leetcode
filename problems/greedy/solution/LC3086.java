package problems.greedy.solution;

import java.util.*;

/**
题意简化: 
    假设一开始灵神站在index的位置, 那么如果nums[index] == 1, 那么此时灵神可以收集一个1, 并且不消耗操作次数
    有以下两种操作: 
        1. 在nums数组原来是0的位置(这个位置不能是index), 直接产生一个1
        2. 将nums数组中相邻的1和0交换位置
    求: 将k个1移动到灵神所站的index位置(包括一开始nums[index]位置的1, 这个1的获得不消耗操作次数), 此时所需要的最少操作次数
    
分析: 
    为了分析方便, 我们重新定义两种操作: 
        操作1: 在index的相邻位置产生一个1, 并且将这个1移动到index位置 -> 消耗2次操作次数
        操作2: 将位于j位置的1移动到index位置 -> 消耗abs(j - index)次操作次数
    对于操作1来说, 要想得到一个1, 其操作次数始终都是2次
    对于操作2来说, 要想得到一个1, 对于index左右两侧的1来说, 其操作次数为1次, 对于其余情况的1, 其操作次数都 >= 2次
    
    可以这样分析: 
        如果maxChanges比较大, 那么我们首先将index左右两侧的1得到(如果有的话), 然后剩下的1都通过操作1得到, 这样显然是更优的
        如果maxChanges比较小, 那么我们首先将index左右两侧的1通过操作2得到(如果有的话), 然后再通过操作1, 得到maxChanges个1, 最后将剩下需要得到的1都通过操作2得到, 这样显然是更优的
    
    通过上面的分析, 不难发现: 
        1. 如果maxChanges比较大, 那么所需要的最少操作次数其实和index的位置是无关的 (因为我们只用到了操作1, 其可以在任意0的位置产生一个1, 并将这个1移动到index位置)
        2. 如果maxChanges比较小, 那么此时就需要用到操作2, 显然操作2和index的位置是相关的, 因此我们需要分析index选在什么位置, 能够使操作次数最少
            通过以上的分析, 我们也可以得出, 在这种情况下, 操作1执行的次数为maxChanges次, 操作2执行的次数为k - maxChanges次
    对于maxChanges比较大还是比较小的分析, 我们可以首先按照 "maxChanges比较大" 来尝试计算, 看看此时能否得到k个1, 如果可以, 那么可以直接返回操作次数, 如果不行, 那么再按照 "maxChanges比较小" 来分析计算
    因此关键的难点就是: maxChanges比较小的情况下, 如何确定index的位置, 使得得到k - maxChanges个1所需要的操作次数最少
    由于我们使用操作2所选的这k - maxChanges个1, 其显然是连续的(因为如果不连续, 那么显然操作次数会更多)
    而因为如果这连续的k - maxChanges个1的位置确定了, 那么其实操作数最小的index的位置实际上也确定了
        这其实是一个经典的 "货仓选址" 问题, 其结论就是选在 中位数 的位置, 此时距离所有货仓的距离之和最小 (特别的, 如果是偶数个元素, 那么货仓选在中间两个元素之间的任意位置(包括这两个元素)均可)
        货仓选址, 其实就是中位数贪心
        具体的证明过程, 可以自己试着取除了中位数之外的几个点看一下, 比较容易证明
    因此我们要确定index的位置, 实际上也就是确定这连续的k - maxChanges个1的位置
    
    我们可以遍历这k - maxChanges个1的所有位置, 对于每一种情况, 分别计算此时的index到所有1的距离和(也就是将1移动到index所需要的操作次数), 然后取min即可
    因此问题转化成 如何求每一种k - maxChanges情况下, 通过中位数贪心选择的index到所有的1和距离和
    这里引入一种经典的计算这种问题的方式: 
        假设有一些数: a0, a1, a2, a3, ..., a(q), ... , a(n - 3), (n - 2), a(n - 1)
        其中, q是这些数的中位数, 我们需要尽可能快的求出abs(a(q) - a0) + abs(a(q) - a1) + abs(a(q) - a2) + ... + abs(a(q) - a(n - 2)) + abs(a(q) - a(n - 1))
        方法:   (图例参见图一)
            对于q前面的这些元素, 其到a(q)的距离和为图二中左边白色阴影部分, 即q * a(q) - (a0 + a1 + a2 + ... + a(q - 1))
            对于q后面的这些元素, 其到a(q)的距离和为图二中右边红色阴影部分, 可以直接计算得到: a(q + 1) + a(q + 2) + ... + a(n - 2) + a(n - 1) - (n - q - 1) * a(q)
                (图中右边白色阴影部分没有用, 这是我画图的时候画错了)
            对于上面两个式子中的 "子数组的和" 可以使用前缀和进行计算, 这样就可以在O(1)的时间下, 求出每一种k - maxChanges情况下, 所有1到index的距离之和

    还有一个小问题, 就是如何枚举所有可能的k - maxChanges的位置
    首先由于我们需要计算每个1到index的距离, 因此我们肯定需要使用一个集合来记录所有1出现的下标
    然后枚举所有可能的k - maxChanges个1时, 我们可以枚举这些1的右端点right, 那么这些1的范围就是[0, right], 然后让这个大小为k - maxChanges的窗口不断向右滑动, 就可以枚举到所有可能的连续的k - maxChanges个1
 */
public class LC3086 {
    public long minimumMoves(int[] nums, int k, int maxChanges) {
        int n = nums.length;
        // 首先判断是不是可以只使用操作1来得到k个1
        // cnt表示最多连续出现的1的个数
        int cnt = 0, cur = 0;
        // 同时得到1的下标集合
        List<Integer> idx = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(nums[i] == 1){
                cur++;
                idx.add(i);
            }else{
                cnt = Math.max(cnt, cur);
                cur = 0;
            }
        }
        // 别忘了最后还需要再更新一次cnt
        cnt = Math.max(cnt, cur);
        // 这里的处理很细节, 好好想想
        if(cnt >= 3) cnt = 3;
        cnt = Math.min(cnt, k);
        if(cnt + maxChanges >= k) return Math.max(cnt - 1, 0) + 2 * (k - cnt);
        // 需要用到操作2
        // 首先计算idx的前缀和
        long[] s = new long[idx.size() + 1];
        for(int i = 1;i <= idx.size();i++){
            s[i] = s[i - 1] + idx.get(i - 1);
        }
        // 枚举k - maxChanges个1的位置
        int size = k - maxChanges;
        long ret = Long.MAX_VALUE;
        // 枚举连续的1的右端点(包括right)
        for(int right = size - 1;right < idx.size();right++){
            int left = right - size + 1, mid = (left + right) / 2;
            // 计算idx[mid]到[left, right]区间的每一个元素的距离和
            long curSum = (mid - left) * idx.get(mid) - (s[mid] - s[left]) + s[right + 1] - s[mid + 1] - (right - mid) * idx.get(mid);
            ret = Math.min(ret, curSum);
        }
        return ret + maxChanges * 2;
    }
}
