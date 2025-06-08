package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
错误解法: 
    dp[i][j] 表示: 以i结尾, 长度为j的路径的不超过t的权值和
    dp[i][j]: 使用刷表法
        对于 i -> nxt: 
        for j in (0, k + 1): 
            if(dp[i][j - 1] + w(i, nxt) < t)
                dp[nxt][j] = max(dp[i][j - 1] + w(i, nxt));
    初始化: dp[i][0] = 0, 其余位置都是-INF
    return max(dp[i][k])

错误原因: 状态转移方程错误!
    if(dp[i][j - 1] + w(i, nxt) < t)
        dp[nxt][j] = max(dp[i][j - 1] + w(i, nxt));
    这种转移方式不能保证dp[nxt][i]的计算是正确的
    因为dp[nxt][j]依赖的位置dp[i][j - 1]取的是以i结尾的路径的权值最大值, 有可能这个最大值加上w(i, nxt)之后就超过了t
    但是存在一条以i结尾的路径, 其加上w之后不超过t, 那么上面这种错误的状态转移方程就会漏掉这种正确的情况
解决方法: 漏掉某些正确情况, 说明状态表示中用到的参数太少, 因此可以考虑给dp表添加一个维度w, 表示当前路径的权值和
即: dp[i][j][w] 表示: 以i结尾, 长度为j, 权值和为w的路径是否存在
 */
public class LC3543_WA {
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
        int INF = 0x3f3f3f3f;
        for(int[] arr : dp) Arrays.fill(arr, -INF);
        for(int i = 0;i < n;i++) dp[i][0] = 0;
        int ret = -1;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            for(int[] nxtArr : g[poll]){
                int nxt = nxtArr[0], w = nxtArr[1];
                for(int j = 1;j <= k;j++){
                    if(dp[poll][j - 1] + w < t){
                        dp[nxt][j] = Math.max(dp[nxt][j], dp[poll][j - 1] + w);
                    }
                }
                if(--in[nxt] == 0) queue.offer(nxt);
            }
        }
        for(int i = 0;i < n;i++) {
            ret = Math.max(ret, dp[i][k]);
        }
        return ret;
    }
}
