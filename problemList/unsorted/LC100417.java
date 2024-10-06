package problemList.unsorted;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// https://leetcode.cn/contest/weekly-contest-418/problems/remove-methods-from-project/description/
/**
先dfs找出所有可疑方法, 然后再开始删

1. 如果k所在的联通分量中有环, 那么把这个环全删了
2. 如果k所在的联通分量中没有环, 那么从k开始, 跑一遍拓扑排序
 */
public class LC100417 {
    private int[][] g;
    private Set<Integer> hashSet = new HashSet<>();
    private boolean[] vis;
    private int n;
    public List<Integer> remainingMethods(int _n, int k, int[][] invocations) {
        // 邻接矩阵建图
        n = _n;
        g = new int[n][n];
        vis = new boolean[n];
        int[] in = new int[n];
        for(int[] invo : invocations){
            g[invo[0]][invo[1]] = 1;
            in[invo[1]]++;
        }
        dfs(k);
        List<Integer> ret = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(i != k && in[i] == 0) ret.add(i);
        }
        // 如果k顶点的入度不为0, 那么说明无法删除任何点
        if(in[k] != 0) {
            // System.out.println("bbb");
            for(int i = 0;i < n;i++){
                if(g[i][k] == 1 && !hashSet.contains(i)) {
                    ret = new ArrayList<>();
                    for(int j = 0;j < n;j++) ret.add(j);
                    return ret;
                }
            }
        }
        in[k] = 0;
        for(int i = 0;i < n;i++){
            if(g[i][k] == 1) g[i][k] = 0;
        }
        // System.out.println(Arrays.toString(in));
        // 进行拓扑排序
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(k);
        while(!queue.isEmpty()){
            int poll = queue.poll();
            // System.out.println("poll: " + poll);
            for(int i = 0;i < n;i++){
                if(g[poll][i] == 1){
                    in[i]--;
                    if(in[i] == 0){
                        queue.offer(i);
                    }
                }
            }
        }
        // System.out.println("aaa");
        // System.out.println(Arrays.toString(in));
        for(int i = 0;i < n;i++){
            if(in[i] != 0) {
                ret.add(i);
                if(hashSet.contains(i)){
                    ret = new ArrayList<>();
                    for(int j = 0;j < n;j++) ret.add(j);
                    return ret;
                }
            }
        }
        return ret;
    }

    // 找到所有可以方法, 存储在hashSet中
    private void dfs(int i) {
        vis[i] = true;
        hashSet.add(i);
        for(int j = 0;j < n;j++){
            if(g[i][j] == 1 && !vis[j]){
                dfs(j);
            }
        }
    }

    public static void main(String[] args) {
        int[][] invocations = new int[][]{{1,2},{0,1},{2,0}};
        int n = 3, k = 2;
        System.out.println(new LC100417().remainingMethods(n, k, invocations));
    }
}
