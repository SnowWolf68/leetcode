package notes.SegmentTree;

/**
线段树支持 范围增加 范围查询
维护累加和
 */
public class SegmentTreeLazyAddQuerySum {
    private int[] sum;
    private int[] todo;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyAddQuerySum(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
        this.todo = new int[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
        线段树初始化 (建树) 的时间复杂度是O(n), 
        因为递归会把sum数组的所有元素都访问一遍, 而sum数组中元素的数量级是O(n)级别的
     */
    private void build(int o, int l, int r, int[] nums){
        if(l == r){
            sum[o] = nums[l];
            return;
        }
        // 划分区间, 继续递归左右子区间
        int mid = (l + r) >> 1;
        build(2 * o, l, mid, nums);
        build(2 * o + 1, mid + 1, r, nums);
        sum[o] = sum[2 * o] + sum[2 * o + 1];
    }

    /**
        o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        L, R, add: 要更新的区间范围, 以及要更新的区间内 每个元素 要增加的值
        单次add操作的时间复杂度是O(logn)
        因为add操作最终可以归结成递归下去两条从根到叶子的路径(当然是最长的情况下), 每条路径长度都是O(logn)级别的
     */
    private void add(int o, int l, int r, int L, int R, int add){
        if(L <= l && R >= r){
            // 当前区间[l, r]被要更新的区间[L, R]完全包含在里面
            // 此时只需要更新当前区间的lazy tag即可, 不需要继续向下递归更新
            do_(o, l, r, add);
            return;
        }
        // 当前区间[l, r]不完全被[L, R]包含在里面, 此时需要将当前节点的lazy tag传递给左右子树
        spread(o, l, r);
        // 继续递归更新左右子区间
        int mid = (l + r) >> 1;
        if(L <= mid) add(2 * o, l, mid, L, R, add);
        if(mid < R) add(2 * o + 1, mid + 1, r, L, R, add);
        // 最后使用左右子区间更新当前区间的元素和
        sum[o] = sum[2 * o] + sum[2 * o + 1];
    }

    private void do_(int o, int l, int r, int add){
        sum[o] += add * (r - l + 1);
        todo[o] += add;
    }

    /**
        将父节点o上的lazy tag传递给(下发给)其左右子节点
     */
    private void spread(int o, int l, int r){
        if(todo[o] != 0){
            // 如果当前节点的lazy tag有东西要向下传递
            int mid = (l + r) >> 1;
            do_(2 * o, l, mid, todo[o]);
            do_(2 * o + 1, mid + 1, r, todo[o]);
            todo[o] = 0;
        }
    }

    /**
        o, l, r: 当前节点, 当前区间范围, 调用入口: o, l, r = 1, 1, n
        L, R: 要查询的区间范围[L, R]
        单次query操作的时间复杂度也是O(logn)级别的
        因为query操作也可以归结为两条从根到叶子的路径(最长的情况下), 每条路径长度的数量级是O(logn)级别的
     */
    private int query(int o, int l, int r, int L, int R){
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
