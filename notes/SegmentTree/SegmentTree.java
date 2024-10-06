package notes.SegmentTree;

public class SegmentTree {
    private int[] sum;

    SegmentTree(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
    }

    // 调用方式: add(1, 1, n, idx, val)
    // 给数组idx下标的元素增加val
    private void add(int o, int l, int r, int idx, int val) {
        if (l == r) {
            // 如果递归到了叶子节点, 那么直接修改
            sum[idx] += val;
            return;
        }
        // 不是叶子节点, 判断是在左子树还是右子树, 继续递归
        int mid = (l + r) >> 1;
        if (idx <= mid)
            add(o * 2, l, mid, idx, val);
        else
            add(o * 1 + 1, mid + 1, r, idx, val);
        // 最后在回溯的过程中更新当前节点
        sum[o] = sum[2 * o] + sum[2 * o + 1];
    }

    // 调用方式: query(1, 1, n, L, R)
    // 返回[L, R]区间的元素和
    private int query(int o, int l, int r, int L, int R) {
        if (L <= l && R >= r) {
            // 当前节点所在区间[l, r]包含在要查询的区间[L, R]当中, 直接返回
            return sum[o];
        }
        // 递归查询
        int sum = 0;
        int mid = (l + r) >> 1;
        if (L <= mid)
            sum += query(2 * o, l, mid, L, R);
        else
            sum += query(2 * o + 1, mid + 1, r, L, R);
        return sum;
    }
}
