package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/*
 * 对拍, gpt写的
 */
public class LC2045_Judge {
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

    // 你的实现
    public int secondMinimum_mine(int n, int[][] edges, int time, int change) {
        return secondMinimum(n, edges, time, change);
    }

    // 参考实现（标准做法：BFS+每点记录前两次到达时间）
    public int secondMinimum_std(int n, int[][] edges, int time, int change) {
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int[] e : edges) {
            g[e[0] - 1].add(e[1] - 1);
            g[e[1] - 1].add(e[0] - 1);
        }
        int[][] arrive = new int[n][2];
        for (int i = 0; i < n; i++) Arrays.fill(arrive[i], Integer.MAX_VALUE);
        arrive[0][0] = 0;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int u = cur[0], t = cur[1];
            for (int v : g[u]) {
                int nextTime = getNextTime(t, time, change);
                if (nextTime < arrive[v][0]) {
                    arrive[v][1] = arrive[v][0];
                    arrive[v][0] = nextTime;
                    q.offer(new int[]{v, nextTime});
                } else if (nextTime > arrive[v][0] && nextTime < arrive[v][1]) {
                    arrive[v][1] = nextTime;
                    q.offer(new int[]{v, nextTime});
                }
            }
        }
        return arrive[n - 1][1];
    }
    private int getNextTime(int cur, int time, int change) {
        if ((cur / change) % 2 == 1) {
            cur += change - (cur % change);
        }
        return cur + time;
    }

    // 随机生成连通无向图
    static Random rnd = new Random();
    public static int[][] randomEdges(int n, int maxE) {
        Set<String> set = new HashSet<>();
        List<int[]> res = new ArrayList<>();
        // 保证连通
        for (int i = 2; i <= n; i++) {
            int u = i, v = rnd.nextInt(i - 1) + 1;
            set.add(Math.min(u,v) + "," + Math.max(u,v));
            res.add(new int[]{u, v});
        }
        // 再加随机边
        int m = Math.min(maxE, n * (n-1) / 2);
        while (res.size() < m) {
            int u = rnd.nextInt(n) + 1, v = rnd.nextInt(n) + 1;
            if (u == v) continue;
            String key = Math.min(u,v) + "," + Math.max(u,v);
            if (set.contains(key)) continue;
            set.add(key);
            res.add(new int[]{u, v});
        }
        return res.toArray(new int[0][0]);
    }

    public static void main(String[] args) {
        LC2045_Judge judge = new LC2045_Judge();
        int cases = 1000;
        for (int t = 1; t <= cases; t++) {
            int n = rnd.nextInt(30) + 50; // 节点2~7, 适合暴力
            int[][] edges = randomEdges(n, n + rnd.nextInt(n));
            int time = rnd.nextInt(5) + 1;
            int change = rnd.nextInt(5) + 1;

            int ans1 = judge.secondMinimum_mine(n, edges, time, change);
            int ans2 = judge.secondMinimum_std(n, edges, time, change);
            if (ans1 != ans2) {
                System.out.println("Wrong! Case #" + t);
                System.out.println("n=" + n + ", time=" + time + ", change=" + change);
                System.out.println("edges=" + Arrays.deepToString(edges));
                System.out.println("mine=" + ans1 + ", std=" + ans2);
                break;
            }
            if (t % 100 == 0) System.out.println("Passed " + t + " cases.");
        }
        System.out.println("Done!");
    }
}
