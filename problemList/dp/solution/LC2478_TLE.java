package problemList.dp.solution;

/**
TLE代码, 没加优化, 没取MOD, 仅做参考
 */
public class LC2478_TLE {
    public int beautifulPartitions(String s, int k, int minLength) {
        int n = s.length();
        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                if(isPrime(s.charAt(j - 1) - '0')) continue;
                for(int m = 1;m <= j - minLength + 1;m++){
                    if(isPrime(s.charAt(m - 1) - '0')) dp[i][j] += dp[i - 1][m - 1];
                }
            }
        }
        return dp[k][n];
    }
    private boolean isPrime(int num){
        if(num == 2 || num == 3 || num == 5 || num == 7) return true;
        else return false;
    }
}
