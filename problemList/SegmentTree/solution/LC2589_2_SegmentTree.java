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
}
