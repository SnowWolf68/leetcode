package problemList.graph.solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LC3543_2 {
    public int maxWeight(int n, int[][] edges, int k, int t) {
        List<int[]>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        int[] in = new int[n];
        for(int[] e : edges){
            g[e[0]].add(new int[]{e[1], e[2]});
            in[e[1]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0;i < n;i++){
            if(in[i] == 0) queue.offer(i);
        }
        int[][] dp = new int[n][k + 1];
        int ret = 0;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int[] nxtArr : g[poll]){
                int nxt = nxtArr[0], w = nxtArr[1];
                for(int j = 1;j <= k;j++){
                    if(dp[poll][j - 1] + w < t){
                        dp[nxt][j] = Math.max(dp[nxt][j], dp[poll][j - 1] + w);
                        ret = Math.max(ret, dp[nxt][j]);
                    }
                }
            }
        }
        return ret;
    }
}
