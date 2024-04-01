package problemList.dp.solution;

import java.util.*;

/**
优化: 考虑如何优化掉p的循环, 在O(1)的时间内计算出dp[i][j]
    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j - 2] + ... + dp[i - 1][j - Math.min(k, j)];
    不难看出, 这显然就是dp[i - 1]这一行中一段区间的和, 那么可以使用前缀和优化
    由于结束位置下标为j - Math.min(k, j), 因此需要讨论两种情况: 
        1. Math.min(k, j) == k, 即k <= j: 此时dp[i][j] = dp[i - 1]这一行中[j - k, j - 1]区间的和
        2. Math.min(k, j) == j, 即k > j: 此时dp[i][j] = dp[i - 1]这一行中[0, j - 1]区间的和
    如果在计算dp[i - 1]这一行的dp值的同时, 计算出这一行的前缀和s[], 那么dp[i][j]可以表示为: 
        1. k <= j: dp[i][j] = s[j - 1 + 1] - s[j - k - 1 + 1];  // 这里s[]数组的下标 + 1 是因为前缀和数组本身的下标就是原数组的下标 + 1
        2. k > j: dp[i][j] = s[j - 1 + 1];
    
    通过上面的分析, dp表的初始化就不能只是初始化i, 还需要初始化j
    添加一行一列的辅助节点, 第一行dp[0][0] = 1, 其余位置都是0
        第一列意味着此时和为0, 那么此时只有 全都不选 一种可能, 因此第一列全都初始化为1
 */
public class LC1155_2 {
    // TODO 有问题
    public int numRollsToTarget(int n, int k, int target) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][target + 1];
        for(int i = 0;i <= n;i++) dp[i][0] = 1;
        int[] s = new int[target + 2];
        Arrays.fill(s, 1);
        s[0] = 0;
        for(int i = 1;i <= n;i++){
            int[] preS = s.clone();
            for(int j = 1;j <= target;j++){
                if(j >= k) dp[i][j] = (preS[j - 1 + 1] - preS[j - k - 1 + 1] + MOD) % MOD;
                else dp[i][j] = preS[j - 1 + 1];
                s[j + 1] = (s[j] + dp[i][j]) % MOD;
            }
        }
        for(int i = 0;i <= n;i++){
            for(int j = 0;j <= target;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[n][target];
    }
}
