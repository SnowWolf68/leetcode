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
    
    注意: 这里我们需要保证(j - 1)不会越界, 因此还需要对j进行初始化, 这里对j的初始化我们直接初始化j == 0这一列即可, 即初始化第一列
        对于第一列, 此时意味着当前数字总和为0, 需要注意的是: 对于一个骰子来说, 其点数是从1开始, 意味着只要用到了骰子, 那么点数和至少是1, 因此点数和为0只有一种情况, 就是没有用任何骰子
        因此第一列我们初始化dp[0][0] = 1, 其余位置都是0

    注意: 由于j是从1开始遍历, 那么s[0], s[1]两个位置就无法在dp的过程中更新, 因此我们需要手动保证这两个位置的值的正确性
        对于s[0], 其一直都是0
        对于s[1], 其等于dp[i][0], 那么只有i == 0时, s[1] == dp[0][0] == 1, 其余情况s[1] == dp[i][0] == 0

时间复杂度: O(n * target)
 */
public class LC1155_2 {
    public int numRollsToTarget(int n, int k, int target) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;
        int[] s = new int[target + 2];
        Arrays.fill(s, 1);
        s[0] = 0;
        for(int i = 1;i <= n;i++){
            int[] preS = s.clone();
            s[1] = 0;
            for(int j = 1;j <= target;j++){
                if(j >= k) dp[i][j] = (preS[j - 1 + 1] - preS[j - k - 1 + 1] + MOD) % MOD;
                else dp[i][j] = preS[j - 1 + 1];
                s[j + 1] = (s[j] + dp[i][j]) % MOD;
            }
        }
        return dp[n][target];
    }
}
