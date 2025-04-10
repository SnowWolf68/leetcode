package problemList.graph.solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 抽象图上的Dijkstra
 */
public class LC3377_TLE {
    public int minOperations(int n, int m) {
        int N = 10 * Math.max(n, m), INF = 0x3f3f3f3f;
        if(isPrime(n)) return -1;
        if(n == m) return n;
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[n] = n;
        boolean[] vis = new boolean[N + 1];
        for(int i = 0;i <= N;i++){
            int t = -1, min = INF;
            for(int j = 0;j <= N;j++){
                if(dist[j] < min && !vis[j]){
                    min = dist[j];
                    t = j;
                }
            }
            if(t == -1) break;
            vis[t] = true;
            Set<Integer> nxt = new HashSet<>();
            int cur = 0;    // 当前取的是t的下标为cur的位
            int tt = t;     // copy
            while (t != 0) {
                int num = t % 10, tClear = tt - (num * ((int)Math.pow(10, cur))), nxtNum = -1;   // 当前位, t去掉当前位剩下的值, 当前位的下一个可能值
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
                if(dist[tt] + nxtNum < dist[nxtNum] && !isPrime(nxtNum)){
                    dist[nxtNum] = dist[tt] + nxtNum;
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
