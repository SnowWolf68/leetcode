package zuoshen.class061;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
题目1
实现Kruskal算法
返回最小生成树的最小权值和
测试链接 : https://www.luogu.com.cn/problem/P3366

注: 下面的代码在luogu上提交会MLE, 不过算法整体是没问题的
注: 在luogu上, 节点的编号是从1开始的
 */
public class Kruskal {
    class UnionFind{
        private int[] pa;
        private int[] size;

        UnionFind(int n){
            pa = new int[n];
            size = new int[n];
            for(int i = 0;i < n;i++) pa[i] = i;
        }

        public int find(int x){
            if(pa[x] != x){
                pa[x] = find(pa[x]);
            }
            return pa[x];
        }

        public boolean isSameSet(int x, int y){
            return find(x) == find(y);
        }

        public void union(int x, int y){
            int px = find(x), py = find(y);
            if(size[px] > size[py]){
                pa[py] = px;
                size[px] += size[py];
            }else{
                pa[py] = px;
                size[py] += size[px];
            }
        }
    }

    public List<int[]> kruskal(int n, List<int[]> edges){
        Collections.sort(edges, (a, b) -> a[2] - b[2]);
        UnionFind uf = new UnionFind(n + 1);
        List<int[]> ret = new ArrayList<>();
        for(int[] edge : edges){
            int x = edge[0], y = edge[1], weight = edge[2];
            if(!uf.isSameSet(x, y)) {
                ret.add(new int[]{x, y, weight});
                uf.union(x, y);
            }
        }
        if(ret.size() < n - 1) return null;
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
        List<int[]> ret = new Kruskal().kruskal(n, edges);
        if(ret == null) System.out.println("orz");
        else{
            int sum = 0;
            for(int[] e : ret) sum += e[2];
            System.out.println(sum);   
        }
    }
}
