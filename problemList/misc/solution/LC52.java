package problemList.misc.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LC52 {
    private int cnt = 0;
    private int n;
    private List<Integer> path = new ArrayList<>();
    public int totalNQueens(int n) {
        this.n = n;
        boolean[] vis = new boolean[n];
        dfs(0, vis);
        return cnt;
    }

    private void dfs(int i, boolean[] vis){
        if(i == n){
            cnt++;
            return;
        }
        for(int j = 0;j < n;j++){
            if(vis[j] || !check(path, i, j)) continue;
            path.add(j);
            vis[j] = true;
            dfs(i + 1, vis);
            path.remove(path.size() - 1);
            vis[j] = false;
        }
    }

    private boolean check(List<Integer> path, int i, int j){
        Set<Integer> set1 = new HashSet<>();    // abs(x1 - y1)
        Set<Integer> set2 = new HashSet<>();    // x1 + y1
        set1.add(i - j);
        set2.add(i + j);
        for(int k = 0;k < path.size();k++){
            int sub = k - path.get(k), sum = k + path.get(k);
            if(set1.contains(sub)) return false;
            set1.add(sub);
            if(set2.contains(sum)) return false;
            set2.add(sum);
        }
        return true;
    }
}
