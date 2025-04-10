package problemList.graph.solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/*
 * 想清楚, 这应该是稀疏图, 用堆优化的dijksra, 而不是朴素dijkstra
 * 
 * 这题更快的方法使用质数筛, 但是我不会, 所以先这样吧, 反正还能过, 就是跑的很慢
 * 
 * 为什么不能dp? (参见灵神题解下的评论: https://leetcode.cn/problems/digit-operations-to-make-two-integers-equal/solutions/3013737/dijkstra-zui-duan-lu-pythonjavacgo-by-en-ofby/)
 * 灵神原话: "可以从 x 变成 y，再从 y 变成 x，有环，没法 DP。"
 * 
 * 也可以参考题解区的另外一位大佬的解释: https://github.com/azl397985856/leetcode/blob/master/problems/3377.digit-operations-to-make-two-integers-equal.md
 * 
 * 这里我再简单解释一下: 假设定义dp[i]表示从n到i的最小代价, 那么假设其中有dp[i1], dp[i2]这两个状态, 其中i2 = i1 + 1
 * 那么显然从dp[i1]可以转化到dp[i2], 从dp[i2]也可以转化为dp[i1], 即这里会出现循环依赖问题
 * 循环依赖会给dp带来什么问题? 如果有循环依赖的话, 那么在我们计算dp[i1]的时候, 其所依赖的dp[i2]这个位置的dp值还没有计算出来 (因为dp[i2]依赖于dp[i1])
 * 因此没办法正确计算dp[i1], 类似的, 计算dp[i2]时同理
 * 综上, 由于环的出现, 导致本题无法使用dp计算最短路
 * 但是由于本题的权值都是正的 (每次变化的权值可以看作是变化后或变化前的数字的值) , 因此可以使用dijkstra来计算最短路
 * 
 */
public class LC3377 {
    public int minOperations(int n, int m) {
        int N = 10 * Math.max(n, m), INF = 0x3f3f3f3f;
        if(isPrime(n)) return -1;
        if(n == m) return n;
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[n] = n;
        boolean[] vis = new boolean[N + 1];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{n, n});
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            if(vis[poll[0]]) continue;
            vis[poll[0]] = true;
            Set<Integer> nxt = new HashSet<>();
            int cur = 0;    // 当前取的是poll[0]的下标为cur的位
            int t = poll[0];
            while (t != 0) {
                int num = t % 10, tClear = poll[0] - (num * ((int)Math.pow(10, cur))), nxtNum = -1;   // 当前位, t去掉当前位剩下的值, 当前位的下一个可能值
                if(num != 0) {
                    nxtNum = num - 1;
                    nxt.add(tClear + (nxtNum * (int)Math.pow(10, cur)));
                }
                if(num != 9){
                    nxtNum = num + 1;
                    nxt.add(tClear + (nxtNum * (int)Math.pow(10, cur)));
                }
                t /= 10;
                cur++;
            }
            for(int nxtNum : nxt){
                if(dist[poll[0]] + nxtNum < dist[nxtNum] && !isPrime(nxtNum)){
                    dist[nxtNum] = dist[poll[0]] + nxtNum;
                    minHeap.offer(new int[]{nxtNum, dist[nxtNum]});
                }
            }
        }
        return dist[m] == INF ? -1 : dist[m];
    }

    private boolean isPrime(int x){
        if(x == 1) return false;
        for(int i = 2;i < (int)Math.sqrt(x) + 1;i++){
            if(x % i == 0) return false;
        }
        return true;
    }
}
