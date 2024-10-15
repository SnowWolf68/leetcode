package problemList.dp.solution;

import java.util.Arrays;

/**
有了1884的dp的基础, 这题应该比较好想

dp[i][j] 表示使用i个鸡蛋, 当前楼高为j, 确定此时的f所需要的最少操作次数
dp[i][j]: 枚举第一个鸡蛋葱几楼扔下来, 假设为p (1 <= p <= j) , 此时根据第一个鸡蛋碎或不碎, 有两种可能
        1. 碎: 接下来转化为从[1, p - 1]中确定f
            dp[i][j] = dp[i - 1][p - 1] + 1;
        2. 不碎: 接下来转化为从[p + 1, j]的范围内确定f
            dp[i][j] = dp[i][j - p] + 1;
        由于要保证一定能够计算出f, 因此上面两种情况要取max
    对于p的所有可能, 此时需要取一个min
初始化: 有可能越界的下标是 i - 1, p - 1, 因此需要添加一行一列辅助节点, 由于i, j本来就应该从1开始, 因此实际上自带一行一列辅助节点
        第一行: 此时不使用任何鸡蛋, 那么只有dp[0][0] = 0合法 (因为楼高为0时, 此时f无意义, 也不需要计算), 其余位置都是非法, 初始化为INF
        第一列: 此时楼高为0, 那么也不需要计算f, 初始化第一列全部为0
填表顺序: 从上往下, 从左往右
return dp[p][n];

如果直接按照上面这样写, 会T
直接dp的时间复杂度是 O(k * n ^ 2), 题目中给的数据范围是 1 <= k <= 100, 1 <= n <= 1e4, 朴素dp肯定会超时
 */
public class LC887_TLE {
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        int INF = 0x3f3f3f3f;
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                for(int p = 1;p <= j;p++){
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][p - 1] + 1, dp[i][j - p] + 1));
                }
            }
        }
        return dp[k][n];
    }
}
