package problemList.dp.solution;

import java.util.Arrays;

/**
这题是一个典型的 前缀和优化dp 类型的题

观察这个循环
    for(int p = 0;p <= Math.min(i - 1, j);p++){
        dp[i][j] = (dp[i - 1][j - p] + dp[i][j]) % MOD;
    }
    对于dp[i][j]来说, 其依赖的状态都是在dp[i - 1]这一行中, 并且从上面这个循环的计算方式中可以看出
    dp[i][j] = sum(dp[i - 1][j - min(i - 1, j) : j]), 换句话说, dp[i][j]等于dp表上一行中 某一段元素的和
    因此可以使用前缀和将这个计算过程优化到O(1)

由于这题的前缀和优化比较简单, 因此具体实现见代码

需要注意的是, 如果只用一个preSum前缀和数组, 那么在使用preSum计算dp[i][j], 以及更新preSum的时候, 会出现冲突
因此这里我们在每次i遍历的时候, 都再额外使用一个nxtSum数组, 来记录当前dp表i这一行中的dp值的前缀和
当dp表i这一行全都计算完毕之后, 再让 preSum = nxtSum, 实现preSum前缀和数组的更新

使用了前缀和进行优化之后, 时间复杂度就来到了 O(n * k)
 */
public class LC629 {
    public int kInversePairs(int n, int k) {
        int MOD = (int)1e9 + 7;
        int[][] dp = new int[n + 1][k + 1];
        for(int i = 0;i <= n;i++) dp[i][0] = 1;
        int[] preSum = new int[k + 2];
        Arrays.fill(preSum, 1);
        preSum[0] = 0;
        for(int i = 1;i <= n;i++){
            int[] nxtSum = new int[k + 2];
            Arrays.fill(nxtSum, 1);
            nxtSum[0] = 0;
            for(int j = 1;j <= k;j++){
                dp[i][j] = (preSum[j + 1] - preSum[j - Math.min(i - 1, j)] + MOD) % MOD;
                nxtSum[j + 1] = (nxtSum[j] + dp[i][j]) % MOD;
            }
            preSum = nxtSum;
        }
        return dp[n][k];
    }
}