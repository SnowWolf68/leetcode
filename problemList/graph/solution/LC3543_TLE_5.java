package problemList.graph.solution;

/**
虽然正常来说在图中的dp如果确定不了dp顺序, 那么无法保证递推计算过程的正确性
但是注意到本题的状态表示   dp[i][j][w] 表示: 以i结尾, 长度为j, 权值和为w的路径是否存在
其中第二个维度j显然是递增的, 换句话说, dp[i][j][w]只会依赖dp[][j - 1][]的这些位置
因此只要按照第二个维度j升序的顺序递推, 就能保证递推顺序的正确性

具体来说, 由于这种递推方式和图并没有关系, 因此无需建图, 直接让j从1开始递增递推
每次递增到某一个j时, 遍历edges数组, 使用刷表法, 对于cur -> nxt的这条边来说, 更新dp[j][nxt][w] |= dp[j - 1][cur][w - weight(cur, nxt)]
初始化: dp[0][i][0] = true;

事实上, 也可以像前几种做法一样, 使用Set优化第三个维度, 避免遍历到无效位置, 但是这里我还是写一下三维boolean数组实现的方式
 */
public class LC3543_TLE_5 {
    public int maxWeight(int n, int[][] edges, int k, int t) {
        boolean[][][] dp = new boolean[k + 1][n][t];
        for(int i = 0;i < n;i++) dp[0][i][0] = true;
        for(int j = 1;j <= k;j++){
            for(int[] e : edges){
                for(int w = 0;w < t;w++){
                    if(w - e[2] >= 0) dp[j][e[1]][w] |= dp[j - 1][e[0]][w - e[2]];
                }
            }
        }
        int ret = -1;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < t;j++){
                if(dp[k][i][j] == true) ret = Math.max(ret, j);
            }
        }
        return ret;
    }
}
