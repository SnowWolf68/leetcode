package problemList.slidingWindow.solution;

import java.util.Arrays;

/**
第一种做法其实并不是很好, 因为用到了TreeMap, 其底层实现基于红黑树, 其每次增删的时间复杂度可以看作是 O(logn) 级别, 因此性能并不是很好
这里其实还有更好的方法

这里我们可以换一个思路, 假设 x 是出现次数最大的元素, 那么我们从 枚举 x 的角度出发计算

首先对nums排序
这里分为两种情况:
    1. x 位于 nums[] 中:
        那么此时只需要计算 nums[] 中位于 [x - k, x + k] 区间的元素有多少, 这个计算过程可以使用 三指针 完成
        需要注意的是, 这里我们也需要考虑originCnt[x]的影响, 这里灵神提供了一种很巧妙的处理方式
        当遍历到一个nums[i]时, 首先我们找到连续的nums[i]中, 最右侧的那一个nums[i], 那么在这个过程中, 我们就可以计算得到nums[i]的出现次数cnt
        即originCnt[nums[i]], 然后我们再进行移动left, right, 并进行计算即可
        -- 注: 灵神的这种cnt的方式很巧妙, 看看代码好好理解一下

    2. x 不位于 nums[] 中
        此时我们可以枚举 变成x的元素的区间的右端点 (枚举右端点而不是左端点, 原因是我们想用滑窗进行优化)
        假设右端点为 nums[i], 那么此时我们只需要计算 [nums[i] - 2 * k, nums[i]] 区间的元素个数, 即是答案
        (注: 由于这里要变成的元素x不在nums[]中, 因此我们不需要考虑orginCnt[x]的影响)
        有人可能要问了, 如果此时恰好 nums[i] - k 存在于nums[]中, 怎么办? 
        换句话说, 如果此时有一个应该属于第一种情况的区间, 我们按照第二种情况计算了, 此时会有影响吗?
         -- 不会
        因为此时如果有一个[nums[i] - 2 * k, nums[i]]的区间属于第一种情况, 但是我们按照第二种情况计算了
        那么此时由于我们没有考虑 originCnt[nums[i] - k]的影响, 因此计算出来的答案一定比正确答案要小, 因此不会影响最终答案的更新
    
由于之前并没有过多接触过 三指针 , 因此这里多说几句
    可以把三指针看成是 带有两个边界的滑窗
    即: 对于滑窗来说, 我们是 枚举右端点, 并且更新左端点
    那么对于三指针, 我们可以看成是 枚举 中间元素, 并且同时更新左右端点

    或者也可以这样理解: 把三指针看成是 两个滑窗的结合: 第一个滑窗是枚举右边界的滑窗, 同时更新left, 第二个滑窗是枚举左边界的滑窗, 同时更新right
    既然三指针可以看成是两个滑窗的结合, 那么判断一个问题是否可以用三指针解决, 其判断规则和判断滑窗也类似
    比如 越短越合法, 越长越合法, 恰好型滑窗, 滑窗计数 等等类似的问题, 如果题目中说了有两个边界, 那么都可以考虑是否能够用三指针解决

 */
public class LC3347_2 {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int n = nums.length, ret = 0;
        Arrays.sort(nums);
        // 第一种情况
        int left = 0, right = 0, cnt = 0;   // cnt表示当前 nums[i] 在nums中的出现次数
        for(int i = 0;i < n;i++){
            cnt++;
            // 循环直到连续相同段的末尾，这样可以统计出 x 的出现次数
            while(i + 1 < n && nums[i] == nums[i + 1]){
                i++;
                cnt++;
                continue;
            }
            while(left <= i && nums[i] - nums[left] > k) left++;
            while(right + 1 < n && nums[right + 1] - nums[i] <= k) right++;
            ret = Math.max(ret, Math.min(numOperations, right - left + 1 - cnt) + cnt);
            cnt = 0;
        }
        // 第二种情况
        left = 0;
        for(int i = 0;i < n;i++){
            while(left <= i && nums[i] - nums[left] > 2 * k) left++;
            ret = Math.max(ret, Math.min(numOperations, i - left + 1));
        }
        return ret;
    }
}
