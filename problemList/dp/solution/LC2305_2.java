package solution;

import java.util.Arrays;

/**
空间优化的版本
 */
public class LC2305_2 {
    public int distributeCookies(int[] cookies, int k) {
        int n = cookies.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[] cnt = new int[mask];
        for(int state = 0;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                cnt[state] += cookies[i];
            }
        }
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int i = 0;i < k;i++){
            for(int state = mask - 1;state >= 0;state--){
                for(int p = state;p != 0;p = (p - 1) & state){
                    dp[state] = Math.min(dp[state], Math.max(dp[state & (~p)], cnt[p]));
                }
            }
        }
        return dp[mask - 1];
    }
}
