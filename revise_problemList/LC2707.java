package revise_problemList;

import java.util.HashSet;
import java.util.Set;

/**
dp[i]: 表示考虑s[0, i]区间的子串, 此时剩下的最少字符
dp[i]: 最后一个字符有两种可能: 
    1. 剩下: dp[i] = dp[i - 1] + 1;
    2. 分割: 枚举最后一个子串的起始下标j, 
        如果s.substring(j, i)在dict中, 那么 dp[i] = dp[j - 1];
    上面两种情况取一个min
初始化: i - 1, j - 1有可能越界, 添加辅助节点dp[0] = 0
return dp[n];
 */
public class LC2707 {
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        Set<String> set = new HashSet<>();
        for(String ss : dictionary) set.add(ss);
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = dp[i - 1] + 1;
            for(int j = 1;j <= i;j++){
                if(set.contains(s.substring(j - 1, i))) dp[i] = Math.min(dp[i], dp[j - 1]);
            }
        }
        return dp[n];
    }
}
