package notes.SegmentTree;

/**
线段树支持 范围重置 范围增加 范围查询
维护区间最大值
 */
public class SegmentTreeLazyResetAndAddQueryMax {
    private int[] max;
    private int[] todoAdd;
    private int[] todoReset;
    private boolean[] valid;    // 同样用来表示todoReset[o]是否有效

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyResetAndAddQueryMax(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.max = new int[len];
        this.todoAdd = new int[len];
        this.todoReset = new int[len];
        this.valid = new boolean[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
        线段树初始化 (建树) 的时间复杂度是O(n), 
        因为递归会把sum数组的所有元素都访问一遍, 而sum数组中元素的数量级是O(n)级别的
     */
    public void build(int o, int l, int r, int[] nums){
        if(l == r){
            max[o] = nums[l];
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
        max[o] = Math.max(max[2 * o], max[2 * o + 1]);
    }

    /**
        o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        L, R, add: 要更新的区间范围, 以及要更新的区间内 每个元素 要增加的值
        单次add操作的时间复杂度是O(logn)
        因为add操作最终可以归结成递归下去两条从根到叶子的路径(当然是最长的情况下), 每条路径长度都是O(logn)级别的
     */
    public void add(int o, int l, int r, int L, int R, int add){
        if(L <= l && R >= r){
            // 当前区间[l, r]被要更新的区间[L, R]完全包含在里面
            // 此时只需要更新当前区间的lazy tag即可, 不需要继续向下递归更新
            doAdd(o, l, r, add);
            return;
        }
        // 当前区间[l, r]不完全被[L, R]包含在里面, 此时需要将当前节点的lazy tag传递给左右子树
        spread(o, l, r);
        // 继续递归更新左右子区间
        int mid = (l + r) >> 1;
        if(L <= mid) add(2 * o, l, mid, L, R, add);
        if(mid < R) add(2 * o + 1, mid + 1, r, L, R, add);
        // 最后使用左右子区间更新当前区间的元素和
        up(o);
    }

    /**
        o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        L, R, resetVal: 要重置的区间范围, 以及要重置的区间内每个元素重置成的值
        单次add操作的时间复杂度是O(logn)
        因为add操作最终可以归结成递归下去两条从根到叶子的路径(当然是最长的情况下), 每条路径长度都是O(logn)级别的
     */
    public void rangeReset(int o, int l, int r, int L, int R, int resetVal){
        if(L <= l && R >= r){
            // 当前区间[l, r]被要更新的区间[L, R]完全包含在里面
            // 此时只需要更新当前区间的lazy tag即可, 不需要继续向下递归更新
            doRangeReset(o, l, r, resetVal);
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
        针对add()操作
        由于add()操作优先级比较低, 因此此时直接修改sum[o]以及todoAdd即可
     */
    private void doAdd(int o, int l, int r, int add){
        max[o] += add;
        todoAdd[o] += add;
    }

     /**
        接受父节点传下来的lazy tag, 更新当前节点的sum[o]以及当前节点的lazy tag
        针对rangeReset()操作
        此时由于rangeReset操作比add操作优先级要更高, 因此doRangeReset()方法中, 
        首先需要清除当前区间上add类型的lazy tag, 并且按照此时的rangeReset操作来修改sum[o]和todoRangeReset
     */
    private void doRangeReset(int o, int l, int r, int resetVal){
        todoAdd[o] = 0;    // 清除add类型的lazy tag
        max[o] = resetVal;
        todoReset[o] = resetVal;
        valid[o] = true;
    }

    /**
        将父节点o上的lazy tag传递给(下发给)其左右子节点
        由于父节点上有两种类型的lazy tag, 并且rangeReset类型的lazy tag优先级更高, 因此需要先传递rangeReset类型的lazy tag
     */
    private void spread(int o, int l, int r){
        int mid = (l + r) >> 1;
        if(valid[o]){
            // 下发rangeReset类型的lazy tag
            doRangeReset(o * 2, l, mid, todoReset[o]);
            doRangeReset(o * 2 + 1, mid + 1, r, todoReset[o]);
            valid[o] = false;
        }
        if(todoAdd[o] != 0){
            // 下发add类型的lazy tag
            doAdd(o * 2, l, mid, todoAdd[o]);
            doAdd(o * 2 + 1, mid + 1, r, todoAdd[o]);
            todoAdd[o] = 0;
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
            return max[o];
        }
        // 需要递归向下查询
        // 首先将当前节点的lazy tag传递下去
        spread(o, l, r);
        int mid = (l + r) >> 1;
        int max = Integer.MIN_VALUE;
        if(L <= mid) max = Math.max(max, query(2 * o, l, mid, L, R));
        if(mid < R) max = Math.max(max, query(2 * o + 1, mid + 1, r, L, R));
        return max;
    }
}
