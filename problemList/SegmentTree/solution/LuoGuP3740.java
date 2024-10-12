package problemList.SegmentTree.solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
线段树上维护的信息很有意思
线段树上维护的既不是 区间累加和, 也不是 区间最大值, 因为这里我们是需要求最终能看到的海报数量
贴海报的操作: 当贴一张海报时, 其实是将海报覆盖范围上的其他海报(如果有的话)全部都覆盖了
因此贴海报的操作可以看成是 区间覆盖 操作
让 贴海报 的操作成为 区间覆盖 的前提, 是线段树的叶子节点, 必须线段树的叶子节点维护 贴在此处的海报编号
接下来考虑对于线段树中的非叶子节点, 我们需要维护什么样的信息?
对于非叶子节点而言, 首先为了能让单次修改操作的时间复杂度是O(logn)级别, 也就是范围重置必须要能够利用懒修改, 必须要满足: 
对于单次的修改操作, 必须要能够在O(1)的时间内, 将区间上维护的信息更新出来
由于这里我们的修改操作是 区间重置, 那么线段树维护的信息就必须要能够在 区间重置 之后, 在O(1)内得到
一种自然的想法, 就是线段树维护 当前区间的海报的编号 , 特别的, 如果当前区间贴了多个海报, 那么此时当前区间维护的编号可以取一个特殊值(比如-1, 0, 等等)

本题的贴海报的范围能够达到 1e7 的级别, 因此显然需要进行离散化
但是进行离散化的时候需要注意, 由于题目的特殊性, 如果直接进行离散化, 那么可能会出问题
考虑下面这一种情况
海报: 海报1: [1, 5], 海报2: [1, 2], 海报3: [4, 5] 
对于上面的这三个海报, 按顺序贴完之后, 墙上[1, 2]区间应该是海报1, [3, 3]区间应该是海报2, [4, 5]区间应该是海报3
此时我们如果直接进行离散化操作, 那么: 先得到所有用到的左右边界: 1, 5, 2, 4
排序: 1, 2, 4, 5
离散化后的值: 1 -> 1, 2 -> 2, 4 -> 3, 5 -> 4
那么三个海报的范围分别是: 海报1: [1, 4], 海报2: [1, 2], 海报3: [3, 4]
那么在这种情况下, 显然海报1就被后面贴上的海报2和海报3完全覆盖了, 这显然改变了原来的意思
如何解决这种问题? 
可以发现, 上述错误产生的原因, 是因为在原本的下标2和下标4之间没有留出足够的距离, 因此导致离散化后的2和4下标贴在了一起, 因此本来可以在下标3地方漏出来的海报1
在离散化后无法漏出来
通过上面的分析, 也可以得出解决这种问题的方法, 即 我们要能够保证, 在离散化后的下标之间, 也要能够留出足够的距离, 让原本能漏出来的海报在离散化后依旧能够漏出来
一种自然的想法, 就是我们在原来离散化前的每两个下标之间, 再添加一个新的下标, 将添加的下标和原本就要用到的下标, 一起进行离散化
这样就能够保证, 对于离散化前的不同海报左右边界的下标之间, 在离散化后, 一定能够留出足够的距离, 让原本能够漏出来的海报在离散化后依旧能够漏出来

coding部分: 
    线段树的板子就随便找一个能够rangeReset的板子就行, 反正都还得改

    离散化这里我同样使用两种方式, 就当是练手了
 */
