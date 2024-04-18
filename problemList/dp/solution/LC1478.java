package solution;

import java.util.*;

/**
通过数据范围可以知道, 邮筒的数量小于等于房屋的数量, 也就是说, 一个邮筒至少需要 "覆盖" 一个房屋
那么我们可以枚举最后一个邮筒覆盖了哪些房屋, 那么剩下的问题就变成了, 使用前面的邮筒覆盖剩下的房屋, 此时的最小距离总和, 这显然可以使用dp解决

dp[i][j] 表示使用[0, i]区间的邮筒, 覆盖[0, j]区间的房屋, 此时的最小距离总和
    dp[i][j]: 枚举最后一个邮筒覆盖哪些房屋, 假设覆盖[k, j]区间的房屋, 那么
        dp[i][j] = dp[i - 1][k - 1] + distance[k][j], 其中distance[k][j]指的是在[k, j]这个区间内放一个邮筒, 此时每个房屋到邮筒的总距离之和
        这里distance[k][j]我们可以通过dp的递推预处理得到
    对于所有可能的k, 只需要取一个min即可
初始化: 这里i - 1和k - 1都有可能越界, 因此需要添加一行一列的辅助节点
    第一行意味着此时没有任何邮筒, 那么只有dp[0][0] = 0, 其余位置一定存在至少一个房屋, 但是此时没有邮筒可以覆盖这些房屋, 因此第一行其余位置都是非法, 初始化为INF
    第一列意味着此时没有房屋, 那么此时第一列都初始化成0

详细说一下如何得到distance[][]数组: 
    显然此时把这个邮筒放在中位数的位置, 此时距离之和最小
    那么不难想到有递推式: distance[i][j] = distance[i + 1][j - 1] + houses[j] - houses[i];
        注意这里没有 + 1, 因为计算的是两个房屋之间的距离
    初始化: distance[i][i] = 0, 并且其余位置都初始化成0即可

注: 别忘了首先对houses进行排序, 这样能够保证计算距离时的正确性
 */
public class LC1478 {
    public int minDistance(int[] houses, int k) {
        Arrays.sort(houses);
        int n = houses.length, INF = 0x3f3f3f3f;
        int[][] distance = new int[n][n];
        for(int i = n - 2;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                distance[i][j] = distance[i + 1][j - 1] + houses[j] - houses[i];
            }
        }
        int[][] dp = new int[k + 1][n + 1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = i;j <= n;j++){
                dp[i][j] = INF;
                for(int m = i;m <= j;m++){
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][m - 1] + distance[m - 1][j - 1]);
                }
            }
        }
        return dp[k][n];
    }
}
