package problemList.dp.solution;

import java.util.*;

/**
这题可以向上下左右四个方向都进行移动, 看起来好像需要依赖四个位置
但是其实题目要求 "最长递增路径" , 也就是说, 当前格子只会依赖比当前格子的值大的格子的dp值
因此我们只需要保证更新当前格子时, 当前格子相邻的四个格子中, 值比当前格子的值大的格子的dp值已经被正确更新了即可

dp[i][j] 表示从i, j出发的最长递增路径的长度
* 为了保证状态依赖的正确性, 我们需要首先对所有格子进行排序, 按照格子值降序排序, 然后按照排序后的顺序进行dp
    dp[i][j]: 枚举相邻格子中比当前格子值大的ni, nj
        dp[i][j] = Math.max(dp[i][j], dp[ni][nj] + 1);
初始化: 所有 "孤立" 的极大值节点的dp值都初始化成1
    在具体代码实现中, 我们可以将初始化过程和dp过程合并
    具体的: 在每次dp到(i, j)时, 首先将dp[i][j] = 1, 此时如果当前节点是 "孤立" 极大值节点, 那么此时k的循环就不会进去, 那么此时就相当于是初始化过程
        如果当前节点不是 "孤立" 极大值节点, 那么此时当前节点也可以看做是一个长度为1的最长递增路径, 此时也是正确的
    这样就简化了O(m * n)的单独手动初始化过程
return Math.max(dp[i][j]);    // 即dp表中的最大值
 */
public class LC329 {
    private int[] dx = new int[]{1, 0, -1, 0};
    private int[] dy = new int[]{0, 1, 0, -1};
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        List<int[]> valList = new ArrayList<>();
        List<int[]> indexList = new ArrayList<>();
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                valList.add(new int[]{matrix[i][j], valList.size()});
                indexList.add(new int[]{i, j});
            }
        }
        Collections.sort(valList, (o1, o2) -> o2[0] - o1[0]);
        int[][] dp = new int[m][n];
        for(int p = 0;p < valList.size();p++){
            int[] cur = indexList.get(valList.get(p)[1]);
            int i = cur[0], j = cur[1];
            dp[i][j] = 1;
            for(int k = 0;k < 4;k++){
                int ni = i + dx[k], nj = j + dy[k];
                if(ni >= 0 && ni < m && nj >= 0 && nj < n){
                    if(matrix[ni][nj] > matrix[i][j]){
                        dp[i][j] = Math.max(dp[i][j], dp[ni][nj] + 1);
                    }
                }
            }
        }
        int ret = 0;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                ret = Math.max(ret, dp[i][j]);
            }
        }
        return ret;
    }
}
