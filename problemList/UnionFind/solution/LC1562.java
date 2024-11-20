package problemList.UnionFind.solution;

/**
使用并查集维护这些1, 同时维护每个联通块的大小

ps: 这部分代码写的很不优雅, 但是我看题解区有比较简洁的写法, TODO: 学习一下题解区更优雅的解法
 */
public class LC1562 {
    public class UnionFind {
        public int[] pa;
        public int[] size;

        UnionFind(int n){
            pa = new int[n];
            size = new int[n];
            for(int i = 0;i < n;i++){
                pa[i] = i;
                size[i] = 1;
            }
        }

        public int find(int x){
            if(pa[x] != x){
                pa[x] = find(pa[x]);
            }
            return pa[x];
        }
    
        public boolean isSameSet(int x, int y){
            return find(x) == find(y);
        }
    
        public void union(int x, int y){
            int px = find(x), py = find(y);
            if(size[px] >= size[py]){
                pa[py] = px;
                size[px] += size[py];
            }else{
                pa[px] = py;
                size[py] += size[px];
            }
        }
    }
    
    public int findLatestStep(int[] arr, int m) {
        int n = arr.length, cnt = 0;    // cnt: 1的个数为m的组数
        UnionFind uf = new UnionFind(n);
        boolean[] vis = new boolean[n]; // 标记某一位是否填了1
        boolean flag = false;
        int ret = -1;
        for(int i = 0;i < n;i++){
            int idx = arr[i] - 1;
            vis[idx] = true;
            if(idx + 1 < n && idx - 1 >= 0){
                if(vis[idx + 1] && vis[idx - 1]){
                    int lf = uf.find(idx - 1), rf = uf.find(idx + 1);
                    if(uf.size[lf] == m) cnt--;
                    if(uf.size[rf] == m) cnt--;
                    if(uf.size[lf] + uf.size[rf] + 1 == m){
                        flag = true;
                        cnt++;
                    }
                    uf.union(idx, lf);
                    uf.union(idx, rf);
                }else if(vis[idx + 1]){
                    int rf = uf.find(idx + 1);
                    if(uf.size[rf] == m) cnt--;
                    if(uf.size[rf] + 1 == m){
                        flag = true;
                        cnt++;
                    }
                    uf.union(idx, rf);
                }else if(vis[idx - 1]){
                    int lf = uf.find(idx - 1);
                    if(uf.size[lf] == m) cnt--;
                    if(uf.size[lf] + 1 == m){
                        flag = true;
                        cnt++;
                    }
                    uf.union(idx, lf);
                }else{
                    if(m == 1){
                        flag = true;
                        cnt++;
                    }
                }
            }else if(idx + 1 < n){
                if(vis[idx + 1]){
                    int rf = uf.find(idx + 1);
                    if(uf.size[rf] == m){
                        cnt--;
                    }
                    if(uf.size[rf] + 1 == m){
                        flag = true;
                        cnt++;
                    }
                    uf.union(idx, rf);
                }else{
                    if(m == 1){
                        flag = true;
                        cnt++;
                    }
                }
            }else if(idx - 1 >= 0){
                if(vis[idx - 1]){
                    int lf = uf.find(idx - 1);
                    if(uf.size[lf] == m){
                        cnt--;
                    }
                    if(uf.size[lf] + 1 == m){
                        flag = true;
                        cnt++;
                    }
                    uf.union(idx, lf);
                }else{
                    if(m == 1){
                        flag = true;
                        cnt++;
                    }
                }
            }else{
                if(m == 1){
                    flag = true;
                    cnt++;
                }
            }
            if(flag && cnt == 0){
                ret = i;
                flag = false;
            }
        }
        return ret != -1 ? ret : (cnt == 0 ? -1 : n);
    }
}
