package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
没有必要先拓扑排序再dp, 可以放在一起来
但是这样就需要用刷表法
 */
public class LC2050_2 {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        int[] in = new int[n];
        for(int[] r : relations){
            g[r[0] - 1].add(r[1] - 1);
            in[r[1] - 1]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0;i < n;i++) {
            if(in[i] == 0) queue.offer(i);
        }
        int ret = 0;
        int[] dp = new int[n];
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            dp[poll] += time[poll];
            for(int nxt : g[poll]){
                dp[nxt] = Math.max(dp[nxt], dp[poll]);
                if(--in[nxt] == 0) queue.offer(nxt);
            }
            ret = Math.max(ret, dp[poll]);
        }
        return ret;
    }
}
