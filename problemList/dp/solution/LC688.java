package problemList.dp.solution;

import java.util.Arrays;

/**
概率DP

dp[p][i][j] 表示从[i, j]开始, 走p步, 还留在棋盘上的概率
dp[p][i][j]: 枚举从[i, j]能够走到的所有位置
    假设从[i, j]位置走到了[nx, ny]位置, 那么 dp[p][i][j] += (1 / 8) * dp[p - 1][nx][ny]
初始化: 需要注意的是, 这里由于走出棋盘外的位置, 我们也需要在dp表中表示, 因此我们将原本的下标范围[0, n - 1]转换成[2, n + 1]
    这样即使走出棋盘外, 也不会在dp表中越界, 此时如果下标在[0, 1] || [n + 2, n + 3]内, 说明此时在棋盘外, 如果下标在[2, n + 1]内, 说明在棋盘范围内 (注意这里只是说的棋盘的某一个维度)
        因此我们的dp表的维度应该开到 new double[k + 1][n + 4][n + 4] 这么大
    说回初始化的问题, 这里p - 1有可能越界, 因此需要初始化p == 0的情况, 当p == 0时, 此时意味着不能再走了, 那么还留在棋盘上的概率只取决于当前[i, j]是否在棋盘上
    即 对于 2 <= i <= n + 1 && 2 <= j <= n + 1 的 i, j 来说, 其dp[0][i][j] = 1, 其余位置的dp[0][i][j] = 0
return dp[k][row + 2][column + 2];  // 这里之所以 + 2 是因为前面我们把下标的映射关系改了一下
 */
public class LC688 {
    private int[][] dirs = new int[][]{{1, 2}, {1, -2}, {2, 1}, {2, -1}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
    public double knightProbability(int n, int k, int row, int column) {
        double[][][] dp = new double[k + 1][n + 4][n + 4];
        for(int i = 2;i < n + 2;i++) Arrays.fill(dp[0][i], 2, n + 2, 1);
        for(int p = 1;p <= k;p++){
            for(int i = 2;i <= n + 1;i++){
                for(int j = 2;j <= n + 1;j++){
                    for(int[] dir : dirs){
                        int nx = i + dir[0], ny = j + dir[1];
                        // 由于i, j我们枚举的是在棋盘中的位置, 因此此时的nx, ny一定不会越界, 不需要判断
                        dp[p][i][j] += (1.0 / 8) * dp[p - 1][nx][ny];
                    }
                }
            }
        }
        return dp[k][row + 2][column + 2];
    }
}
