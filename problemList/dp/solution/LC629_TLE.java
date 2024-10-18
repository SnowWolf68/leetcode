package problemList.dp.solution;

/**
dp[i][j] 表示考虑perm[0]到perm[i], 并且有j个逆序对, 此时不同的排列种数
dp[i][j]: 枚举perm[i]可以和前面的元素形成多少个逆序对, 假设可以形成p个, 其中 0 <= p <= min(i, j)
            有关p的上界的解释: 首先由于perm[0]到perm[i]区间中只有i + 1个元素, 因此最多形成i个逆序对, 因此p的上界的其中一个限制就是p <= i
            除此之外, 由于计算当前状态dp[i][j], 本来就表明此时逆序对最多计算到j就可以了, 再往后计算也没有必要了, 因此p的另外一个上界就是p <= j
            因此p的上界应该是min(i, j)
        dp[i][j] += dp[i - 1][j - p]
初始化: i - 1, j - p有可能越界, 因此添加一行一列的辅助节点
        第一行: 此时意味着排列中没有任何元素, 那么此时只有dp[0][0] = 1, 其余位置都是非法, 初始化为0
        第一列: 此时意味着当前没有任何逆序对, 那么显然此时只有一种排列, 即让所有元素升序排列, 因此初始化第一列所有元素都是1

    特别注意!!! 在添加了辅助节点之后, p的上界会发生变化, 原来的上界是min(i, j), 添加了辅助节点之后的上界变成min(i - 1, j)
                因为此时添加了辅助节点之后, dp[i][j]表示的排列中, 只有i个元素, 而不是i + 1个元素, 因此最多形成的逆序对数为i - 1, 而不是i个
return dp[n][k];

时间复杂度: O(n * k * min(n, k)), 本题的数据范围中, n和k都可以到1e3的范围, 这种朴素dp显然会T
 */
public class LC629_TLE {
    public int kInversePairs(int n, int k) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][k + 1];
        for(int i = 0;i <= n;i++) dp[i][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= k;j++){
                for(int p = 0;p <= Math.min(i - 1, j);p++){
                    dp[i][j] = (dp[i - 1][j - p] + dp[i][j]) % MOD;
                }
            }
        }
        return dp[n][k];
    }
}