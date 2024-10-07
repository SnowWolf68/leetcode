package notes.SegmentTree;

public class SegmentTreeLazy {
    private int[] sum;
    private int[] todo;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazy(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
        this.todo = new int[len];
    }

    // o, l, r: 当前节点以及当前区间的左右端点
    private void build(int o, int l, int r){
        if(l == r){
            // 递归到叶子结点
            // TODO 更新...
            return;
        }
        // 划分区间, 继续递归左右子区间
        int mid = (l + r) >> 1;
        build(2 * o, l, mid);
        build(2 * o + 1, mid + 1, r);
        // TODO 维护...
    }

    // o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
    // L, R, add: 要更新的区间范围, 以及要更新的区间内 每个元素 要增加的值
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

    private void spread(int o, int l, int r){
        if(todo[o] != 0){
            // 如果当前节点的lazy tag有东西要向下传递
            int mid = (l + r) >> 1;
            do_(2 * o, l, mid, todo[o]);
            do_(2 * o + 1, mid + 1, r, todo[o]);
            // todo[2 * o] += todo[o];
            // todo[2 * o + 1] += todo[o];
            todo[o] = 0;
        }
    }

    // o, l, r: 当前节点, 当前区间范围, 调用入口: o, l, r = 1, 1, n
    // L, R: 要查询的区间范围[L, R]
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
