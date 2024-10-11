package problemList.SegmentTree.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
这题的修改操作是 区间取模 , 取模的操作显然也不满足 "一段范围上的修改操作, 能够在O(1)的时间内, 将这段区间上维护的信息加工出来"
因此也属于 线段树上的特别修改, 需要使用势能分析来分析复杂度

本题中的线段树要支持的区间修改操作是 对区间上的每个数字取模, 显然这也是属于特别修改, 因为在对区间上的每一个数取模之后, 无法在O(1)的时间内
将这个区间上维护的信息加工出来

因此需要使用势能分析来分析本题的复杂度

由于对区间上每个元素进行的取模操作, 每次都需要递归到线段树的叶子结点, 才能完成修改
因此分析一下, 对于某一个叶子结点, 我们最多需要走到这里多少次

这里就需要分析一下 取模 这个操作有什么特性
1. 取模的第一个特性: 假设区间上某一个元素为v, 如果不断的对v进行取模操作, 直到v == 0时, 之后无论是对什么数取模, v都会保持0不变
也就意味着如果我们发现某一个区间上的最大值是0, 那么此时更新操作就不用向下走了, 因为0无论对谁取模, 结果都是0

2. 取模的第二个特性: 对于v而言, 如果取模的数x大于v, 那么此时v % x == v, 这也是一个很好用的性质, 因为如果我们发现一个区间上的最大值v小于
要取模的数x, 那么此时也不用向下递归更新, 因为此时区间上的任何数, 对大于自己的一个数取模, 其结果还是自己本身

3. 取模的第三个特性: 对于元素v而言, 考虑将其取模至多多少次, 就能将其减小到0?
    假设元素为v, 要对x取模, 可以这样分类讨论
        1. 如果x < v / 2: 由于a对b取模, 取模的结果一定会小于b, 因此在这种情况下, v % x的结果一定小于v / 2
        2. 如果x > v / 2: 此时v / x的商一定是1, 余数就是v - x, 又因为x > v / 2, 因此v - x < v / 2, 也就是说此时的余数一定小于v / 2
    通过上面的分析可以发现, 对于v来说, 如果对一个比v小的数取模, 那么取模后的结果一定会让v减少至少一半
    换句话说, 对于v来说, 至多对小于v的数取模logv次, 之后v就会变成0, 之后再取模都不会影响v的值了

结合上面的分析, 可以使用势能分析来分析当前这个更新操作的复杂度

由于对于每个叶节点来说, 至多对小于v的数取模logv次, 之后v就会变成0了, 因此对于每一个叶节点来说, 其具有的势能为 logv * logn (即走到叶节点的次数 * 树高)
因此整棵线段树的势能为 n * logv * logn, 因此总的更新操作的时间复杂度为 O(n * logv * logn)

但是这题除了区间取模之外, 还有单点重置的操作
需要注意的是, 如果将某个元素置为x, 那么可能会增加这个叶子结点的势能, 每次增加 logx * logn 的势能, 然而, 本题中的重置操作, 只涉及单点重置
而不涉及区间重置, 并且由于总的操作次数一定, 那么总的重置操作给整棵线段树增加的势能其实也是有限的
因此本题的更新的时间复杂度也并不会太高

coding部分: 本题的线段树可以拿 SegmentTreeBasic 这个板子稍作修改得到

coding: 线段树支持: 区间重置 区间取模 区间查询累加和
因此可以拿 SegmentTreeLazyResetQuerySum 这个板子改一下
需要注意的是, 虽然区间取模的操作无法使用lazy tag优化, 但是区间重置要想做到单次O(logn)的时间复杂度, 还是需要加上区间重置对应的lazy tag
 */
public class CF438D {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken(); int n = (int)in.nval;
        in.nextToken(); int m = (int)in.nval;

        long[] nums = new long[n + 1];
        for(int i = 1;i <= n;i++){
            in.nextToken(); long num = (long)in.nval;
            nums[i] = num;
        }

        SegmentTree segTree = new SegmentTree(n);
        segTree.build(1, 1, n, nums);

        for(int i = 0;i < m;i++){
            in.nextToken(); int op = (int)in.nval;
            if(op == 1){
                in.nextToken(); int l = (int)in.nval;
                in.nextToken(); int r = (int)in.nval;
                out.println(segTree.querySum(1, 1, n, l, r));
            }else if(op == 2){
                in.nextToken(); int l = (int)in.nval;
                in.nextToken(); int r = (int)in.nval;
                in.nextToken(); int x = (int)in.nval;
                segTree.rangeMod(1, 1, n, l, r, x);
            }else{
                in.nextToken(); int k = (int)in.nval;
                in.nextToken(); int x = (int)in.nval;
                segTree.rangeReset(1, 1, n, k, k, x);
            }
        }

        out.flush();
		out.close();
		br.close();
    }
}


class SegmentTree {
    private long[] sum;
    private long[] todo;
    private boolean[] valid;    // valid[i] 用来表示 todo[i]是否有效
    private long[] max;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTree(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new long[len];
        this.todo = new long[len];
        this.valid = new boolean[len];
        this.max = new long[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
     */
    public void build(int o, int l, int r, long[] nums){
        if(l == r){
            sum[o] = nums[l];
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
        sum[o] = sum[2 * o] + sum[2 * o + 1];
        max[o] = Math.max(max[2 * o], max[2 * o + 1]);
    }

    /**
        o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        L, R, resetVal: 要重置的区间范围, 以及区间要重置的值
     */
    public void rangeReset(int o, int l, int r, int L, int R, long resetVal){
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
        [L, r]区间内的所有元素对mod取模
     */
    public void rangeMod(int o, int l, int r, int L, int R, int mod){
        if(queryMax(o, l, r, L, R) < mod) return;  // 对大于自己的数取模, 结果还是自己本身
        if(l == r){
            // 走到叶子结点
            sum[o] %= mod;
            max[o] %= mod;
            return;
        }
        int mid = (l + r) >> 1;
        if(L <= mid) rangeMod(o * 2, l, mid, L, R, mod);
        if(mid < R) rangeMod(o * 2 + 1, mid + 1, r, L, R, mod);
        up(o);
    }

    /**
        接受父节点传下来的lazy tag, 更新当前节点的sum[o]以及当前节点的lazy tag
     */
    private void do_(int o, int l, int r, long resetVal){
        max[o] = resetVal;
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
    public long querySum(int o, int l, int r, int L, int R){
        if(L <= l && R >= r){
            // 如果当前区间[l, r]完全包含在要查询的区间[L, R]当中
            // 此时无需继续递归查询, 直接返回结果即可
            return sum[o];
        }
        // 需要递归向下查询
        // 首先将当前节点的lazy tag传递下去
        spread(o, l, r);
        int mid = (l + r) >> 1;
        long sum = 0;
        if(L <= mid) sum += querySum(2 * o, l, mid, L, R);
        if(mid < R) sum += querySum(2 * o + 1, mid + 1, r, L, R);
        return sum;
    }

    // return: [L, R]区间的最大值
    public long queryMax(int o, int l, int r, int L, int R){
        if(L <= l && R >= r){
            return max[o];
        }
        spread(o, l, r);
        int mid = (l + r) >> 1;
        long max = Long.MIN_VALUE;
        if(L <= mid) max = Math.max(max, queryMax(2 * o, l, mid, L, R));
        if(mid < R) max = Math.max(max, queryMax(2 * o + 1, mid + 1, r, L, R));
        return max;
    }
}