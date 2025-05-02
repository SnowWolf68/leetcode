package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 尝试朴素dijkstra是否能实现求严格次短路
 */
public class LC2045_3_TLE {
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
        // second[0] = 0;
        for (int i = 0; i < 2 * n - 1; i++) {
            int firstMin = INF, secondMin = INF, firstT = -1, secondT = -1;

            // 这种更新方式无法保证secondMin是严格大于firstMin的全局最小

            // for (int j = 0; j < n; j++) {
            //     if (dist[j] < firstMin && !done1[j]) {
            //         firstMin = dist[j];
            //         firstT = j;
            //     } else if (dist[j] != firstMin && dist[j] < secondMin && !done1[j]) {
            //         secondMin = dist[j];
            //         secondT = j;
            //     }
            // }
            // System.out.println("before: firstMin = " + firstMin + " firstT = " + firstT + " secondMin = " + secondMin + " secondT = " + secondT);
            // for (int j = 0; j < n; j++) {
            //     if (second[j] < secondMin && !done2[j] && second[j] < firstMin) {
            //         secondMin = second[j];
            //         secondT = j;
            //     }
            // }

            // 这才是正确的更新方式
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

            // System.out.println(Arrays.toString(dist));
            // System.out.println(Arrays.toString(second));
            // System.out.println("after: firstMin = " + firstMin + " firstT = " + firstT + " secondMin = " + secondMin + " secondT = " + secondT);

            if (firstT != -1)
                done1[firstT] = true;
            if (secondT != -1)
                done2[secondT] = true;
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
        // System.out.println(Arrays.toString(dist));
        // System.out.println(Arrays.toString(second));
        return second[n - 1];
    }

    private int calcTime(int time, int change, int curTime) {
        return time + ((curTime / change) % 2 == 0 ? 0 : (change - (curTime % change)));
    }
}
