package problemList.UnionFind.solution;

/**
每次向右合并, 并且把连通块的元素和记录在 find(i) 中 (即记录在当前连通块的根节点上)
 */
public class LC2382 {
    class UnionFind {
        public int[] pa;
        public long[] sum;
    
        UnionFind(int n){
            pa = new int[n];
            sum = new long[n];
            for(int i = 0;i < n;i++){
                pa[i] = i;
            }
        }

        public int find(int x){
            if(pa[x] != x){
                pa[x] = find(pa[x]);
            }
            return pa[x];
        }
    }
    public long[] maximumSegmentSum(int[] nums, int[] removeQueries) {
        int n = nums.length, m = removeQueries.length;
        UnionFind uf = new UnionFind(n + 1);    // 为了避免向右合并可能的越界, 这里多开一个节点
        long[] ret = new long[m];
        for(int i = m - 1;i >= 1;i--){
            int fa = uf.find(removeQueries[i] + 1);
            uf.pa[removeQueries[i]] = fa;
            uf.sum[fa] += nums[removeQueries[i]] + uf.sum[removeQueries[i]];
            ret[i - 1] = Math.max(ret[i], uf.sum[fa]);
        }
        return ret;
    }
}
