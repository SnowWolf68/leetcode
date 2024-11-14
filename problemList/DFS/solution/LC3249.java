package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.List;

/**
很简单的一个计算子树大小的dfs
 */
public class LC3249 {
    private List<Integer>[] g;
    private int ret = 0;
    public int countGoodNodes(int[][] edges) {
        int n = edges.length;
        g = new List[n + 1];
        for(int i = 0;i <= n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(0, -1);
        return ret;
    }

    // return: 子树大小
    private int dfs(int i, int pa){
        int subSize = -1, retSize = 0;
        boolean isEqual = true;
        for(int nxt : g[i]){
            if(nxt != pa){
                int size = dfs(nxt, i);
                retSize += size;
                if(subSize == -1){
                    subSize = size;
                }else{
                    if(subSize != size){
                        isEqual = false;
                    }
                }
            }
        }
        if(isEqual) ret++;
        return retSize + 1;
    }
}
