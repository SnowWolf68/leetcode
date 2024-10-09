package problemList.SegmentTree.solution;

import java.util.Arrays;

/**
这题和 往花瓶中插花 那题很像, 那题在 notes/SegmentTree/SegmentTreeBinartSearch.java 这里我有写过
在这题中, 将所有的任务按照运行区间的右端点排序之后, 相邻的两个区间之间的重叠部分只能是前一个区间的一个后缀
因此可以得出如下的贪心策略: 从前往后安排任务的运行时间点, 对于某个任务task, 我们在安排运行时间点时, 应该优先安排在这个任务的运行区间的后面
                        这样才能让这个任务下一个的任务, 能够尽可能的利用前一个任务已经安排过的时间点
                        这样才能最大限度地降低总的电脑运行时间
可以用线段树实现上述过程, 线段树应该具有 区间重置 区间查询累加和 功能, 直接掏出 SegmentTreeLazyResetQuerySum 的板子

对于每个任务: 
    由于题目能够保证这个任务区间的时间一定能够满足这个任务的要求
    因此我们可以先查任务区间中1的数量, 得到这个任务的区间中已经分配运行的时间点的数量
    如果已经分配的运行时间点数量足够当前任务运行, 那么直接跳过, 继续安排下一个任务
    如果不够, 用已经分配的时间点 - 要求的时间点 得出还需要的时间点数量, 假设为neededTime
    同时假设当前这个任务的任务区间为[taskL, taskR]
    接下来我们需要从taskR开始, 向左边查询, 查询第1个0的下标endIdx, 以及第neededTime个0的下标startIdx
    这个查找过程就是线段树二分, 可以看我写过的 notes/SegmentTree/SegmentTreeBinartSearch.java 这里就直接给出代码, 不再解释
    接下来只需要segTree.rangeReset(1, 1, n, startIdx, endIdx, 1); 即可

return segTree.query(1, 1, n, 1, n);
 */
public class LC2589_2_SegmentTree {
    public int findMinimumTime(int[][] tasks) {
        Arrays.sort(tasks, (o1, o2) -> o1[1] - o2[1]);
        int n = tasks[tasks.length - 1][1] + 1;
        SegmentTreeLazyResetQuerySum segTree = new SegmentTreeLazyResetQuerySum(n);
        for(int[] task : tasks){
            int taskL = task[0], taskR = task[1], duration = task[2];
            int curSum = segTree.query(1, 1, n, taskL, taskR);
            if(duration > curSum){
                int neededTime = duration - curSum;
                int startIdx = findKthZero(segTree, n, taskR, neededTime), endIdx = findKthZero(segTree, n, taskR, 1);
                segTree.rangeReset(1, 1, n, startIdx, endIdx, 1);
            }
        }
        return segTree.query(1, 1, n, 1, n);
    }

    /**
        这个二分看起来是一个查询某一个点的二分, 其实是一个查询区间端点的二分
        具体来说, 应该是一个查询右区间左端点的二分, 即查询满足 0的数量 >= k的右区间的左端点
     */
    private int findKthZero(SegmentTreeLazyResetQuerySum segTree, int n, int rightBound, int k){
        int l = 1, r = rightBound;
        while(l < r){
            int mid = (l + r) >> 1;
            int rightZeroCount = rightBound - mid - segTree.query(1, 1, n, mid + 1, rightBound);
            if(rightZeroCount >= k) l = mid + 1;
            else r = mid;
        }
        return l;      // 理论上不会走到这里, 在二分过程中一定会返回
    }
}

/**
线段树支持 范围重置 范围查询
维护累加和
 */
class SegmentTreeLazyResetQuerySum {
    private int[] sum;
    private int[] todo;
    private boolean[] valid;    // valid[i] 用来表示 todo[i]是否有效

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyResetQuerySum(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
        this.todo = new int[len];
        this.valid = new boolean[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
     */
    public void build(int o, int l, int r, int[] nums){
        if(l == r){
            sum[o] = nums[l];
            return;
        }
        // 划分区间, 继续递归左右子区间
        int mid = (l + r) >> 1;
        build(2 * o, l, mid, nums);
        build(2 * o + 1, mid + 1, r, nums);
        up(o);
    }

    /**
        当前节点为o, 使用当前节点左右子节点的信息更新o节点的信息
        之所以抽取这个方法, 是因为如果线段树维护的是其他信息, 那么这里的up方法也要相应改动
        并且有多个地方要用到这个up方法
     */
    private void up(int o){
        sum[o] = sum[2 * o] + sum[2 * o + 1];
    }

    /**
        o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        L, R, resetVal: 要重置的区间范围, 以及区间要重置的值
     */
    public void rangeReset(int o, int l, int r, int L, int R, int resetVal){
        if(L <= l && R >= r){
            // 当前区间[l, r]被要更新的区间[L, R]完全包含在里面
            // 此时只需要更新当前区间的lazy tag即可, 不需要继续向下递归更新
            do_(o, l, r, resetVal);
            return;
        }
        // 当前区间[l, r]不完全被[L, R]包含在里面, 此时需要将当前节点的lazy tag传递给左右子树
        spread(o, l, r);
        // 继续递归更新左右子区间
        int mid = (l + r) >> 1;
        if(L <= mid) rangeReset(2 * o, l, mid, L, R, resetVal);
        if(mid < R) rangeReset(2 * o + 1, mid + 1, r, L, R, resetVal);
        // 最后使用左右子区间更新当前区间的元素和
        up(o);
    }

    /**
        接受父节点传下来的lazy tag, 更新当前节点的sum[o]以及当前节点的lazy tag
     */
    private void do_(int o, int l, int r, int resetVal){
        sum[o] = resetVal * (r - l + 1);
        todo[o] = resetVal;
        valid[o] = true;    // 标记todo[o]有效
    }

    /**
        将父节点o上的lazy tag传递给(下发给)其左右子节点
     */
    private void spread(int o, int l, int r){
        if(valid[o]){   // valid[o] == true 表示 todo[o] 有效
            // 如果当前节点的lazy tag有东西要向下传递
            int mid = (l + r) >> 1;
            do_(2 * o, l, mid, todo[o]);
            do_(2 * o + 1, mid + 1, r, todo[o]);
            valid[o] = false;   // 标记todo[o]无效
        }
    }

    /**
        o, l, r: 当前节点, 当前区间范围, 调用入口: o, l, r = 1, 1, n
        L, R: 要查询的区间范围[L, R]
        单次query操作的时间复杂度也是O(logn)级别的
        因为query操作也可以归结为两条从根到叶子的路径(最长的情况下), 每条路径长度的数量级是O(logn)级别的
     */
    public int query(int o, int l, int r, int L, int R){
        if(L <= l && R >= r){
            // 如果当前区间[l, r]完全包含在要查询的区间[L, R]当中
            // 此时无需继续递归查询, 直接返回结果即可
            return sum[o];
        }
        // 需要递归向下查询
        // 首先将当前节点的lazy tag传递下去
        spread(o, l, r);
        int mid = (l + r) >> 1;
        int sum = 0;
        if(L <= mid) sum += query(2 * o, l, mid, L, R);
        if(mid < R) sum += query(2 * o + 1, mid + 1, r, L, R);
        return sum;
    }
}
