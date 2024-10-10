package problemList.SegmentTree.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
本题是一个经典的 线段树 特别修改的题目, 因此我把这题的详解放到了线段树的笔记中
请移步 notes/SegmentTree/SegmentTree.md 中查看

这里我就重点说一下coding如何实现
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

        in.nextToken(); int m = (int)in.nval;
        for(int i = 0;i < m;i++){
            in.nextToken(); int k = (int)in.nval;
            in.nextToken(); int a = (int)in.nval;
            in.nextToken(); int b = (int)in.nval;
            int l = Math.min(a, b), r = Math.max(a, b);
            if(k == 0){
                segTree.sqrt(1, 1, n, l, r);
            }else{
                out.println(segTree.querySum(1, 1, n, l, r));
            }
        }


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
    private long[] max;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTree(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new long[len];
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
        int mid = (l + r) >> 1;
        build(o * 2, l, mid, nums);
        build(o * 2 + 1, mid + 1, r, nums);
        up(o);
    }

    private void up(int o){
        sum[o] = sum[2 * o] + sum[2 * o + 1];
        max[o] = Math.max(max[2 * o], max[2 * o + 1]);
    }

    /**
        对[L, R]区间内的所有元素进行开平方(sqrt)操作
        调用入口: o, l, r = 1, 1, n
     */
    public void sqrt(int o, int l, int r, int L, int R){
        // 剪枝
        if(queryMax(o, l, r, L, R) == 1) return;
        // 否则递归
        // 如果走到叶子结点
        if(l == r){
            sum[o] = (int)Math.sqrt(sum[o]);
            max[o] = sum[o];
            return;
        }
        // 递归左右节点
        int mid = (l + r) >> 1;
        if(L <= mid) sqrt(o * 2, l, mid, L, R);
        if(mid < R) sqrt(o * 2 + 1, mid + 1, r, L, R);
        up(o);
    }

    /**
        调用方式: querySum(1, 1, n, L, R)
        返回[L, R]区间的元素和
     */
    public long querySum(int o, int l, int r, int L, int R) {
        if (L <= l && R >= r) {
            // 当前节点所在区间[l, r]包含在要查询的区间[L, R]当中, 直接返回
            return sum[o];
        }
        // 递归查询
        long sum = 0;
        int mid = (l + r) >> 1;
        if (L <= mid) sum += querySum(2 * o, l, mid, L, R);
        if (mid < R) sum += querySum(2 * o + 1, mid + 1, r, L, R);
        return sum;
    }

    /**
        调用方式: queryMax(1, 1, n, L, R)
        返回[L, R]区间的最大值
     */
    public long queryMax(int o, int l, int r, int L, int R){
        if(L <= l && R >= r){
            return max[o];
        }
        int mid = (l + r) >> 1;
        long max = Long.MIN_VALUE;
        if(L <= mid) max = Math.max(max, queryMax(2 * o, l, mid, L, R));
        if(mid < R) max = Math.max(max, queryMax(2 * o + 1, mid + 1, r, L, R));
        return max;
    }
}