public class LuoGuP3740 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken(); int n = (int)in.nval;
        in.nextToken(); int m = (int)in.nval;

        SegmentTree segTree = new SegmentTree(n);

        int[][] postRange = new int[m][2];

        for(int i = 0;i < m;i++){
            in.nextToken(); int l = (int)in.nval;
            in.nextToken(); int r = (int)in.nval;
            postRange[i] = new int[]{l, r};
        }

        discretization1(postRange);

        int postIdx = 1;
        for(int[] range : postRange){
            segTree.rangeReset(1, 1, n, getIdx1(range[0]), getIdx1(range[1]), postIdx++);
        }

        out.println(segTree.query(1, 1, n));

        out.flush();
		out.close();
		br.close();
    }

    // 两种离散化方式都使用这个List
    private static List<Integer> list = new ArrayList<>();

    // 第一种离散化方式需要用到的保存映射关系的Map
    // <离散化前, 离散化后>
    private static Map<Integer, Integer> hashMap = new HashMap<>();

    private static void discretization1(int[][] postRange){
        Set<Integer> set = new HashSet<>();
        for(int[] range : postRange){
            set.add(range[0]);
            set.add(range[1]);
        }
        list.addAll(set);
        Collections.sort(list);
        for(int i = 0;i < list.size();i++){
            if(i != list.size() - 1 && list.get(i + 1) - list.get(i) > 1) list.add(list.get(i) + 1);
        }
        Collections.sort(list);
        for(int i = 0;i < list.size();i++){
            hashMap.put(list.get(i), i + 1);
        }
    }

    private static int getIdx1(int idx){
        return hashMap.get(idx);
    }

    private static void discretization2(int[][] postRange){
        Set<Integer> set = new HashSet<>();
        for(int[] range : postRange){
            set.add(range[0]);
            set.add(range[1]);
        }
        list.addAll(set);
        Collections.sort(list);
        for(int i = 0;i < list.size();i++){
            if(i != list.size() - 1 && list.get(i + 1) - list.get(i) > 1) list.add(list.get(i) + 1);
        }
        Collections.sort(list);
    }

    private static int getIdx2(int idx){
        int l = 0, r = list.size() - 1;
        while(l <= r){
            int mid = (l + r) >> 1;
            if(list.get(mid) == idx) return mid;
            else if(list.get(mid) > idx) r = mid - 1;
            else l = mid + 1;
        }
        return -1;      // 理论上不会走到这里, 在二分的过程中一定能找到并返回
    }
}

/**
    线段树维护的是当前区间贴的海报的编号, 特别的, 如果当前区间贴的海报不止一种, 那么当前区间的编号为-1(随便找的一个非法值)
    线段树要支持 区间重置 功能
    查询操作只需要执行一次, 并且是查询所有叶节点上的不同海报编号的数量
 */
class SegmentTree {
    private int[] postId;     // 当前区间的海报编号, 特别的, 如果当前区间贴的海报不止一种, 那么当前区间的编号为-1(随便找的一个非法值)
    private int[] todo;
    private boolean[] valid;    // valid[i] 用来表示 todo[i]是否有效

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTree(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.postId = new int[len];
        this.todo = new int[len];
        this.valid = new boolean[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
     */
    public void build(int o, int l, int r, int[] nums){
        if(l == r){
            nums[o] = nums[l];
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
        postId[o] = (postId[2 * o] == -1 || postId[2 * o + 1] == -1) ? -1 : (postId[2 * o] == postId[2 * o + 1] ? postId[2 * o] : -1);
    }

    /**
        o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        L, R, resetVal: 要重置的区间范围, 以及区间要重置的值
     */
    public void rangeReset(int o, int l, int r, int L, int R, int resetVal){
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
        接受父节点传下来的lazy tag, 更新当前节点的sum[o]以及当前节点的lazy tag
     */
    private void do_(int o, int l, int r, int resetVal){
        postId[o] = resetVal;
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

    private Set<Integer> hashSet = new HashSet<>();     // 查询操作去重要用的哈希集合
    /**
        查询操作只需要执行一次, 并且是查询所有叶节点上的不同海报编号的数量
        查询操作的复杂度是O(n)级别的
        调用入口: o, l, r = 1, 1, n
     */
    public int query(int o, int l, int r){
        if(l == r || postId[o] != -1){     // 走到叶子结点, 或者当前区间上只有一种海报, 此时可以直接返回
            if(!hashSet.contains(postId[o])){
                hashSet.add(postId[o]);
                return 1;
            }else{
                return 0;
            }
        }
        // 需要递归查询左右子区间
        int mid = (l + r) >> 1;
        return query(2 * o, l, mid) + query(2 * o + 1, mid + 1, r);
    }
}
