package problemList.dp.solution;

import java.util.*;

public class LC2266 {
    public int countTexts(String pressedKeys) {
        Map<Integer, Integer> cnt = new HashMap<>();
        cnt.put(2, 3); cnt.put(3, 3); cnt.put(4, 3); cnt.put(5, 3);
        cnt.put(6, 3); cnt.put(7, 4); cnt.put(8, 3); cnt.put(9, 4);
        char[] s = pressedKeys.toCharArray();
        int MOD = (int)1e9 + 7, n = s.length;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1;i <= n;i++){
            dp[i] = dp[i - 1];
            for(int j = i - 1;j >= Math.max(1, i - cnt.get(s[i - 1] - '0') + 1);j--){
                if(s[j - 1] == s[i - 1]){
                    dp[i] = (dp[i] + dp[j - 1]) % MOD;
                }else{
                    break;
                }
            }
        }
        return dp[n];
    }
}
