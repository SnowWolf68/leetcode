package problemList.UnionFind.solution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
这题的难点在与一开始的分析

首先需要注意的是, 这题要求我们只要找到一个符合要求的子数组即可, 因此我们可以先来分析一下从哪里开始找, 更有可能找到符合要求的子数组?
注意到子数组需要满足的条件是: 子数组中的每个元素都要大于 threshold / k , 其中k是这个子数组的长度
那么我们自然想到, 如果我们从比较大的元素开始往两边拓展子数组, 显然是更容易满足 每个元素都大于 threshold / k 的这个条件的
因此我们可以首先对 nums 中的元素 带着下标排序, 并且从最大的元素开始向两边拓展子数组

并且注意到本题还有另外的一个性质: 对于子数组中每个元素应该满足的要求 threshold / k 来说, 其中threshold是固定的, 并且k是子数组的长度, 因此自然得到: 
子数组长度越长, 子数组中每个元素应该满足的条件 threshold / k 就越小
因此我们可以得到一个结论: 子数组的长度越长, 子数组就越有可能满足要求

因此当我们从大到小遍历nums中的元素, 每遍历到某一个元素时, 都要看看这个元素两边是否有之前遍历过的元素 (之前已经产生的子数组)
如果有的话, 根据上面分析的结论, 此时将这个元素和其左右的子数组合并, 合并得到的子数组更有可能满足要求

上面这个合并的操作可以使用并查集来解决, 并且并查集还需要额外维护 联通块中的最小值 以及 联通块中的元素个数 这两个信息
在维护了这两个信息之后, 就能够直接判断并查集维护的某一个联通块是否符合要求, 即判断 threshold / k 和 minimum 之间的关系即可, 如果当前子数组中的最小值都大于 threshold / k , 那么这个子数组显然是符合要求的

并且在并查集合并的过程中, 联通块的最小值 和 联通块中的元素个数 也是可以在 O(1) 的时间内计算得到的

注: 这里的并查集, 确切来说, 用的是 数组上的并查集, 即每次只需要向右合并即可

 */
public class LC2334 {
    class UnionFind {
        public int[] pa;
        public int[] size;
        public int[] min;
    
        UnionFind(int n){
            pa = new int[n];
            size = new int[n];
            min = new int[n];
            for(int i = 0;i < n;i++){
                pa[i] = i;
                min[i] = Integer.MAX_VALUE;
            }
        }
        public int find(int x){
            if(pa[x] != x){
                pa[x] = find(pa[x]);
            }
            return pa[x];
        }
    }

    public int validSubarraySize(int[] nums, int threshold) {
        int n = nums.length;
        UnionFind uf = new UnionFind(n + 1);
        // [nums[i], i]
        List<int[]> list = new ArrayList<>();
        for(int i = 0;i < n;i++) list.add(new int[]{nums[i], i});
        Collections.sort(list, (o1, o2) -> o2[0] - o1[0]);
        for(int[] cur : list){
            int num = cur[0], idx = cur[1], fa = uf.find(idx + 1);
            uf.pa[idx] = fa;
            uf.size[fa] += uf.size[idx] + 1;
            uf.min[fa] = Math.min(uf.min[fa], Math.min(num, uf.min[idx]));
            if(uf.min[fa] > threshold / uf.size[fa]){
                return uf.size[fa];
            }
        }
        return -1;
    }
}
