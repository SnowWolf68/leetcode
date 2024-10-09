package notes.SegmentTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
题目2
瓶子里的花朵
给定n个瓶子，编号从0~n-1，一开始所有瓶子都是空的
每个瓶子最多插入一朵花，实现以下两种类型的操作
操作 1 from flower : 一共有flower朵花，从from位置开始依次插入花朵，已经有花的瓶子跳过
                    如果一直到最后的瓶子，花也没有用完，就丢弃剩下的花朵
                    返回这次操作插入的首个空瓶的位置 和 最后空瓶的位置
                    如果从from开始所有瓶子都有花，打印"Can not put any one."
操作 2 left right  : 从left位置开始到right位置的瓶子，变回空瓶，返回清理花朵的数量
测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=4614

输入: 
    第一行: t, 测试数据组数
        每一组测试数据: 先给出n, m, 其中n是当前这组数据的数组长度, m是操作数
            接下来m行, 每行: 
                            1. 第一种操作: op(op = 1), from, flowers    -->  对应题目中的操作1
                            2. 第二种操作: op(op != 1), left, right     -->  对应题目中的操作2
输出: n行, 行间有空行分割

线段树 + 二分搜索的结合

首先肯定需要一个支持 区间重置 查询区间累加和 的线段树, 直接掏出 SegmentTreeLazyResetQuerySum 的板子

分析一下题目中给出的两种操作如何实现
    操作 1: 
        假设花瓶中有花我们使用1表示, 花瓶中没有花我们使用0表示
        首先我们可以查询[from, n]区间上的元素和, 这个操作可以使用线段树实现, 如果元素和等于n - from + 1, 那么说明[from, n]这段区间上的花瓶中都插入花了
        因此打印 "Can not put any one." 操作结束
        如果元素和小于n - from + 1, 说明此时可以插入花, 此时我们需要找到能够插入的第一个位置和最后一个位置
        假设[from, n]上的元素和为sum, 那么(n - from + 1) - sum就是[from, n]区间上空着的花瓶的数量, 假设为freeCount
        判断freeCount和flowers的大小, 取count = Math.min(freeCount, flowers), 即为能够插入的花的数量
        那么我们此时需要找到from右边第一个插入花的位置, 以及插入count这些花的最后一个插入位置
        也就是我们需要找到from右边, 第一个0的下标, 以及第count个0的下标
        上述过程可以抽象为一个函数 int findKthZero(int from, int k) , 即从下标from开始, 返回第k个0的下标
        这个查找过程可以使用二分实现

        线段树上二分: 
            过程: 
                首先将当前区间划分为左区间和右区间

                查找左区间上0的个数和k的关系:   
                    1. 等于k: 说明要找的下标就是mid, return mid;
                    2. 如果大于k, 说明第k个0在左区间, 继续在左区间上二分查询
                    3. 如果小于k, 说明第k个0在右区间, 继续在右区间上二分查找
            伪代码
            int findKthZero(int from, int k){
                int l = from, r = n;
                while(l <= r){
                    int mid = (l + r) >> 1;
                    int leftZeroCount = (mid - l + 1) - segTree.query(1, 1, n, l, mid);
                    if(leftZeroCount == k) return mid;
                    else if(leftZeroCount < k) r = mid - 1;
                    else l = mid + 1;
                }
                return -1;      // 按道理来说一定会在二分的过程中返回, 不会走到这里
            }
            
    操作 2: 
            首先查询一下[left, right]区间上1的数量, 也就是[left, right]区间的元素和, 这就是该区间上花的数量
            然后调用segTree.rangeReset(1, 1, n, left, right, 0)将这段区间上全部置0即可

下面的代码我没使用hdu的平台测试, 因为注册的账号要管理员审核
不过可以写一个对拍和左神的代码对拍一下, 但是我现在暂时懒得写(bushi
 */
public class SegmentTreeBinarySearch {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        in.nextToken();
        int t = (int)in.nval;
        for(int i = 0;i < t;i++){
            in.nextToken(); int n = (int)in.nval;
            in.nextToken(); int m = (int)in.nval;
            int[] nums = new int[n + 1];
            for(int j = 1;j <= n;j++){
                in.nextToken();
                int num = (int)in.nval;
                nums[i] = num;
            }
            SegmentTreeLazyResetQuerySum segTree = new SegmentTreeLazyResetQuerySum(n);
            segTree.build(1, 1, n, nums);
            for(int j = 0;j < m;j++){
                in.nextToken(); int op = (int)in.nval;
                if(op == 1){
                    // 操作一
                    in.nextToken(); int from = (int)in.nval;
                    in.nextToken(); int flowers = (int)in.nval;
                    int freeCount = (n - from + 1) - segTree.query(1, 1, n, from, n);
                    if(freeCount == 0){
                        out.println("Can not put any one.");
                        continue;
                    }
                    int count = Math.min(freeCount, flowers);
                    int startIdx = findKthZero(segTree, n, from, 1), endIdx = findKthZero(segTree, n, from, count);
                    segTree.rangeReset(1, 1, n, startIdx, endIdx, 1);
                }else{
                    // 操作二
                    in.nextToken(); int left = (int)in.nval;
                    in.nextToken(); int right = (int)in.nval;
                    int flowerCount = segTree.query(1, 1, n, left, right);
                    out.println(flowerCount);
                    segTree.rangeReset(1, 1, n, left, right, 0);
                }
                out.println();
            }
        }

        out.flush();
		out.close();
		br.close();
    }

    /**
        return: segTree线段树上[from, n]上第k个0的下标
     */
    private static int findKthZero(SegmentTreeLazyResetQuerySum segTree, int n, int from, int k){
        int l = from, r = n;
        while(l <= r){
            int mid = (l + r) >> 1;
            int leftZeroCount = (mid - l + 1) - segTree.query(1, 1, n, l, mid);
            if(leftZeroCount == k) return mid;
            else if(leftZeroCount < k) r = mid - 1;
            else l = mid + 1;
        }
        return -1;      // 按道理来说一定会在二分的过程中返回, 不会走到这里
    }
}

/**
线段树支持 范围重置 范围查询
维护累加和
 */
class SegmentTreeLazyResetQuerySum {
    private int[] sum;
    private int[] todo;
    private boolean[] valid;    // valid[i] 用来表示 todo[i]是否有效

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyResetQuerySum(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.sum = new int[len];
        this.todo = new int[len];
        this.valid = new boolean[len];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
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
