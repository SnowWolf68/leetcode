package notes.SegmentTree;

import java.util.Arrays;

public class SegmentTreeLazyAddQueryMaxTest {
    /**
     * 这个板子同样要使用对数器验证
     */
    public static void main(String[] args) {
        // 对数器
        // n: 随机数组元素个数, v: 随机数组元素范围
        int n = 10000, v = 20000;
        int[] arr = randArraay(n, v);
        SegmentTreeLazyAddQueryMax segTree = new SegmentTreeLazyAddQueryMax(n);
        segTree.build(1, 1, n, arr);
        int[] arrCopy = Arrays.copyOf(arr, n + 1);
        // 操作次数范围
        int t = 100000;
        System.out.println("Test Started...");
        for(int i = 0;i < t;i++){
            // 随机操作类型
            int op = (int)(Math.random() * 2);  // 随机出来的op只有0, 1两种可能
            // 随机操作范围
            // (int)(Math.random() * n) 范围 [0, n - 1]
            // (int)(Math.random() * n) + 1 范围 [1, n]
            int a = (int)(Math.random() * n) + 1;
            int b = (int)(Math.random() * n) + 1;
            int L = Math.min(a, b), R = Math.max(a, b);
            if(op == 0){
                // 区间查询
                int segRet = segTree.query(1, 1, n, L, R);
                int ret = Integer.MIN_VALUE;
                for(int j = L;j <= R;j++) ret = Math.max(ret, arrCopy[j]);
                if(ret != segRet){
                    System.out.println("Wrong Answer: " + ret + " " + segRet);
                    return;
                }
            }else{
                // 区间增加
                // 随机增加的值
                int add = (int)(Math.random() * v);
                segTree.add(1, 1, n, L, R, add);
                for(int j = L;j <= R;j++) {
                    arrCopy[j] += add;
                    if(arrCopy[j] < 0) System.out.print("OverFlow!");
                }
            }
        }
        System.out.println("Accepted");
    }

    private static int[] randArraay(int n, int v) {
        int[] arr = new int[n + 1];
        for(int i = 1;i <= n;i++){
            // Math.random() 产生[0, 1) 范围的随机数
            arr[i] = (int) (Math.random() * v);
        }
        return arr;
    }
}

/**
线段树支持 范围增加 范围查询
维护区间最大值
 */
class SegmentTreeLazyAddQueryMax {
    private int[] max;
    private int[] todo;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyAddQueryMax(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.max = new int[len];
        this.todo = new int[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
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
        L, R, add: 要增加的区间范围, 以及区间要增加的值
     */
    public void add(int o, int l, int r, int L, int R, int add){
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
        up(o);
    }

    /**
        接受父节点传下来的lazy tag, 更新当前节点的sum[o]以及当前节点的lazy tag
     */
    private void do_(int o, int l, int r, int add){
        max[o] += add;
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
