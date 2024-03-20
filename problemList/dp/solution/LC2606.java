package problemList.dp.solution;

import java.util.*;

public class LC2606 {
    public int maximumCostSubstring(String s, String chars, int[] vals) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0;i < chars.length();i++) map.put(chars.charAt(i), i);
        int n = s.length();
        int[] dp = new int[n];
        dp[0] = getCost(s.charAt(0), map, vals);
        int ret = dp[0];
        for(int i = 1;i < n;i++){
            int curCost = getCost(s.charAt(i), map, vals);
            dp[i] = Math.max(curCost, dp[i - 1] + curCost);
            ret = Math.max(ret, dp[i]);
        }
        return Math.max(ret, 0);
    }
    private int getCost(char c, Map<Character, Integer> map, int[] vals){
        if(map.containsKey(c)) return vals[map.get(c)];
        else return c - 'a' + 1;
    }
}
