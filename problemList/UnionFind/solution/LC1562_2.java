package problemList.UnionFind.solution;

/**
每次只需向右合并
 */
public class LC1562_2 {
    class UnionFind {
        public int[] pa;
        public long[] size;
    
        UnionFind(int n){
            pa = new int[n];
            size = new long[n];
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
    
    public int findLatestStep(int[] arr, int m) {
        int n = arr.length;
        UnionFind uf = new UnionFind(n + 1);
        boolean flag = false;
        int cnt = 0, ret = -1;
        for(int i = 0;i < n;i++){
            int x = arr[i] - 1;
            int fa = uf.find(x + 1);
            uf.pa[x] = fa;
            cnt -= (uf.size[x] == m ? 1 : 0) + (uf.size[fa] == m ? 1 : 0);
            uf.size[fa] += uf.size[x] + 1;
            if(uf.size[fa] == m){
                flag = true;
                cnt++;
            }
            if(cnt == 0 && flag){
                ret = i;
                flag = false;
            }
        }
        return cnt == 0 ? ret : n;
    }
}
