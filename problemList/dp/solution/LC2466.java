package problemList.dp.solution;

public class LC2466 {
    public int countGoodStrings(int low, int high, int zero, int one) {
        int MOD = (int)1e9 + 7;
        int[] dp = new int[high + 1];
        dp[0] = 1;
        for(int i = 1;i <= high;i++){
            if(i - zero >= 0) dp[i] = (dp[i] + dp[i - zero]) % MOD;
            if(i - one >= 0) dp[i] = (dp[i] + dp[i - one]) % MOD;
        }
        int ret = 0;
        for(int i = low;i <= high;i++) ret = (ret + dp[i]) % MOD;
        return ret;
    }
}
