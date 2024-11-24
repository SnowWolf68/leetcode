package problemList.unsorted;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
手玩示例的重要性

对于示例一: nums = [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
定义合法区间为: 对于nums中的每一个集合, 都至少有一个元素在这个区间中, 那么就定义这个区间叫合法区间
考虑这样的一个问题: 给定合法区间的左端点, 如何求此时合法区间的右端点? 
    这里不妨假设区间左端点是4, 那么怎么求此时的合法区间的长度?
    由于对于nums中的每一个集合来说, 其内部都是 非递减排列 的, 因此我们只需要遍历剩下的几个集合, 找到这几个集合中第一个满足 >= 4 的元素, 
    这几个元素中的最大值就是当前 以4为合法区间左端点 对应的右端点
    并且这样找到的合法区间, 一定是 以 4 为区间左端点 的合法区间中, 长度最短的一个

通过上面的例子的启发, 可以想到如下的解法: 
因此我们可以定义 n 个指针, 分别指向nums中的n个集合的第一个元素, 这里显然 n = nums.length
首先取这n个指针指向的元素的最小值作为 合法区间的左端点
然后剩下这n - 1个指针指向的元素的最大值, 就是当前这个区间的右端点

接下来让此时左端点的指针++, 然后继续在这n个指针指向的元素中挑一个最小的, 作为下一步的左端点, 然后再求此时的右端点, 进而求得此时的区间长度
以此类推...

注意题目中规定的区间的比较要求: 对于长度相等的区间, 此时比较的是区间的左端点的大小, 左端点小的区间就小
因此对于上面的遍历左端点来说, 此时对于区间长度相等的合法区间, 此时我们只需要取第一个找到的区间, 就是左端点最小的区间

这里还有一个细节, 就是如何快速找到n个指针指向的元素中的最小值(作为左端点), 以及最大值(作为右端点)?
一种自然的想法是使用两个堆分别维护 最小值 和 最大值, 但是实际上真的需要两个堆吗?
实际上只需要使用一个堆维护最小值即可, 因为对于最小值来说, 每当某一个指针++的时候, 我们都需要马上知道在接下来的这些元素中的最小值以及对应的指针是多少
但是对于右端点来说, 由于此时维护的是最大值, 而在指针 ++ 的时候, 最大值的位置只有两种可能: 一是之前的最大值, 二是指针++之后指向的位置的元素
因此最大值可以直接使用一个变量来维护, 而最小值需要使用一个最小堆来维护

还要多说一句, 由于这里实际上是一个 多序列 多指针 的做法, 那么什么时候退出循环?
    如果从循环次数的角度出发考虑, 那么这里的分析会比较复杂, 因此我们可以从指针移动的角度出发考虑
    从指针移动的角度出发, 这里实际上就是要判断: 什么时候指针不能再移动?
    那么显然 如果此时我们的左端点的指针已经移动到对应的 集合的最右端点, 那么此时显然这个左指针不能再移动了, 因此此时就需要退出循环

TODO: 这题还有滑窗的写法, 参见灵神题解
 */
public class LC632_1 {
    public int[] smallestRange(List<List<Integer>> nums) {
        int n = nums.size();
        // [i, j, nums.get(i).get(j)]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        int max = nums.get(0).get(0);
        for(int i = 0;i < n;i++) {
            minHeap.offer(new int[]{i, 0, nums.get(i).get(0)});
            max = Math.max(max, nums.get(i).get(0));
        }
        int ret = -1, retL = -1, retR = -1;     // 最短合法区间的长度, 对应的左端点
        while(minHeap.peek()[1] < nums.get(minHeap.peek()[0]).size() - 1){
            int[] curL = minHeap.poll();
            minHeap.offer(new int[]{curL[0], curL[1] + 1, nums.get(curL[0]).get(curL[1] + 1)});
            int curLen = max - curL[2] + 1;
            if(ret == -1 || curLen < ret){
                ret = curLen;
                retL = curL[2];
                retR = max;
            }
            max = Math.max(max, nums.get(curL[0]).get(curL[1] + 1));
        }
        int[] curL = minHeap.poll();
        int curLen = max - curL[2] + 1;
        if(ret == -1 || curLen < ret){
            ret = curLen;
            retL = curL[2];
            retR = max;
        }
        return new int[]{retL, retR};
    }
}
