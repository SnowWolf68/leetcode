package revise_problemList;

import java.util.HashSet;
import java.util.Set;

/**
1. source[i] == pattern[j]: 
    1. dp[i - 1][j] + (set.contains(i) ? 1 : 0);
    2. dp[i - 1][j - 1]
2. source[i] != pattern[j]: 
    dp[i - 1][j] + (set.contains(i) ? 1 : 0);
别忘了取max
 */
public class LC3316 {
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        Set<Integer> set = new HashSet<>();
        for(int i : targetIndices) set.add(i);
        int n = source.length(), m = pattern.length(), INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;
        for(int i = 1;i <= m;i++) dp[0][i] = -INF;
        for(int i = 1;i <= n;i++){
            dp[i][0] = dp[i - 1][0] + (set.contains(i - 1) ? 1 : 0);
        }
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = dp[i - 1][j] + (set.contains(i - 1) ? 1 : 0);
                if(source.charAt(i - 1) == pattern.charAt(j - 1)){
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1]);
                }
            }
        }
        return dp[n][m];
    }
}
