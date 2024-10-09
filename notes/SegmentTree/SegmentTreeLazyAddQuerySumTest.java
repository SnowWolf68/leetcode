package notes.SegmentTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class SegmentTreeLazyAddQuerySumTest {
    /**
        模版测试: 测试链接: https://www.luogu.com.cn/problem/P3372
     */
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        in.nextToken(); int n = (int)in.nval;
        in.nextToken(); int m = (int)in.nval;

        long[] nums = new long[n + 1];
        for(int i = 1;i <= n;i++){
            in.nextToken();
            nums[i] = (long)in.nval;
        }

        SegmentTreeLazyAddQuerySum segTree = new SegmentTreeLazyAddQuerySum(n);
        segTree.build(1, 1, n, nums);
        for(int i = 0;i < m;i++){
            in.nextToken(); 
            int op = (int)in.nval;
            in.nextToken();
            int x = (int)in.nval;
            in.nextToken();
            int y = (int)in.nval;
            if(op == 1){
                in.nextToken();
                long k = (long)in.nval;
                segTree.add(1, 1, n, x, y, k);
            }else{
                out.println(segTree.query(1, 1, n, x, y));
            }
        }

        out.flush();
		out.close();
		br.close();
    }
}

/**
线段树支持 范围增加 范围查询
维护累加和

注意输入数据可能会爆int, 这里把模版中的int改为long
 */
class SegmentTreeLazyAddQuerySum {
    private long[] sum;
    private long[] todo;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyAddQuerySum(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new long[len];
        this.todo = new long[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
        线段树初始化 (建树) 的时间复杂度是O(n), 
        因为递归会把sum数组的所有元素都访问一遍, 而sum数组中元素的数量级是O(n)级别的
     */
    public void build(int o, int l, int r, long[] nums){
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
    public void add(int o, int l, int r, int L, int R, long add){
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
    private void do_(int o, int l, int r, long add){
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
    public long query(int o, int l, int r, int L, int R){
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
        if(L <= mid) sum += query(2 * o, l, mid, L, R);
        if(mid < R) sum += query(2 * o + 1, mid + 1, r, L, R);
        return sum;
    }
}

