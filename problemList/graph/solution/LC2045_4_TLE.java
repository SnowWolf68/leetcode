package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 对LC2045_3_TLE中的代码加以整理
 * 
 * 时间复杂度: O(n ^ 2 + e), 其中找min的过程是O(n ^ 2), 松弛的过程最多执行O(e)次
 * 由于这个是稀疏图, e 和 n 的数量级大致相等, 因此最终的时间复杂度为 O(n ^ 2)
 * 
 * 为什么松弛的次数是O(e)级别? 因为每次松弛都是要用某一条边进行松弛, 并且在求最大/次大的过程中, 一条边至多用于一次松弛 (单独求最大/次大的过程中)
 * 因此总的松弛的数量级为O(e)
 * 
 */
public class LC2045_4_TLE {
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        List<Integer>[] g = new List[n];
        int INF = 0x3f3f3f3f;
        for (int i = 0; i < n; i++)
            g[i] = new ArrayList<>();
        for (int[] e : edges) {
            g[e[0] - 1].add(e[1] - 1);
            g[e[1] - 1].add(e[0] - 1);
        }
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        int[] second = new int[n];
        Arrays.fill(second, INF);
        boolean[] done1 = new boolean[n];
        boolean[] done2 = new boolean[n];
        for (int i = 0; i < 2 * n - 1; i++) {
            int firstMin = INF, secondMin = INF, firstT = -1, secondT = -1;

            for (int j = 0; j < n; j++) {
                if (dist[j] < firstMin && !done1[j]) {
                    firstMin = dist[j];
                    firstT = j;
                } 
            }

            for (int j = 0; j < n; j++) {
                if (second[j] < secondMin && !done2[j] && second[j] < firstMin) {
                    secondMin = second[j];
                    secondT = j;
                }
            }

            if (firstT != -1) done1[firstT] = true;
            if (secondT != -1) done2[secondT] = true;
            int w1 = calcTime(time, change, firstMin), w2 = calcTime(time, change, secondMin);
            if (firstT != -1) {
                for (int j : g[firstT]) {
                    if (firstMin + w1 < dist[j]) {
                        dist[j] = firstMin + w1;
                    } else if (firstMin + w1 != dist[j] && firstMin + w1 < second[j]) {
                        second[j] = firstMin + w1;
                    }
                }
            }
            if (secondT != -1) {
                for (int j : g[secondT]) {
                    if (secondMin + w2 != dist[j] && secondMin + w2 < second[j]) {
                        second[j] = secondMin + w2;
                    }
                }
            }
        }
        return second[n - 1];
    }

    private int calcTime(int time, int change, int curTime) {
        return time + ((curTime / change) % 2 == 0 ? 0 : (change - (curTime % change)));
    }
}
