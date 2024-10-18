package problemList.SegmentTree.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
线段树 维护区间和, 支持区间翻转操作 随便掏出一个QuerySum的板子拿来改改
 */
public class LuoGuP3870 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken(); int n = (int)in.nval;
        in.nextToken(); int m = (int)in.nval;

        SegmentTree segTree = new SegmentTree(n);
        for(int i = 0;i < m;i++){
            in.nextToken(); int c = (int)in.nval;
            in.nextToken(); int a = (int)in.nval;
            in.nextToken(); int b = (int)in.nval;
            if(c == 0){
                segTree.rangeReverse(1, 1, n, a, b);
            }else{
                out.println(segTree.query(1, 1, n, a, b));
            }
        }


        out.flush();
		out.close();
		br.close();
    }
}

class SegmentTree {
    private int[] sum;
    private boolean[] todo;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTree(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
        this.todo = new boolean[len];
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
        L, R 要翻转的区间范围
     */
    public void rangeReverse(int o, int l, int r, int L, int R){
        if(L <= l && R >= r){
            do_(o, l, r);
            return;
        }
        spread(o, l, r);
        int mid = (l + r) >> 1;
        if(L <= mid) rangeReverse(o * 2, l, mid, L, R);
        else if(mid < R) rangeReverse(o * 2 + 1, mid + 1, r, L, R);
    }

    /**
        翻转区间
        接受父节点传下来的lazy tag, 更新当前节点的sum[o]以及当前节点的lazy tag
     */
    private void do_(int o, int l, int r){
        sum[o] = r - l + 1 - sum[o];
        todo[o] = true;
    }

    /**
        将父节点o上的lazy tag传递给(下发给)其左右子节点
     */
    private void spread(int o, int l, int r){
        if(!todo[o]){
            // 如果当前节点的lazy tag有东西要向下传递
            int mid = (l + r) >> 1;
            do_(2 * o, l, mid);
            do_(2 * o + 1, mid + 1, r);
            todo[o] = false;
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
