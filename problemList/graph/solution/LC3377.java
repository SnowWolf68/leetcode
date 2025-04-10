package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * TODO: 还得debug
 */
public class LC3377 {
    public int minOperations(int n, int m) {
        int N = 10 * Math.max(n, m), INF = 0x3f3f3f3f;
        System.out.println("N = " + N);
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
            System.out.println("test..");
            vis[t] = true;
            Set<Integer> nxt = new HashSet<>();
            int cur = 0;    // 当前取的是t的下标为cur的位
            int tt = t;     // copy
            while (t != 0) {
                int num = t % 10, tClear = tt - (num * ((int)Math.pow(10, cur))), nxtNum = -1;   // 当前位, t去掉当前位剩下的值, 当前位的下一个可能值
                System.out.println("num = " + num + " t = " + t + " tt = " + tt + " cur = " + cur + " tClear = " + tClear);
                if(num != 0) {
                    nxtNum = num - 1;
                    nxt.add(tClear + (nxtNum * (10 ^ cur)));
                    System.out.println("nxt = " + (tClear + (nxtNum * (10 ^ cur))));
                }else if(num != 9){
                    nxtNum = num + 1;
                    nxt.add(tClear + (nxtNum * (10 ^ cur)));
                    System.out.println("nxt = " + (tClear + (nxtNum * (10 ^ cur))));
                }
                // System.out.println("nxt.size() = " + nxt.size());
                t /= 10;
                cur++;
            }
            for(int nxtNum : nxt){
                System.out.println("nxtNum = " + nxtNum);
                if(dist[t] + nxtNum < dist[nxtNum] && !isPrime(nxtNum)){
                    dist[nxtNum] = dist[t] + nxtNum;
                }
            }
        }
        return dist[m];
    }

    private boolean isPrime(int x){
        for(int i = 2;i < (int)Math.sqrt(x) + 1;i++){
            if(x % i == 0) return true;
        }
        return false;
    }
}
