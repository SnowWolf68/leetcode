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
 * 解释: 因为不能变成质数, 因此可能需要 "绕远" 来绕过质数, 因此可能存在环形依赖, 这样不能保证计算dp[i]时, 其依赖的位置都已经被正确计算过, 因此不能dp
 * 所谓绕远, 比如示例1
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
