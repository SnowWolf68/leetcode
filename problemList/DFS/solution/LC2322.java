package problemList.DFS.solution;

import java.util.ArrayList;
import java.util.List;

/**
这题的解析我单独放在了 DFS/notes/DFS时间戳.md 中
 */
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
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int[] e : edges){
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        dfs(nums, g, 0, -1);     // 题目中也没有说根节点是谁, 这里假设从0开始
        // 预处理一下edges, 保证edges[i][0]一定是edges[i][1]的父节点
        for(int[] e : edges){
            if(!isParent(e[0], e[1])){
                swap(e, 0, 1);
            }
        }
        int ret = Integer.MAX_VALUE;
        for(int i = 0;i < n - 1;i++){
            for(int j = i + 1;j < n - 1;j++){
                // x1, y1: 第一条边连接的两个点, 并且x1是y1的父节点
                // x2, y2: 第二条边连接的两个点, 并且x2是y2的父节点
                int x1 = edges[i][0], y1 = edges[i][1], x2 = edges[j][0], y2 = edges[j][1];
                int xor1 = 0, xor2 = 0, xor3 = 0, min = 0, max = 0;
                // 注意: isParent(x, y) 中x和y的顺序只能按照下面的顺序来判断, 不能任意改动
                if(isParent(y1, x2)){
                    xor1 = xr[y2]; xor2 = xr[y1] ^ xr[y2]; xor3 = xr[0] ^ xr[y1];
                }else if(isParent(y2, x1)){
                    xor1 = xr[y1]; xor2 = xr[y2] ^ xr[y1]; xor3 = xr[0] ^ xr[y2];
                }else{
                    xor1 = xr[y1]; xor2 = xr[y2]; xor3 = xr[0] ^ xr[y1] ^ xr[y2];
                }
                max = Math.max(xor1, Math.max(xor2, xor3));
                min = Math.min(xor1, Math.min(xor2, xor3));
                ret = Math.min(ret, max - min);
            }
        }
        return ret;
    }

    private boolean isParent(int x, int y){
        return in[x] <= in[y] && out[y] <= out[x];
    }

    private void swap(int[] arr, int idx1, int idx2){
        int t = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = t;
    }

    private int dfs(int[] nums, List<Integer>[] g, int i, int pa) {
        in[i] = cnt++;
        int curXr = nums[i];
        for(int nxt : g[i]){
            if(nxt != pa){
                curXr ^= dfs(nums, g, nxt, i);
            }
        }
        out[i] = cnt;
        xr[i] = curXr;
        return curXr;
    }
}
