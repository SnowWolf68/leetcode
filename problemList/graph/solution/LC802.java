package problemList.graph.solution;

import java.util.ArrayList;
import java.util.List;

/**
三色标记法
当发现有环(colors[nxt] == 1)时, 需要通过当前dfs的返回值将有环的信息传递给当前环上前面的所有节点
并且还要继续dfs当前节点的其余邻接点

这题也可以使用拓扑排序做, 排序完之后入度不为0的节点就是环上的节点, 拓扑排序完成后, 只需要将入度为0的节点添加到ret中即可
 */
public class LC802 {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < n;i++){
            for(int x : graph[i]){
                g[i].add(x);
            }
        }
        int[] colors = new int[n];
        boolean[] vis = new boolean[n];
        for(int i = 0;i < n;i++){
            if(!vis[i]) dfs(i, g, colors, vis);
        }
        List<Integer> ret = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(colors[i] != 1) ret.add(i);
        }
        return ret;
    }

    private boolean dfs(int i, List<Integer>[] g, int[] colors, boolean[] vis){
        colors[i]++;
        vis[i] = true;
        boolean flag = false;   // true: has cycle, false: does not have cycle
        for(int nxt : g[i]){
            if(colors[nxt] == 1) flag = true; 
            else if(!vis[nxt]) flag |= dfs(nxt, g, colors, vis);
        }
        if(!flag) colors[i]++;
        return flag;
    }
}
