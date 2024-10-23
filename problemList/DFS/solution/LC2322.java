package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.List;

public class LC2322 {
    private int cnt = 0;    // 全局时间戳
    private int[] xr;       // 以某个节点为根的子树的异或值
    private int[] in;       // in[i]: 进入i节点的时间
    private int[] out;      // out[i]: 离开i节点的时间
    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        xr = new int[n];
        in = new int[n];
        out = new int[n];
        List<Integer>[] g = new List[n - 1];
        for(int i = 0;i < n - 1;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(nums, g, 0);     // 题目中也没有说根节点是谁, 这里假设从0开始
        for(int i = 0;i < n - 1;i++){
            for(int j = i + 1;j < n - 1;j++){
                int x = edges[i][0], y = edges[j][0];
                if(in[x] <= in[y] && out[y] <= out[x]){
                    // x是y的父节点
                    int xor1 = 
                }else if(in[y] <= in[x] && out[x] <= out[y]){

                }else{

                }
            }
        }
    }

    private int dfs(int[] nums, List<Integer>[] g, int i) {
        in[i] = cnt++;
        int curXr = nums[i];
        for(int nxt : g[i]){
            curXr ^= dfs(nums, g, nxt);
        }
        out[i] = cnt;
        xr[i] = curXr;
        return curXr;
    }
    
}
