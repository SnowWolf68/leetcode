package problemList.SegmentTree.solution;

import java.util.Arrays;

/**
在上一个解法中, 我们可以看出来, 其中比较花费时间的操作是: 
遍历distributed数组在[task[0], task[1]]的区间, 统计1的个数
这可以看做是一个 区间查询 操作
但是, 对于distributed数组的更新操作, 由于我们无法在O(1)的时间内确定[task[0], task[1]]范围内有哪些时间点是没有被分配的
而是需要从后往前依次遍历, 找到未被分配的时间点再更新
因此不能直接看作是线段树的区间更新操作


 */
public class LC2589_2_SegmentTree {
    public int findMinimumTime(int[][] tasks) {
        Arrays.sort(tasks, (o1, o2) -> o1[1] - o2[1]);
        int n = tasks[tasks.length - 1][1] + 1;
        int ret = 0;
        SegmentTreeLazy segTree = new SegmentTreeLazy(n);
        for(int i = 0;i < tasks.length;i++){
            int[] task = tasks[i];
            int from = task[0], to = task[1], duration = task[2];
            int curCnt = segTree.query(1, 1, n, from, to);
            if(curCnt < duration){
                // 先线段树二分找到要添加运行时间点的区间的左端点left
                segTree.bisectLeft()
                // 然后将[left, to]区间内未运行的时间点都添加为运行时间点

            }
        }
        return ret;
    }

    public class SegmentTreeLazy {
        private int[] sum;
        private int[] todo;
    
        // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
        SegmentTreeLazy(int n) {
            int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
            this.sum = new int[len];
            this.todo = new int[len];
        }
    
        // o, l, r: 当前节点以及当前区间的左右端点
        private void build(int o, int l, int r){
            if(l == r){
                // 递归到叶子结点
                // TODO 更新...
                return;
            }
            // 划分区间, 继续递归左右子区间
            int mid = (l + r) >> 1;
            build(2 * o, l, mid);
            build(2 * o + 1, mid + 1, r);
            // TODO 维护...
        }
    
        // o, l, r: 当前节点以及当前区间左右端点    调用入口: o, l, r = 1, 1, n
        // L, R, add: 要更新的区间范围, 以及要更新的区间内 每个元素 要增加的值
        private void add(int o, int l, int r, int L, int R, int add){
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
            sum[o] = sum[2 * o] + sum[2 * o + 1];
        }
    
        private void do_(int o, int l, int r, int add){
            sum[o] += add * (r - l + 1);
            todo[o] += add;
        }
    
        private void spread(int o, int l, int r){
            if(todo[o] != 0){
                // 如果当前节点的lazy tag有东西要向下传递
                int mid = (l + r) >> 1;
                do_(2 * o, l, mid, todo[o]);
                do_(2 * o + 1, mid + 1, r, todo[o]);
                // todo[2 * o] += todo[o];
                // todo[2 * o + 1] += todo[o];
                todo[o] = 0;
            }
        }
    
        // o, l, r: 当前节点, 当前区间范围, 调用入口: o, l, r = 1, 1, n
        // L, R: 要查询的区间范围[L, R]
        private int query(int o, int l, int r, int L, int R){
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

        /**
         * o, l, r: 当前节点及其区间范围, 入口: o, l, r = 1, 1, n
         * L, R: 要查找的下标的区间范围
         * return: [L, R]区间内第一个满足sum[i, R] >= val的下标
         */
        private int bisectLeftSumGe(int o, int l, int r, int L, int R, int val){
            
        }
    }
    
}
