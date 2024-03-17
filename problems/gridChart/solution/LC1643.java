package problems.gridChart.solution;

import java.util.*;

/*
首先想到的肯定是dfs出所有的路径, 然后将这些路径排序之后取第k小
但是 dfs求出所有路径 这一步dfs的复杂度是2 ^ (m * n), 这显然太大了, 会TLE
考虑其他的方法
首先对于向下走的 'V' 以及向右走的 'H', 其字典序关系为 H < V
假设我们走到的某一个格子(i, j)
    1. 此时(i, j + 1)这个格子走到终点的方案数小于k, 那么意味着此时肯定是要向下走
        因为当前(i, j)向右走, 再走到终点的所有路径, 都比 当前(i, j)向下走, 再走到终点的路径要小 (因为H < V)
        并且当前我们是要找第k小的路径, 因此对于当前节点来说, 显然我们需要向下走
        注意: 此时有很关键的一步: 更新k, 因为此时相当于我们已经排除掉 "从(i, j + 1)走到终点的路径数目" 这么多的路径了
                因此继续向下找的时候, 实际上是找第 k - cnt(从(i, j + 1)走到终点的路径数目) 小的路径
    2. 此时(i, j + 1)这个格子走到终点的方案数大于等于k, 那么意味着此时肯定是要向右走, 因为显然此时第k小的路径是要被包含在 "向右走" 的这些路径中的
具体实现: 由于我们需要知道对于每一个节点(i, j), 其走到终点的路径的方案数, 这一点我们可以通过二维dp来求出
            然后只需要根据上面分析出来的规则, 进行模拟即可, 模拟的过程中记录走的路径, 最后得到的路径就是第k小的路径
时间复杂度: dp与处理的时间复杂度是O(m * n), 模拟的时间复杂度是O(m + n), 因此总的时间复杂度是O(max(m * n, m + n))
 */
public class LC1643 {
    public String kthSmallestPath(int[] destination, int k) {
        // m * n
        int m = destination[0] + 1, n = destination[1] + 1;
        int[][] dp = new int[m][n];
        for(int i = 0;i < n;i++) dp[m - 1][i] = 1;
        for(int i = 0;i < m;i++) dp[i][n - 1] = 1;
        for(int i = m - 2;i >= 0;i--){
            for(int j = n - 2;j >= 0;j--){
                dp[i][j] = dp[i][j + 1] + dp[i + 1][j];
            }
        }
        StringBuilder path = new StringBuilder();
        int i = 0, j = 0;
        while(true){
            if(j + 1 < n && dp[i][j + 1] >= k){
                path.append("H");
                j++;
            }else{
                path.append("V");
                k -= j + 1 < n ? dp[i][j + 1] : 0;
                i++;
            }
            if(i == m - 1 && j == n - 1) break;
        }
        return path.toString();
    }
}
