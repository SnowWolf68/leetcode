package problemList.slidingWindow.solution;

/**
这题很有意思, 在做这题之前, 需要先做 LC3258 , 首先要对这个滑窗很熟悉才行
首先看数据范围, 本题的数据范围要求: 1 <= s.length <= 1e5, 1 <= queries.length <= 1e5
因此显然只能写一个O(n)或O(n * logn)的算法

这里先回顾一下LC3258这题, 考虑一下这题的滑窗能够给我们提供什么信息?
对于[left, right]的这个滑窗来说, 由于滑窗的性质, 我们可以知道, 在while循环更新完left之后, 此时的left, 一定是以right为结尾元素的符合要求的子串的最左端的下标
换句话说, 此时的[left, right]区间的 所有 子串, 都是符合要求的子串

假设我们将所有的这种信息都存储在一个数组left[]中, 其中left[i]表示以i下标为右端点的符合要求的子串的最左端的下标
显然, left[]数组我们可以在一次O(n)的滑窗中计算得到

接下来考虑如何利用left[]数组提供的信息处理所有的查询queries[]

 
 */
public class LC3261 {
    public long[] countKConstraintSubstrings(String s, int k, int[][] queries) {
        
    }
}
