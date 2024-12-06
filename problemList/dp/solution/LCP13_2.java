package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
二刷

将总路线拆分成三部分: 
    1. S -> O -> M
    2. M -> O -> M'
    3. M -> T
不难发现: 只要访问M的顺序确定了, 那么总的步数就确定了
因为只要M确定了, 那么对于O来说, 显然我们可以枚举所有的O, 找到最少的步数

因此问题转化成了: 求一个M的访问序列, 使得总的步数最少 , 由于 0 <= M的数量 <= 16 , 因此这显然是一个 状压DP 问题
并且显然对于每一个M, 计算此时的步数都和上一次访问的M'有关, 因此这显然是一个 相邻相关型状压DP

需要注意的是, 对于2这部分的路径, 其都可以表示为 M -> O -> M', 但是第一部分和第三部分的路径和第二部分不一样
因此我们在dp的时候, 需要将第一部分和第三部分放在dp中一起考虑

特别的, 由于最后返回值是最少的步数, 因此实际上对于第三部分的路径来说, 我们不把它放在dp中考虑也可
只需要在dp结束, 求最小步数的时候, 将这部分加上即可, 因为反正最后求的是最小值 (这一点要想清楚, 想清楚为什么这部分可以不放在dp中)

但是对于第一部分路径的步数, 这显然是要放在dp中的, 因此我们可以把这部分的步数放在对应的M的排列的初始位置进行dp计算
具体的实现可以看下面的dp初始化部分

接下来分析一下, 对于给定的M, 如何计算对于所有可能的O, 最少的步数?
具体来说, 我们需要计算以下几种距离: 
    1. 给定M, 如何计算 S -> O -> M 的最少步数?
    2. 给定M, M', 如何计算 M -> O -> M' 的最少步数?
    3. 给定M, 如何计算 M -> T 的最少步数?
由于网格图中有可能有障碍 ('#') , 因此这里的求最短路问题我们可以使用bfs解决
具体来说, 我们可以使用bfs求出对于所有可能的O, S -> O 的最短距离, 以及 O -> M的距离, 那么再遍历这所有可能的O, 将两段距离拼起来
就可以得到对于给定的M, S -> O -> M 的最少步数

对于其余两种距离, 其求解方法也是类似的


dp[state][i] 表示当前访问过的m集合为state, 并且最后访问的m的下标为i, 此时最少的步数
dp[state][i]: 枚举前一次访问的m的下标, 假设为pre
    dp[state][i] = min(dp[state ^ (1 << i)][pre] + m2m[pre][i])     其中m2m[pre][i]指的是从 pre -> O -> i 的最少步数
初始化: 通过前面的分析可以知道, 我们需要将 S -> O -> M 的距离放在dp中考虑, 因此我们可以将这部分的步数放在初始化中
    具体来说, 当state中只有一个M时, 我们可以将此时的步数初始化为 S -> O -> M 的步数
    即 dp[1 << i][i] = s2m[i];
return: min(dp[mask - 1][i] + m2t[i]);  由于 M -> T 这部分步数虽然和M的排列有关, 但是由于最后我们求的是最小步数, 因此我们可以将这部分距离从dp中拿出来单独考虑, 即最后再加到结果上

 */
public class LCP13_2 {
    private char[][] maze;
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{1, 0, -1, 0};
    private int m, n;
    public int minimalSteps(String[] maze) {
        m = maze.length; n = maze[0].length();
        int INF = 0x3f3f3f3f;
        this.maze = new char[m][n];
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                this.maze[i][j] = maze[i].charAt(j);
            }
        }
        List<int[]> mmList = new ArrayList<>();
        List<int[]> ooList = new ArrayList<>();
        int[] s = new int[2];
        int[] t = new int[2];
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(this.maze[i][j] == 'M') mmList.add(new int[]{i, j});
                else if(this.maze[i][j] == 'O') ooList.add(new int[]{i, j});
                else if(this.maze[i][j] == 'S') s = new int[]{i, j};
                else if(this.maze[i][j] == 'T') t = new int[]{i, j};
            }
        }
        int[] s2o = new int[ooList.size()];
        int[][] o2m = new int[ooList.size()][mmList.size()];
        int[] m2t = new int[mmList.size()];
        for(int i = 0;i < ooList.size();i++) s2o[i] = bfs(s, ooList.get(i));
        for(int i = 0;i < ooList.size();i++){
            for(int j = 0;j < mmList.size();j++){
                o2m[i][j] = bfs(ooList.get(i), mmList.get(j));
            }
        }
        for(int i = 0;i < mmList.size();i++) m2t[i] = bfs(mmList.get(i), t);
        int[][] m2m = new int[mmList.size()][mmList.size()];
        for(int i = 0;i < mmList.size();i++){
            for(int j = 0;j < mmList.size();j++){
                m2m[i][j] = INF;
                for(int k = 0;k < ooList.size();k++){
                    m2m[i][j] = Math.min(m2m[i][j], o2m[k][i] + o2m[k][j]);
                }
            }
        }
        int[] s2m = new int[mmList.size()];
        for(int i = 0;i < mmList.size();i++){
            s2m[i] = INF;
            for(int j = 0;j < ooList.size();j++){
                s2m[i] = Math.min(s2m[i], s2o[j] + o2m[j][i]);
            }
        }
        if(mmList.size() == 0){
            int s2t = bfs(s, t);
            return s2t == INF ? -1 : s2t;
        }
        if(mmList.size() == 1){
            int ret = INF;
            for(int i = 0;i < ooList.size();i++){
                if(s2o[i] == INF || o2m[i][0] == INF || m2t[0] == INF) continue;
                ret = Math.min(ret, s2o[i] + o2m[i][0] + m2t[0]);
            }
            return ret == INF ? -1 : ret;
        }
        int mask = 1 << mmList.size();
        int[][] dp = new int[mask][mmList.size()];
        for(int i = 0;i < mmList.size();i++) dp[1 << i][i] = s2m[i];
        int ret = INF;
        for(int state = 1;state < mask;state++){
            if(Integer.bitCount(state) == 1) continue;
            for(int i = 0;i < mmList.size();i++){
                dp[state][i] = INF;
                if(((state >> i) & 1) == 0) continue;
                for(int pre = 0;pre < mmList.size();pre++){
                    if(((state >> pre) & 1) == 0 || pre == i) continue;
                    dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre] + m2m[pre][i]);
                }
                if(state == mask - 1){
                    ret = Math.min(ret, dp[state][i] + m2t[i]);
                }
            }
        }
        return ret == INF ? -1 : ret;
    }

    // return: 从start到end的最短距离
    private int bfs(int[] start, int[] end){
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1], 0});
        boolean[][] vis = new boolean[m][n];
        vis[start[0]][start[1]] = true;
        while(!queue.isEmpty()){
            int[] poll = queue.poll();
            int curX = poll[0], curY = poll[1], step = poll[2];
            if(curX == end[0] && curY == end[1]) return step;
            for(int i = 0;i < 4;i++){
                int nx = curX + dx[i], ny = curY + dy[i];
                if(nx < 0 || nx >= m || ny < 0 || ny >= n || maze[nx][ny] == '#' || vis[nx][ny]) continue;
                vis[nx][ny] = true;
                queue.offer(new int[]{nx, ny, step + 1});
            }
        }
        return 0x3f3f3f3f;  // INF表示到不了, 不过本题中应该不会出现这种情况
    }
}
