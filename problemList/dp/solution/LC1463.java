package problemList.dp.solution;

import java.util.*;

/**
这题就老老实实(i1, j1, i2, j2)吧
但是由于每一个人都有三种选择, 因此这里就不能直接硬取max, 只能遍历
初始化: 这里由于不涉及 "障碍" , 因此辅助节点初始化为0或-INF均可
 */
public class LC1463 {
    public int cherryPickup(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][][][] dp = new int[m + 1][n + 1][m + 1][n + 1];
        
    }
}
