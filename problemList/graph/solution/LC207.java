package problemList.graph.solution;

import java.util.ArrayList;
import java.util.List;

/**
三色标记法判环, 参考灵神题解: https://leetcode.cn/problems/course-schedule/solutions/2992884/san-se-biao-ji-fa-pythonjavacgojsrust-by-pll7/
 */
public class LC207 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int n = numCourses;
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : prerequisites){
            g[e[0]].add(e[1]);
        }
        int[] colors = new int[n];
        boolean flag = true;
        boolean[] vis = new boolean[n];
        for(int i = 0;i < n;i++){
            if(!vis[i] && !dfs(i, g, colors, vis)){
                flag = false;
                break;
            }
        }
        return flag;
    }

    // return false: have cycle, true: don't have cycle
    private boolean dfs(int i, List<Integer>[] g, int[] colors, boolean[] vis){
        colors[i]++;
        vis[i] = true;
        for(int nxt : g[i]){
            if(colors[nxt] == 1 || (!vis[nxt] && !dfs(nxt, g, colors, vis))) return false;
        }
        colors[i]++;
        return true;
    }
}
