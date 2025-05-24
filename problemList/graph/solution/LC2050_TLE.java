package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LC2050_TLE {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<Integer>[] g = new List[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        int[] in = new int[n];
        for(int[] r : relations){
            g[r[0] - 1].add(r[1] - 1);
            in[r[1] - 1]++;
        }
        List<Integer> topo = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0;i < n;i++) {
            if(in[i] == 0) queue.offer(i);
        }
        System.out.println(Arrays.toString(in));
        Map<Integer, List<Integer>> pre = new HashMap<>();
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            topo.add(poll);
            for(int nxt : g[poll]){
                List<Integer> curPre = pre.getOrDefault(nxt, new ArrayList<>());
                curPre.add(poll);
                pre.put(nxt, curPre);
                if(--in[nxt] == 0) queue.offer(nxt);
            }
        }
        int[] dp = new int[n];
        dp[topo.get(0)] = time[topo.get(0)];
        for(int i = 1;i < n;i++){
            for(int j : pre.getOrDefault(topo.get(i), new ArrayList<>())){
                dp[topo.get(i)] = Math.max(dp[topo.get(i)], dp[j]);
            }
            dp[topo.get(i)] += time[topo.get(i)];
        }
        // System.out.println(topo.toString());
        // System.out.println(Arrays.toString(dp));
        return dp[topo.get(n - 1)];
    }
}
