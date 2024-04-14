package problemList.dp.solution;

import java.util.*;

/**
转化一下: 剩下的字符最少 -> 保留的字符最多
dp[i] 表示划分[0, i]区间的子串, 此时保留的字符最多是多少
    dp[i]: 枚举最后一个划分出来的子串的起始位置j, 那么最后一个子串的范围是[j, i], 对于这个子串last是否在dict中, 有两种可能
        1. dict.contains(last): dp[i] = dp[j - 1] + last.length();
        2. !dict.contains(last): dp[i] = dp[j - 1];
    对于上面两种情况, 需要取一个max, 对于所有可能的j, 也需要取一个max
初始化: 添加一个辅助节点, dp[0] = 0;
return s.length() - dp[n - 1];
 */
public class LC2707_2 {
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        Set<String> dict = new HashSet<>();
        for(String str : dictionary) dict.add(str);
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= i;j++){
                String last = s.substring(j - 1, i);
                if(dict.contains(last)) dp[i] = Math.max(dp[i], dp[j - 1] + last.length());
                else dp[i] = Math.max(dp[i], dp[j - 1]);
            }
        }
        return n - dp[n];
    }
}