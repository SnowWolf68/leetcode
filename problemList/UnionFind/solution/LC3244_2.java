package problemList.UnionFind.solution;

/**
由于添加的道路不会有交叉, 并且有 "遇到捷径就走捷径" 的贪心策略, 因此我们也可以直接维护跳转位置
具体来说: 
    使用nxt[]来维护每一个节点的下一次跳转位置, 即 nxt[i] 表示节点i的下一次跳转的位置为 nxt[i]
    对于每一个queries[i], 我们都需要更新[queries[i][0], queries[i][1]]区间的nxt[]
    如何更新?   假设这里 l = queries[i][0], r = queries[i][1], 为了简化下面的表示
        1. 首先如果当前区间 [l, r] 已经被更大的区间 [L, R] 包含, 也就是有一条范围更大的捷径 [L, R] 包含了当前添加的这条捷径的区间 [l, r]
            那么此时显然我们会走更大的那一条捷径, 因此此时我们不需要更新nxt[], 也不需要更新cnt  (因为在添加更大的那一条捷径 [L, R] 时, 当前[l, r]区间已经被更新过了)
            那么如何表示当前节点i被更大的捷径包含? 我们规定, nxt[i] = 0 表示i这个节点被更大的捷径包含
            因此这里我们需要判断: 如果 nxt[l] == 0, 说明l这个节点已经被更大的区间包含了, 而由于本题的捷径不会交叉, 因此当前整个 [l, r] 都已经被更大的捷径包含了, 因此此时不需要更新
        2. 如果当前区间没有被更大的区间包含, 那么判断此时节点l能跳转到哪里, 即判断 nxt[l] 和 r 的关系
                为什么要判断?
                    因为虽然添加的捷径不会有交叉, 但是有可能出现两条捷径的起点相同, 但终点不同的情况
                    如果之前添加过一条起点为l, 但终点 r' > r 的捷径, 那么当前这条捷径 [l, r] 显然也没有必要添加
            因此判断如果 nxt[l] >= r , 说明此时当前这条捷径没必要添加, 继续下一次循环
            
            如果 0 < nxt[l] < r, 说明当前这条捷径有必要添加
            如何更新 [l, r] 区间的nxt[]值, 以及如何更新cnt? 
            这里我们知道, nxt[i] == 0表示i这个节点被更大的捷径包含, 而由于要更新的 [l, r] 区间中, 有可能有一些更小的捷径, 对于这些捷径[l', r']来说, 其nxt[l']就表示这个捷径的结束下标
            因此我们可以利用 nxt[i] 一直往后跳到下一条捷径, 并且更新所有的 nxt[i] = 0 即可
    这样说可能会比较抽象, 看看下面的代码就懂了

也可以用链表的思想来理解这个过程
 */
public class LC3244_2 {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        int m = queries.length, cnt = n - 1;
        int[] ret = new int[m], nxt = new int[n];
        for(int i = 0;i < n;i++) nxt[i] = i + 1;
        for(int i = 0;i < m;i++){
            int l = queries[i][0], r = queries[i][1];
            if(0 < nxt[l] && nxt[l] < r){
                int j = nxt[l];
                while(j < r){
                    cnt--;
                    int nxtJ = nxt[j];
                    nxt[j] = 0;
                    j = nxtJ;
                }
                nxt[l] = r;
            }
            ret[i] = cnt;
        }
        return ret;
    }
}
