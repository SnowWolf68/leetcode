package problemList.unsorted;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
避免重复计算某些位置

这题甚至不需要记忆化搜索, 只需要避免重复访问计算过的位置即可

之所以不用记忆化, 是因为这里dfs并没有返回值, 因此我们只需要避免参数完全相同的dfs重复计算即可
因此可以使用一个vis来记录当前计算过哪些参数的dfs, 当后续dfs到之前计算过的参数时, 直接跳过即可

但是这种使用三维vis进行记忆化的方式还是会超时
 */
public class LC3543_TLE_2 {
    private int ret = -1;
    private List<int[]>[] g;
    private int k, t;
    private boolean[][][] vis;
    public int maxWeight(int n, int[][] edges, int _k, int _t) {
        this.k = _k;
        this.t = _t;
        g = new List[n];
        int sumWeight = 0;
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
            sumWeight += e[2];
        }
        vis = new boolean[n][edges.length + 1][sumWeight + 1];
        for(int i = 0;i < n;i++){
            dfs(i, 0, 0);
        }
        return ret;
    }

    // 当前节点编号, 当前走过的边数, 当前路径上的边权和
    private void dfs(int i, int curCnt, int sum){
        if(vis[i][curCnt][sum]) return;
        vis[i][curCnt][sum] = true;
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
