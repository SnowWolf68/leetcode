package problemList.dp.solution;

public class LC1639_2 {
    public int numWays(String[] words, String target) {
        int m = words[0].length(), n = target.length(), MOD = (int)1e9 + 7;
        long[][] cnt = new long[m][26];
        for(String word : words){
            for(int i = 0;i < m;i++){
                cnt[i][word.charAt(i) - 'a']++;
            }
        }
        // dp
        long[][] dp = new long[n + 1][m + 1];
        for(int j = 0;j <= m;j++) dp[0][j] = 1;
        for(int i = 1;i <= n;i++){
            // 由于i <= j, 因此这里让j从i开始循环即可
            // 这样实际上只需要填写一半的dp表, 从而优化运行时间: 65ms -> 38ms
            for(int j = i;j <= m;j++){
                dp[i][j] = dp[i][j - 1];
                dp[i][j] = (dp[i][j] + (dp[i - 1][j - 1] * cnt[j - 1][target.charAt(i - 1) - 'a']) % MOD) % MOD;
            }
        }
        return (int)dp[n][m];
    }
}
