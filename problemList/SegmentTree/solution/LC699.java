package problemList.SegmentTree.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
使用这题主要是想展示一下线段树的离散化

需要用到的区间操作: 区间查询最大值, 区间重置 --> 因此掏出来SegmentTreeLazyResetQueryMax的板子来

但是有了线段树的板子还不够, 这题还需要解决额外的几个问题: 
    1. 考虑两个方块, 其x轴上的范围分别是: [1, 3], [3, 4], 按照题目要求, 这两块是可以并排放的, 
        但是如果直接使用线段树的话, 得到的结果是这两个不能并排放置, 只能一上一下放置, 这个问题如何解决?
            -- 可以使用 左闭右开 区间来解决
                具体来说, 对于第一块[1, 3]来说, 我们使用左闭右开区间, 于是第一块的范围变成了[1, 3), 换成闭区间就是[1, 2]
                同理第二个[3, 4] -> [3, 4) --> [3, 3]
                这样两个再通过线段树的判断, [1, 2]和[3, 3]显然是可以并排放置的
            -- 错误的解决方式: 一开始碰到这个问题的时候, 我想用开区间来解决, 即左开右开区间
                        这显然是错误的, 举一个反例, 对于上面的[3, 4]区间, 如果使用左开右开区间, 即(3, 4), 再转换成闭区间, 就变成了啥也没有的闭区间
                        这显然是错误的
    2. 题目中方块左端点的数据范围到了1e8, 方块的长度(高度)到了1e6, 如果直接用下标开线段树, 显然开不了这么大的空间
        -- 线段树的离散化
        在之前学习树状数组的时候, 就接触过离散化, 但是那个时候的离散化都比较显然, 那么这题为什么可以使用离散化, 或者说离散化之后为什么是正确的?
        考虑一下落下方块的过程, 当落下一个新的方块时, 我们会查询这个方块所在的x轴区间上的最大高度
        换句话说, 我们会比较当前这个方块的x轴区间, 和其他方块的x轴区间有没有交集
        实际上对于x轴的下标来说, 我们只涉及到比较操作, 而离散化不影响比较操作的正确性, 因此进行离散化不会影响答案的正确性

    3. 如何进行离散化? 
        在这之前, 我习惯的离散化方式是三叶姐那种的, 去重 + 排序 + map保存映射关系
        但是, 在灵神和左神的讲解中, 提到了另一种离散化方式, 去重 + 排序 + 二分查找离散化后的值
        在这题中, 我会将这两种方式都实现一下
    
    4. 本题中我们用到了将 闭区间转化为开区间, 然后再将开区间转化为闭区间, 以及用到了离散化
        需要注意的是, 原本的闭区间是[pos[0], pos[0] + pos[1]], 我们转化为了开区间, 最终转化成的闭区间是[pos[0], pos[0] + pos[1] - 1]
        需要注意的是, 在离散化的时候, 需要被离散化的值是 pos[0] + pos[1] - 1, 而不是pos[0] + pos[1]
        因为我们最终用到的值是pos[0] + pos[1]

        也就是说, 我们需要在离散化之前完成 - 1的操作, 而不是在调用getIndex()获得离散化后的值之后再 - 1
 */
public class LC699 {
    public List<Integer> fallingSquares(int[][] positions) {
        // 离散化, 第一种离散化方式
        // 不管是discretization1()还是discretization2(), 执行完之后, 都会将离散化前的值保存在list中
        discretization1(positions);
        // discretization2(positions);
        int n = list.size();
        SegmentTreeLazyResetQueryMax segTree = new SegmentTreeLazyResetQueryMax(n);
        List<Integer> ret = new ArrayList<>();
        for(int[] pos : positions){
            // 采用左开右闭区间, 转化为闭区间就是[left, right]
            int left = getIndex1(pos[0]), right = getIndex1(pos[0] + pos[1] - 1);
            // int left = getIndex2(pos[0]), right = getIndex2(pos[0] + pos[1] - 1);
            int max = segTree.query(1, 1, n, left, right);
            segTree.rangeReset(1, 1, n, left, right, max + pos[1]);
            ret.add(segTree.query(1, 1, n, 1, n));
        }
        return ret;
    }

    // 第一种离散化方式: 去重 + 排序 + 二分
    // return: 线段树下标的范围[1, n], 返回n
    private int discretization1(int[][] positions){
        // 用于去重的set
        Set<Integer> set = new HashSet<>();
        for(int[] pos : positions){
            int left = pos[0], length = pos[1];
            if(!set.contains(left)){
                set.add(left);
                list.add(left);
            }
            if(!set.contains(left + length - 1)){
                set.add(left + length - 1);
                list.add(left + length - 1);
            }
        }
        Collections.sort(list);
        return list.size();
    }

    // 保存第一种离散化排序后的值, 用于getIndex1中二分
    // 由于第二种离散化中也要排序, 因此discretization2()中也继续使用这个list
    private List<Integer> list = new ArrayList<>();
    
    // 获取经过第一种离散化过后的下标
    private int getIndex1(int idx){
        // 简单的二分
        int l = 0, r = list.size() - 1;
        while(l <= r){
            int mid = (l + r) >> 1;
            if(idx == list.get(mid)) return mid + 1;    // mid + 1为了保证线段树的下标从1开始
            if(idx < list.get(mid)) r = mid - 1;
            else l = mid + 1;
        }
        return -1;  // 其实不会走到这里, 在二分中一定能找到离散化前的值, 进而找到离散化后的下标
    }

    // 第二种离散化方式: 去重 + 排序 + map保存映射关系
    private void discretization2(int[][] positions){
        // 用于去重的set
        Set<Integer> set = new HashSet<>();
        for(int[] pos : positions){
            int left = pos[0], length = pos[1];
            if(!set.contains(left)){
                set.add(left);
                list.add(left);
            }
            if(!set.contains(left + length - 1)){
                set.add(left + length - 1);
                list.add(left + length - 1);
            }
        }
        Collections.sort(list);
        for(int i = 0;i < list.size();i++){
            map.put(list.get(i), i + 1);
        }
    }

    // <离散化前的值, 离散化后的值>
    private Map<Integer, Integer> map = new HashMap<>();

    // 获取经过第二种离散化过后的下标
    private int getIndex2(int idx){
        return map.get(idx);
    }
}

/**
线段树支持 范围重置 范围查询
维护区间最大值
 */
class SegmentTreeLazyResetQueryMax {
    private int[] max;
    private int[] todo;
    private boolean[] valid;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeLazyResetQueryMax(int n) {
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.max = new int[len];
        this.todo = new int[len];
        this.valid = new boolean[len];
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
        max[o] = resetVal;
        todo[o] = resetVal;
        valid[o] = true;
    }

    /**
        将父节点o上的lazy tag传递给(下发给)其左右子节点
     */
    private void spread(int o, int l, int r){
        if(valid[o]){
            // 如果当前节点的lazy tag有东西要向下传递
            int mid = (l + r) >> 1;
            do_(2 * o, l, mid, todo[o]);
            do_(2 * o + 1, mid + 1, r, todo[o]);
            valid[o] = false;
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
