package problemList.SegmentTree.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
线段树上的特别修改
    线段树要能够满足 区间每个数开平方 区间查询累加和 操作  这是一个涉及到特别修改的线段树, 但是可以拿 SegmentTreeBasic 基本线段树的板子稍加修改得到

特别修改: 
    线段树上的修改操作, 如果想要做到单次O(logn)级别的时间复杂度, 必须要满足 "一段范围上的修改操作, 必须要能够在O(1)的时间内, 把这段区间上维护的信息加工出来"
    本题中的修改操作是对单个元素开平方, 显然不符合上面的要求
    
 */
public class LuoGuP4145 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken(); int n = (int)in.nval;
        long[] nums = new long[n + 1];
        for(int i = 1;i <= n;i++){
            in.nextToken(); long num = (long)in.nval;
            nums[i] = num;
        }
        SegmentTree segTree = new SegmentTree(n);
        segTree.build(1, 1, n, nums);


        out.flush();
		out.close();
		br.close();
    }
}

/**
    在基本线段树上修改得到的 支持 特别修改 的线段树

    注意要把int改成long, 否则这题的数据范围会爆int
 */
class SegmentTree {
    private long[] sum;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTree(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new long[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
     */
    public void build(int o, int l, int r, long[] nums){
        if(l == r){
            sum[o] = nums[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(o * 2, l, mid, nums);
        build(o * 2 + 1, mid + 1, r, nums);
        up(o);
    }

    private void up(int o){
        sum[o] = sum[2 * o] + sum[2 * o + 1];
    }

    /**
        对[L, R]区间内的所有元素进行开平方(sqrt)操作
        调用入口: o, l, r = 1, 1, n
     */
    public void sqrt(int o, int l, int r, int L, int R){

    }

    // 调用方式: query(1, 1, n, L, R)
    // 返回[L, R]区间的元素和
    public long query(int o, int l, int r, int L, int R) {
        if (L <= l && R >= r) {
            // 当前节点所在区间[l, r]包含在要查询的区间[L, R]当中, 直接返回
            return sum[o];
        }
        // 递归查询
        long sum = 0;
        int mid = (l + r) >> 1;
        if (L <= mid)
            sum += query(2 * o, l, mid, L, R);
        else
            sum += query(2 * o + 1, mid + 1, r, L, R);
        return sum;
    }
}
