package problemList.simple;

import java.util.ArrayList;
import java.util.List;

public class LC3243 {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        int[] ret = new int[queries.length];
        int INF = 0x3f3f3f3f;
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0;i < n;i++) list.add(new ArrayList<>());
        for(int i = 1;i < n;i++) list.get(i).add(i - 1);
        for(int q = 0;q < queries.length;q++){
            int[] query = queries[q];
            list.get(query[1]).add(query[0]);
            int[] dp = new int[n];  // dp[i]表示从0到i的最短路径长度
            for(int i = 1;i < n;i++){
                dp[i] = INF;
                for(int prev : list.get(i)){
                    dp[i] = Math.min(dp[i], dp[prev] + 1);
                }
            }
            ret[q] = dp[n - 1];
        }
        return ret;
    }
}
