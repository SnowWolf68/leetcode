package problemList.slidingWindow.solution;

/**
朴素想法: 划分型DP, 但是肯定T

其实从这题的数据范围也可以看出来, 1 <= prizePositions[i] <= 1e9, 0 <= k <= 1e9
这两个范围明摆着不是让你用dp的, 但是这里我还是用dp试了一下, 就当做是复习一下划分型DP了 

dp[i][j]: 考虑[0, i]区间的下标, 选择j个线段, 此时能够得到的最大价值
dp[i][j]: 此时根据 最后一个线段包不包括i这个下标,  有两种可能
        1. 如果最后一个线段包括i这个下标: 
            枚举最后一个线段的起始下标, 假设为k, 1 <= k <= i: 
            dp[i][j] = dp[k - 1][j - 1] + [k, i]区间的奖品数
            其中, [k, i]区间的奖品数可以使用前缀和在O(1)得到
        2. 如果最后一个线段不包括i这个下标: dp[i][j] = dp[i - 1][j];
        对于所有可能的k, 这里需要取一个max
初始化: k - 1和j - 1可能越界, 因此需要添加一行一列的辅助节点, 显然一行一列辅助节点的值都应该初始化成0
        并且由于这里奖品的下标是从1开始, 并且线段数量也是从1开始, 因此相当于自带了一行一列的辅助节点
return dp[n][m]
 */
public class LC2555_TLE {
    public int maximizeWin(int[] prizePositions, int k) {
        int n = prizePositions[prizePositions.length - 1] + 1;
        int[] pos = new int[n];
        for(int x : prizePositions){
            pos[x]++;
        }
        int[] pre = new int[n + 1];
        for(int i = 1;i <= n;i++){
            pre[i] = pre[i - 1] + pos[i - 1];
        }
        int m = 2;  // 选择2个线段
        int[][] dp = new int[n][m + 1];
        for(int i = 1;i < n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = dp[i - 1][j];
                for(int p = Math.max(1, i - k);p <= i;p++){
                    dp[i][j] = Math.max(dp[i][j], dp[p - 1][j - 1] + pre[i + 1] - pre[p]);
                }
            }
        }
        return dp[n - 1][m];
    }
}
