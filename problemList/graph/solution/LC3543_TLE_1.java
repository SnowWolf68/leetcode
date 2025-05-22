package problemList.graph.solution;

import java.util.ArrayList;
import java.util.List;

/*
暴力dfs

时间复杂度:  O(n * (d ^ k))   其中d为某个节点的最大出度
    
 */
public class LC3543_TLE_1 {
    private int ret = -1;
    private List<int[]>[] g;
    private int k, t;
    public int maxWeight(int n, int[][] edges, int _k, int _t) {
        this.k = _k;
        this.t = _t;
        g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
        }
        for(int i = 0;i < n;i++){
            dfs(i, 0, 0);
        }
        return ret;
    }

    // 当前节点编号, 当前走过的边数, 当前路径上的边权和
    private void dfs(int i, int curCnt, int sum){
        if(curCnt == k){
            if(sum < t) ret = Math.max(sum, ret);
            return;
        }
        for(int[] nxt : g[i]){
            if(sum + nxt[1] < t){
                dfs(nxt[0], curCnt + 1, sum + nxt[1]);
            }
        }
    }
}
