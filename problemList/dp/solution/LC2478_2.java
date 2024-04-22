package problemList.dp.solution;

/**
第二种前缀和的写法
    上一种前缀和的思路是: 在上一行计算dp[i][j]的同时, 计算当前行的前缀和数组sum
    到了计算下一行的时候, 使用上一行计算出来的前缀和数组sum来更新当前的dp值

    这里我们前缀和的思路是: 由于这里的前缀和求的都是 "从0开始的前缀和" , 因此我们可以将前缀和数组优化成一个变量sum
    在计算当前行的dp[i][j]时, 同时遍历上一行的dp值, 并且使用sum来维护上一行的前缀和, 并用这个前缀和来更新当前行的dp[i][j]

从代码量上来看, 这种写法更简洁
 */
public class LC2478_2 {
    public int beautifulPartitions(String s, int k, int minLength) {
        int n = s.length(), MOD = (int)1e9 + 7;
        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= k;i++){
            int sum = 0;
            for(int j = 1;j <= n;j++){
                if(j - minLength >= 0 && isPrime(s.charAt(j - minLength) - '0')) sum = (sum + dp[i - 1][j - minLength]) % MOD;
                if(!isPrime(s.charAt(j - 1) - '0')) dp[i][j] = sum;
            }
        }
        return dp[k][n];
    }
    private boolean isPrime(int num){
        if(num == 2 || num == 3 || num == 5 || num == 7) return true;
        else return false;
    }
}
