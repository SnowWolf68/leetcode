package problemList.dp.solution;

import java.util.Arrays;

/**
数据范围只有100, 提示可以写一个三次方的解法
先用O(n ^ 3)的时间处理每一个子串变成回文串所需要的最少操作次数
然后进行O(n ^ 2 * k)的dp即可
 */
public class LC1278 {
    public int palindromePartition(String s, int k) {
        int n = s.length(), INF = 0x3f3f3f3f;
        int[][] cnt = new int[n][n];
        for(int i = 0;i < n;i++){
            for(int j = i + 1;j < n;j++){
                int ii = i, jj = j;
                while(ii < jj){
                    if(s.charAt(ii) != s.charAt(jj)) cnt[i][j]++;
                    ii++; jj--;
                }
            }
        }
        int[][] dp = new int[k + 1][n + 1];
        Arrays.fill(dp[0], INF);
        for(int i = 0;i <= k;i++) dp[i][0] = INF;
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                for(int m = j;m >= 1;m--){
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][m - 1] + cnt[m - 1][j - 1]);
                }
            }
        }
        return dp[k][n];
    }
}
