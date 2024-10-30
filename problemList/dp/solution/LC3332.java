package problemList.dp.solution;

// 测试链接: https://leetcode.cn/contest/biweekly-contest-142/problems/maximum-points-tourist-can-earn/
/**
比较明显的一个DP
dp[i][j] 表示考虑[0, i]这些天, 并且下标为i的这一天待在j这个城市, 此时能够获得的最多点数
dp[i][j]: 枚举前一天待在哪个城市, 假设前一天待在的城市的下标是k, 此时根据前一天和当前这一天待在的城市是否相同, 有两种情况
        1. k == j: dp[i][j] = dp[i - 1][k] + stay[i][j];
        2. k != j: dp[i][j] = dp[i - 1][k] + travel[k][j];  显然对于所有可能的k, 这里需要取一个max
初始化: 这里可能越界的只有 i - 1 , 但是在本题的情境下, 添加辅助节点并不是很好, 因此这里我们手动初始化第一行
        第一行意味着此时是下标为0的天, 即第一天, 那么显然此时第一天不能获得任何点数, 因此第一行全都是0
return max(dp[n][k]), 其中0 <= k < n
 */
public class LC3332 {
    public int maxScore(int n, int k, int[][] stay, int[][] travel) {
        int[][] dp = new int[k + 1][n];
        for(int i = 1;i <= k;i++){
            for(int j = 0;j < n;j++){
                for(int p = 0;p < n;p++){
                    if(p == j){
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][p] + stay[i - 1][j]);
                    }else{
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][p] + travel[p][j]);
                    }
                }
            }
        }
        int ret = 0;
        for(int i = 0;i < n;i++) ret = Math.max(ret, dp[k][i]);
        return ret;
    }
}
