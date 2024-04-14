package problemList.dp.solution;

import java.util.*;

/**
dp[i] 表示考虑划分[0, i]区间的子串, 此时剩下的字符最少是多少
    dp[i]: 枚举最后一个划分出来的字符串的起始位置j, 那么最后一个子串就是[j, i], 对于这个子串last, 此时有两种情况
        1. dict.contains(last): dp[i] = dp[j - 1];
        2. !dict.contains(last): dp[i] = dp[j - 1] + last.length();
    对于上面的两种情况, dp[i]需要取一个min, 并且对于所有可能的j的位置, dp[i]也需要取一个min
初始化: j - 1可能越界, 添加辅助节点, 此时意味着当前没有任何字符, 那么dp[0] = 0
return dp[n - 1];
 */
public class LC2707_1 {
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length(), INF = 0x3f3f3f3f;
        Set<String> dict = new HashSet<>();
        for(String str : dictionary) dict.add(str);
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = 1;j <= i;j++){
                String last = s.substring(j - 1, i);
                if(dict.contains(last)) dp[i] = Math.min(dp[i], dp[j - 1]);
                else dp[i] = Math.min(dp[i], dp[j - 1] + last.length());
            }
        }
        return dp[n];
    }
}