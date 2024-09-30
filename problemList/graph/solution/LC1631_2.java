package problemList.graph.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
本题实际上可以转化成图中的最短路问题: 
    1. 将每个格子抽象成图中的一个点
    2. 将每两个相邻的格子之间连接一条边，长度为这两个格子本身权值的差的绝对值
    3. 需要找到一条从左上角到右下角的「最短路径」，其中路径的长度定义为路径上所有边的权值的最大值

这题使用堆优化Dijkstra要比朴素Dijkstra快很多
 */
public class LC1631_2 {
    public int minimumEffortPath(int[][] heights) {
        int mm = heights.length, nn = heights[0].length, n = mm * nn, INF = 0x3f3f3f3f;
        int[] dx = new int[]{1, 0, -1, 0}, dy = new int[]{0, 1, 0, -1};
        // 建图
        List<int[]>[] g = new ArrayList[n];
        for(int i = 0;i < n;i++) g[i] = new ArrayList<>();
        for(int i = 0;i < mm;i++){
            for(int j = 0;j < nn;j++){
                int newIdx = i * nn + j;
                for(int k = 0;k < 4;k++){
                    int nx = i + dx[k], ny = j + dy[k];
                    if(nx >= 0 && nx < mm && ny >= 0 && ny < nn){
                        int nxtIdx = nx * nn + ny;
                        g[newIdx].add(new int[]{nxtIdx, Math.abs(heights[i][j] - heights[nx][ny])});
                        g[nxtIdx].add(new int[]{newIdx, Math.abs(heights[i][j] - heights[nx][ny])});
                    }
                }
            }
        }
        // dijkstra
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        minHeap.offer(new int[]{0, 0});
        while(!minHeap.isEmpty()){
            int[] poll = minHeap.poll();
            int x = poll[1], distx = poll[0];
            if(distx > dist[x]) continue;
            for(int[] nxt : g[x]){
                int next = nxt[0], weight = nxt[1], newDis = Math.max(weight, dist[x]);
                if(newDis < dist[next]){
                    dist[next] = newDis;
                    minHeap.offer(new int[]{dist[next], next});
                }
            }
        }
        return dist[n - 1];
    }
}
