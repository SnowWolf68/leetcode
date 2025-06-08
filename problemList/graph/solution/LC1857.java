package problemList.graph.solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
dp[i][j] 表示 以i结尾的路径中, j出现的最多次数
刷表法计算, 假设有 i -> nxt : 
    for j in (0, 26):
        dp[nxt][j] = max(dp[nxt][j], dp[i][j] + (nxtColor == j ? 1 : 0));
return max(dp[i][j])
 */
public class LC1857 {
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        int[] in = new int[n];
        for(int[] e : edges){
            in[e[1]]++;
            g[e[0]].add(e[1]);
        }
        // 正拓扑序
        Queue<Integer> queue = new LinkedList<>();
        int[][] dp = new int[n][26];
        int ret = 0;
        for(int i = 0;i < n;i++){
            if(in[i] == 0) {
                queue.offer(i);
                dp[i][colors.charAt(i) - 'a']++;
                ret = Math.max(ret, dp[i][colors.charAt(i) - 'a']);
            }
        }
        if(queue.isEmpty()) return -1;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int nxt : g[poll]){
                int nxtColor = colors.charAt(nxt) - 'a';
                for(int j = 0;j < 26;j++){
                    dp[nxt][j] = Math.max(dp[nxt][j], dp[poll][j] + (nxtColor == j ? 1 : 0));
                    ret = Math.max(ret, dp[nxt][j]);
                }
                if(--in[nxt] == 0) queue.offer(nxt);
            }
        }
        for(int i = 0;i < n;i++){
            if(in[i] != 0) return -1;
        }
        return ret;
    }
}
