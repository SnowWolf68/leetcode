package problemList.graph.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
灵神的记忆化做法: 将三个状态压缩到一个mask中, 每一个状态用10bit表示
使用set记忆mask即可

时间复杂度: 
    以每一个点为起点的dfs的时间复杂度最差是 O(n + m) (最差就是遍历整张图) (m为边数)
    因此总的复杂度为: O(n * (m + n))
 */
public class LC3543_1 {
    private int ret = -1;
    private List<int[]>[] g;
    private int k, t;
    private Set<Integer> set = new HashSet<>();
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
        for(int i = 0;i < n;i++){
            dfs(i, 0, 0);
        }
        return ret;
    }

    // 当前节点编号, 当前走过的边数, 当前路径上的边权和
    private void dfs(int i, int curCnt, int sum){
        int mask = i << 20 | curCnt << 10 | sum;
        if(set.contains(mask)) return;
        set.add(mask);
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
