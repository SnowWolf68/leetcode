package notes.SegmentTree;

/**
线段树支持 范围重置 范围增加 范围查询
维护累加和

和前几个线段树不同的是, 这个线段树要求支持两种修改操作
因此也就意味着对于线段树上的每个区间来说, 此时只有一个lazy tag就不能满足要求, 必须有两个lazy tag, 分别对应上述两种修改操作

需要注意的是, 这两种类型的lazy tag之间存在一定的优先级关系
    比如 如果某个区间已经有一个add的lazy tag, 此时又来了一个reset的lazy tag
    那么显然重置类型的lazy tag要比区间增加类型的lazy tag的优先级更高, 也就是此时reset类型的lazy tag会把add类型的lazy tag给抹除掉
    而如果当前区间已经有一个reset类型的lazy tag, 此时又来了一个add类型的lazy tag, 那么此时add类型的lazy tag就不会把reset的lazy tag给覆盖掉
    在这种情况下, 我们应该保留当前节点的reset类型的lazy tag不变, 并且修改当前区间的sum[o], 以及修改当前节点的add类型的lazy tag的信息

        -- 一开始我看到这个题的时候, 想到的是, 如果当前区间既有reset类型的lazy tag, 此时又来了一个add类型的lazy tag, 
            那么此时是不是可以直接将add类型的lazy tag直接更新到reset类型的lazy tag上, 因为对于当前区间来说, 
            reset类型的lazy tag要比add类型的lazy tag来得早, 因此add是在reset的基础上进行的add, 也就相当于对reset的值进行了add
        这样的想法确实没什么问题, 但是在实现上完全没必要
        首先对于某一个区间来说, reset类型的lazy tag和add类型的lazy tag都是要能够存储的, 这很显然
        因此我们完全没有必要像上面说的那样, 将add类型的lazy tag更新到reset类型的lazy tag上去
        除此之外, 上面这种 将add类型的lazy tag更新到reset类型的lazy tag上 的情况出现也是要有条件的, 必须满足当前节点(区间)要有一个reset
        类型的lazy tag, 此时又来了一个add类型的lazy tag, 那么此时可以合并, 然而并不是每次来一个add类型的lazy tag, 当前区间之前就一定有一个
        reset类型的lazy tag, 因此需要多做判断, 这更是没有必要的

        因此我们在doAdd()方法中, 直接将add类型的lazy tag更新到当前区间对应的add的lazy tag上即可
            不用考虑什么将add类型的lazy tag和reset类型的lazy tag合并的问题, 这样能够让代码更简单, 并且在理解上也更好理解

假设上述两个操作分别为 rangeReset() 和 add() 下面我们简单分析一下这两个操作如何实现

1. rangeReset()
    1. 当递归到一个小于当前任务区间[L, R]的区间时, 由于此时rangeReset的优先级更高
        因此清除当前区间上的add类型的lazy tag, 并且修改当前区间的sum[o], 同时更新当前区间的reset的lazy tag
        
        此时由于区间的修改操作分为两种, 因此原来的do_()方法也应该分为两种, 分别是doRangeReset()以及doAdd()
        这里对应的显然应该是doRangeReset()
    2. 如果当前区间[l, r]不全部包含与任务区间[L, R]中, 那么此时还是首先需要让当前节点上的两种类型的lazy tag都传递下去
        因此这里首先分析一下spread()如何实现
            spread()中, 由于当前节点有可能两种类型的lazy tag都有, 并且这两种类型的lazy tag之间还有优先级关系
            根据优先级高低, 我们首先应该将父节点(下标为o)的节点上的reset类型的lazy tag传递到左右子节点(下标分别为2 * o, 2 * o + 1)上
            然后再将父节点的add类型的lazy tag传递到左右子节点中
        将当前区间的两种类型的lazy tag传递下去之后, 接下来还是要取mid, 然后判断任务区间[L, R]是在当前区间[l, r]的左半部分还是右半部分, 还是两边都有
        然后在子区间中继续递归调用rangeReset()
        最后别忘了up()操作
2. add()
    1. 当递归到一个小于当前任务区间[L, R]的区间时, 此时直接更新当前区间的sum[o], 并且更新当前区间的add类型的lazy tag即可
        -- 上面的操作其实也就对应了doAdd()方法
    2. 如果当前区间[l, r]不全部包含与任务区间[L, R]中, 那么此时还是首先需要让当前节点上的两种类型的lazy tag都传递下去
        即调用spread()方法, spread()方法如何实现在上面我都说了
        然后还是计算mid, 判断任务区间[L, R]是在当前区间[l, r]的左半部分还是右半部分, 还是两边都有
        然后在子区间中继续递归调用add()
        最后别忘了up()操作

 */
public class SegmentTreeLazyResetAndAddQuerySum {
    private int[] sum;
    private int[] todoAdd;
    private int[] todoReset;
    private boolean[] valid;    // 同样用来表示todoReset[o]是否有效

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyResetAndAddQuerySum(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
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
        sum[o] += add * (r - l + 1);
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
        sum[o] = resetVal * (r - l + 1);
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
