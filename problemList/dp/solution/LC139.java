package problemList.dp.solution;

import java.util.*;

public class LC139 {
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> set = new HashSet<>();
        for(String word : wordDict) set.add(word);
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= i;j++){
                if(set.contains(s.substring(j - 1, i))) dp[i] |= dp[j - 1];
            }
        }
        return dp[n];
    }
}
