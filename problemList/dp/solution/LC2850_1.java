package solution;

import java.util.*;

/**
状压DP

分析一下为什么这题可以用状压DP来解决: 
对于第二个示例: 
    1, 3, 0
    1, 0, 0
    1, 0, 3
将所有需要 移出去石头 的位置列出来 (如果一个位置有多个(n个)石头需要移出去, 那么就将这个位置重复写n次)
同理将所有需要 移入石头 的位置列出来
移出石头位置: (0, 1), (0, 1), (2, 2), (2, 2)
移入石头位置: (0, 2), (1, 1), (1, 2), (2, 1)

将一个石头从 需要被移出的位置 移动到 需要被移入的位置 所需的移动次数就是 移出石头位置 和 移入石头位置 时间的距离 abs(x1 - x2) + abs(y1 - y2)

这样一看是不是就是LC1879这题了?
 */
public class LC2850_1 {
    public int minimumMoves(int[][] grid) {
        List<int[]> in = new ArrayList<>();
        List<int[]> out = new ArrayList<>();
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(grid[i][j] == 0){
                    in.add(new int[]{i, j});
                }else if(grid[i][j] > 1){
                    for(int k = 0;k < grid[i][j] - 1;k++){
                        out.add(new int[]{i, j});
                    }
                }
            }
        }
        int n = in.size(), mask = 1 << n, INF = 0x3f3f3f3f;
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int state = 1;state < mask;state++){
            int bitCount = Integer.bitCount(state);
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state] = Math.min(dp[state], dp[state & (~(1 << i))] + Math.abs(in.get(bitCount - 1)[0] - out.get(i)[0]) + Math.abs(in.get(bitCount - 1)[1] - out.get(i)[1]));
            }
        }
        return dp[mask - 1];
    }
}
