package problemList.dp.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
考虑将根节点从 i 换到 nxt 时, 边翻转次数会发生怎样的变化: 
    1. 如果 i 与 nxt 之间的边的方向是 i -> nxt: 那么从i换到nxt, 需要多翻转一条边
    2. 如果 i 与 nxt 之间的边的方向是 nxt -> i: 那么从i换到nxt, 可以少翻转一条边
因此我们可以首先进行一次dfs, 计算出以0为根的情况下, 最少需要翻转多少条边, 然后依次换根到其余所有节点, 即可得到答案

如何统计以0为根时, 此时需要翻转多少条边?
首先按照无向边建图, 然后进行dfs, 对于每次dfs, 如果 i -> nxt 这条有向边不在edges中, 那么需要翻转的边数++
 */
public class LC2858 {
    private List<Integer>[] g;
    private Map<Integer, Set<Integer>> map = new HashMap<>();
    private int[] ans;
    public int[] minEdgeReversals(int n, int[][] edges) {
        g = new List[n];
        ans = new int[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
            Set<Integer> set = map.getOrDefault(e[0], new HashSet<>());
            set.add(e[1]);
            map.put(e[0], set);
        }
        ans[0] = dfs(0, -1);
        reroot(0, -1);
        return ans;
    }

    private int dfs(int i, int pa){
        int cnt = 0;
        for(int nxt : g[i]){
            if(nxt != pa){
                if(!map.getOrDefault(i, new HashSet<>()).contains(nxt)) cnt += dfs(nxt, i) + 1;
                else cnt += dfs(nxt, i);
            }
        }
        return cnt;
    }

    private void reroot(int i, int pa){
        for(int nxt : g[i]){
            if(nxt != pa){
                // 换根: 从i换到nxt
                if(map.getOrDefault(i, new HashSet<>()).contains(nxt)){     // i -> nxt
                    ans[nxt] = ans[i] + 1;
                }else{
                    ans[nxt] = ans[i] - 1;
                }
                reroot(nxt, i);
            }
        }
    }
}
