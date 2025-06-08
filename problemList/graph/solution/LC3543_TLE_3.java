package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
dp[i][j][w] 表示: 以i结尾, 长度为j, 权值和为w的路径是否存在
dp[i][j][w]: 刷表法
    对于i -> nxt: 
        for j in (1, k + 1):
            for w in (0, t):
                if(w - weight >= 0) dp[nxt][j][w] |= dp[i][j - 1][w - weight(i, nxt)]

但是会TLE, 时间复杂度: 
    拓扑排序O(n + E), 每次拓扑排序内部进行的两层循环: O(k * t)
    因此总的时间复杂度: O((n + E) * k * t), 对于本题来说, 最坏可以到 300 * 300 * 600, 确实会T
 */
public class LC3543_TLE_3 {
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
        boolean[][][] dp = new boolean[n][k + 1][t];
        for(int i = 0;i < n;i++) dp[i][0][0] = true;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int[] nxtArr : g[poll]){
                int nxt = nxtArr[0], weight = nxtArr[1];
                for(int j = 1;j <= k;j++){
                    for(int w = 0;w < t;w++){
                        if(w - weight >= 0) dp[nxt][j][w] |= dp[poll][j - 1][w - weight];
                    }
                }
                if(--in[nxt] == 0) queue.offer(nxt);
            }
        }
        int ret = -1;
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < t;j++){
                if(dp[i][k][j]) ret = Math.max(ret, j);
            }
        }
        return ret;
    }
}
