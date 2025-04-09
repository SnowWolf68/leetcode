package problemList.graph.solution;

import java.util.Arrays;

/*
 * 自己做出来的~
 * 
 * 比较特殊的图
 * 
 * 只需要考虑start, target, 以及n个specialRoads的起点, 考虑将这n + 2个点建一个图
 * 并且这个图是一个 无向完全图 , 即任意两个点之间都有一条边
 * 在实际代码中, 并不需要将这个图真的建起来, 我们可以使用 0 ~ n - 1 分别表示specialRoads的下标, 使用n和n + 1表示start和target
 * 边权: 这题的边权比较特殊, 从(x, y)到(nx, ny)的边权并不固定, 有可能是直接走, 也有可能是走specialRoads, 再直接走完剩下的, 两种情况都需要考虑
 * 
 * 在上面描述的这个图上跑一遍dijksra即可
 */
public class LC2662 {
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        int n = specialRoads.length, INF = 0x3f3f3f3f;
        int[] dist = new int[n + 2];    // dist[n] = start, dist[n + 1] = target
        Arrays.fill(dist, INF);
        dist[n] = 0;
        boolean[] vis = new boolean[n + 2];
        for(int i = 0;i < n + 2;i++){
            int t = -1, min = INF;
            for(int j = 0;j < n + 2;j++){
                if(dist[j] < min && !vis[j]){
                    t = j;
                    min = dist[j];
                }
            }
            vis[t] = true;
            int[] curPos = getPos(t, start, target, specialRoads);
            int x = curPos[0], y = curPos[1];
            for(int j = 0;j < n + 2;j++){
                int[] nxtPos = getPos(j, start, target, specialRoads);
                int nx = nxtPos[0], ny = nxtPos[1];
                if(dist[t] + getCost(x, y, nx, ny) < dist[j]){
                    dist[j] = dist[t] + getCost(x, y, nx, ny);
                }
                if(t < n && dist[t] + specialRoads[t][4] + getCost(specialRoads[t][2], specialRoads[t][3], nx, ny) < dist[j]){
                    dist[j] = dist[t] + specialRoads[t][4] + getCost(specialRoads[t][2], specialRoads[t][3], nx, ny);
                }
            }
        }
        return dist[n + 1];
    }

    private int getCost(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private int[] getPos(int t, int[] start, int[] target, int[][] specialRoads){
        int x = -1, y = -1, n = specialRoads.length;
        if(t == n){
            x = start[0];
            y = start[1];
        }else if(t == n + 1){
            x = target[0];
            y = target[1];
        }else{
            x = specialRoads[t][0];
            y = specialRoads[t][1];
        }
        return new int[]{x, y};
    }
}
