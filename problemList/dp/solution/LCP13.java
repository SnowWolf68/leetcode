package solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
首先分析一下题目, 题目中提到了5类点: 
    1. S: 起点
    2. T: 终点(宝藏地点)
    3. M: 机关点
    4. O: 石堆
    5. #: 墙壁
我们的目的是: 从S出发, 经过每个机关点, 并且经过每个机关点的同时都需要找一个石头放在机关点上, 然后走到T
可以继续细分一下: 
    1. 首先从S出发, 走到某一个石堆O, 拿起一块石头, 然后走到某一个机关点M, 将石头放到机关点上, 这类路径我们记为 S-O-M
    2. 从某一个机关点M出发, 走到某一个石堆O, 拿起一块石头, 然后走到另外一个机关点M', 这类路径我们记为 M-O-M'
    3. 从某一个机关点M出发, 直接走到T, 这类路径我们记为 M-T
不难发现, 在整个过程中我们需要走的路径无非上面的这三类, 其中: 
    第一类 S-O-M 路径只会走一次, 第二类 M-O-M' 路径会走多次, 第三类 M-T 路径也只会走一次
进一步分析这三种路径: 
    1. S-O-M: 这类路径中的S已经确定, 并且只要确定了目标机关M, 我们可以遍历所有的O, 就可以找到一条最短的路径, 即S-O-M类路径的长度和M有关
    2. M-O-M': 类似的, 我们只需要确定了M, M', 只需要遍历所有的O, 找到一条最短路径即可
    3. M-T: 同样, 只需要确定M, 就可以找到一条M-T的最短路径
因此我们发现, 现在唯一不确定的就是 M的遍历顺序 , 即如果确定了所有机关点的遍历顺序, 我们就能够确定这种情况下的最短路径

因此这其实是对 M 的排列型DP问题, 并且相邻相关, 因此这就是一个 相邻相关的排列型状压DP 问题

状压DP的部分就比较常规, 这里不再赘述, 具体细节可以直接看代码
 */
public class LCP13 {
    private int m, n;
    private String[] maze;
    public int minimalSteps(String[] _maze) {
        maze = _maze;
        // m行, n列
        m = maze.length; n = maze[0].length();
        // 统计maze中所有的S, T, M, O这四类点的位置
        List<int[]> ss = new ArrayList<>(), tt = new ArrayList<>(), mm = new ArrayList<>(), oo = new ArrayList<>();
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(maze[i].charAt(j) == 'S') ss.add(new int[]{i, j});
                if(maze[i].charAt(j) == 'T') tt.add(new int[]{i, j});
                if(maze[i].charAt(j) == 'M') mm.add(new int[]{i, j});
                if(maze[i].charAt(j) == 'O') oo.add(new int[]{i, j});
            }
        }
        int ms = mm.size();
        int o = oo.size();
        int[][] ooToMM = new int[o][ms];
        for(int i = 0;i < o;i++){
            for(int j = 0;j < ms;j++){
                ooToMM[i][j] = bfs(oo.get(i), mm.get(j));
            }
        }
        int[] ssToOO = new int[o];
        for(int i = 0;i < o;i++){
            ssToOO[i] = bfs(ss.get(0), oo.get(i));
        }
        int INF = 0x3f3f3f3f;
        int[] ssToMM = new int[ms];
        for(int i = 0;i < ms;i++){
            ssToMM[i] = INF;
            for(int j = 0;j < o;j++){
                if(ooToMM[j][i] > 0) ssToMM[i] = Math.min(ssToMM[i], ssToOO[j] + ooToMM[j][i]);
            }
        }
        int[][] mmToMM = new int[ms][ms];
        for(int i = 0;i < ms;i++){
            for(int j = 0;j < ms;j++){
                if(i != j){
                    mmToMM[i][j] = INF;
                    for(int k = 0;k < o;k++){
                        if(ooToMM[k][i] > 0 && ooToMM[k][j] > 0) mmToMM[i][j] = Math.min(mmToMM[i][j], ooToMM[k][i] + ooToMM[k][j]);
                    }
                }
            }
        }
        int[] mmToTT = new int[ms];
        for(int i = 0;i < ms;i++){
            mmToTT[i] = bfs(mm.get(i), tt.get(0));
        }
        int mask = 1 << ms;
        int[][] dp = new int[mask][ms];
        for(int i = 0;i < ms;i++){
            Arrays.fill(dp[1 << i], INF);
            dp[1 << i][i] = ssToMM[i];
        }
        int ret = INF;
        // 只有一个M时, 特殊处理
        if(ms == 1){
            if(ssToMM[0] > 0 && mmToTT[0] > 0){
                return ssToMM[0] + mmToTT[0];
            }else{
                return -1;
            }
        }
        // 没有M时, 特殊处理
        if(ms == 0){
            return bfs(ss.get(0), tt.get(0));
        }
        for(int state = 1;state < mask;state++){
            if(Integer.bitCount(state) <= 1) continue;
            for(int i = 0;i < ms;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state][i] = INF;
                for(int j = 0;j < ms;j++){
                    if(i == j || ((state >> j) & 1) == 0) continue;
                    if(mmToMM[j][i] >= 0) dp[state][i] = Math.min(dp[state][i], dp[state & (~(1 << i))][j] + mmToMM[j][i]);
                }
                if(state == mask - 1) {
                    if(mmToTT[i] > 0){
                        dp[state][i] += mmToTT[i];
                        ret = Math.min(ret, dp[state][i]);
                    }else{
                        dp[state][i] = -1;
                    }
                }
            }
        }
        return ret == INF ? -1 : ret;
    }
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};
    /*
     * 求从from到to的最短路径长度, -1表示不可达
     */
    private int bfs(int[] from, int[] to){
        Queue<int[]> queue = new LinkedList<>();
        boolean[] vis = new boolean[m * n];
        queue.offer(new int[]{from[0], from[1], 0});
        vis[from[0] * n + from[1]] = true;
        while(!queue.isEmpty()){
            int[] poll = queue.poll();
            for(int i = 0;i < 4;i++){
                int nx = poll[0] + dx[i], ny = poll[1] + dy[i];
                if(nx == to[0] && ny == to[1]) {
                    return poll[2] + 1;
                }
                if(nx >= 0 && nx < m && ny >= 0 && ny < n && maze[nx].charAt(ny) != '#' &&
                    !vis[nx * n + ny]){
                    vis[nx * n + ny] = true;
                    queue.offer(new int[]{nx, ny, poll[2] + 1});
                }
            }
        }
        // -1 表示from和to不可达
        return -1;
    }
}
