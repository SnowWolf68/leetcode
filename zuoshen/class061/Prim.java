package zuoshen.class061;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
题目1
实现Prim算法
返回最小生成树的最小权值和
测试链接 : https://www.luogu.com.cn/problem/P3366

注: 下面的代码在luogu上提交会MLE, 不过算法整体是没问题的
注: 在luogu上, 节点的编号是从1开始的
 */
public class Prim {
    private List<int[]> prim(int n, List<int[]> edges){
        int[][] g = new int[n + 1][n + 1];
        int INF = 0x3f3f3f3f;
        for(int i = 0;i <= n;i++) Arrays.fill(g[i], INF);
        for(int[] e : edges){
            // luogu上的数据很坑, 可能会有 2 4 276 , 2 4 860  这种数据, 因此需要取最小的权值
            if(g[e[0]][e[1]] > e[2]) {
                g[e[0]][e[1]] = e[2];
                g[e[1]][e[0]] = e[2];
            }
        }
        boolean[] vis = new boolean[n + 1];
        vis[1] = true;  // 假设从1开始
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        for(int i = 1;i <= n;i++){
            if(g[1][i] != INF) minHeap.offer(new int[]{1, i, g[1][i]});
        }
        int cnt = 1;    // 当前选择的点的数量
        List<int[]> ret = new ArrayList<>();
        while(!minHeap.isEmpty()){
            int[] e = minHeap.poll();
            if(vis[e[1]]) continue;
            else {
                vis[e[1]] = true;
                for(int i = 1;i <= n;i++){
                    if(g[e[1]][i] != INF){
                        minHeap.offer(new int[]{e[1], i, g[e[1]][i]});
                    }
                }
                ret.add(new int[]{e[1], e[0], g[e[1]][e[0]]});
                cnt++;
            }
        }
        if(cnt != n) return null;
        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        List<int[]> edges = new ArrayList<>();
        for(int i = 0;i < m;i++){
            int x = sc.nextInt(), y = sc.nextInt(), weight = sc.nextInt();
            edges.add(new int[]{x, y, weight});
        }
        List<int[]> ret = new Prim().prim(n, edges);
        if(ret == null) System.out.println("orz");
        else{
            int sum = 0;
            for(int[] e : ret) sum += e[2];
            System.out.println(sum);   
        }
    }
}
