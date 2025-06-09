package problemList.graph.solution;

import java.util.HashSet;
import java.util.Set;

/**
使用Set优化第三个维度
 */
public class LC3543_3 {
    public int maxWeight(int n, int[][] edges, int k, int t) {
        // boolean[][][] dp = new boolean[k + 1][n][t];
        Set<Integer>[][] dp = new Set[k + 1][n];
        for(int i = 0;i <= k;i++){
            for(int j = 0;j < n;j++){
                dp[i][j] = new HashSet<>();
            }
        }
        // for(int i = 0;i < n;i++) dp[0][i][0] = true;
        for(int i = 0;i < n;i++) dp[0][i].add(0);
        for(int j = 1;j <= k;j++){
            for(int[] e : edges){
                // for(int w = 0;w < t;w++){
                //     if(w - e[2] >= 0) dp[j][e[1]][w] |= dp[j - 1][e[0]][w - e[2]];
                // }
                for(int w : dp[j - 1][e[0]]){
                    if(w + e[2] < t) dp[j][e[1]].add(w + e[2]);
                }
            }
        }
        int ret = -1;
        for(int i = 0;i < n;i++){
            // for(int j = 0;j < t;j++){
            //     if(dp[k][i][j] == true) ret = Math.max(ret, j);
            // }
            for(int w : dp[k][i]){
                ret = Math.max(ret, w);
            }
        }
        return ret;
    }
}
