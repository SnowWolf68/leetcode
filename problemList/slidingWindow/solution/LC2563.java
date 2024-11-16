package problemList.slidingWindow.solution;

import java.util.Arrays;

/**
是个考察三指针的好题, 虽然难度分不高, 但是题目很值得思考

对于题目中给的这个式子 lower <= nums[i] + nums[j] <= upper , 不难想到我们可以枚举i, 并且计算此时位于 [lower - nums[i], upper - nums[i]] 区间的nums[j]的个数

这里我们可以首先对nums排个序, 然后观察lower - nums[i]和upper - nums[i]这两个区间端点是如何移动的
假设这里我们从小到大枚举i, 那么不难看出, 由于nums已经是有序的了, 那么显然区间的两个端点 lower - nums[i]和upper - nums[i]都是随着i的增大而减小的
那么假设我们使用left, right表示这两个端点, 那么显然left和right都是一直往左移动 (一直减小) 这显然具有单调性, 因此我们可以使用三指针

    -- 为什么不是滑窗而是三指针? 很显然, 这里区间的两个端点都需要单独使用一个指针来维护, 因此需要left, right, i三个指针才可以

通过之前的分析我们也可以知道, left, right两个指针都是从大往小移动的, 因此这里left, right的初始值都应该是 n - 1 , 其中 n = nums.length
left, right两个指针的移动过程: 
    1. left: 如果left还可以往左移, 或者说left--之后还满足要求, 那么此时就让left--
        使用代码表达, 就是: if(left - 1 >= 0 && nums[left - 1] >= lower - nums[i]) left--;
    2. right: 如果right此时不符合要求, 那么就让right--
        if(right >= left && nums[right] > upper - nums[i]) right--;

如何统计数量: 
    这里的统计也需要注意, 由于枚举的j必须要满足 i < j, 因此这里我们需要根据 [left, right] 区间和 i 的关系分类讨论
        1. 如果 i < left, 即 [left, right] 区间位于 i 的右边, 那么此时位于 [left, right] 区间中的所有j都是满足要求的, 此时 ret += right - left + 1
        2. 如果i位于[left, right]区间之内, 那么此时只有 [i + 1, right] 区间的j是符合要求的, 因此此时 ret += right - i
        3. 如果right <= i, 那么此时没有符合要求的j, 并且此时由于left, right移动的单调性, 以及i的枚举顺序, 显然再往后枚举i, 也不会存在符合要求的j了, 因此此时可以直接退出循环

最后还有一点需要注意, 这里对于一开始的区间 [lower - nums[0], upper - nums[0]] 来说, 这个区间有可能和nums[]没有交集, 即 lower - nums[0] > nums[n - 1]
换句话说, 此时一个符合要求的j都没有, 对于这种情况, 我们此时不应该统计, 而是应该让i继续往右枚举, 直到 [lower - nums[i], upper - nums[i]] 这个区间和 nums[] 存在交集为止
 */
public class LC2563 {
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        int n = nums.length, right = n - 1, left = n - 1;
        long ret = 0;
        for(int i = 0;i < n;i++){
            if(nums[left] < lower - nums[i]) continue;
            while(left - 1 >= 0 && nums[left - 1] >= lower - nums[i]) left--;
            while(right >= left && nums[right] > upper - nums[i]) right--;
            if(i < left) ret += right - left + 1;
            else if(left <= i && i < right) ret += right - i;
            else break;
        }
        return ret;
    }
}
