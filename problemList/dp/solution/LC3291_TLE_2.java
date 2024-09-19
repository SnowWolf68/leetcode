package problemList.dp.solution;

import java.util.HashSet;
import java.util.Set;

/**

尝试从前往后dp
dp[i] 表示组成target[0, i]区间的字符串, 所需要的最少有效字符串数量
dp[i]: 枚举最后一个字符串的长度, 假设为j    
        如果i - j >= 0 (这题提前添加辅助节点, dp[0] 表示此时target中没有任何字符)
        并且target.substring(i - 1, j)是一个前缀, 那么dp[i] = dp[i - j] + 1;
        对所有可能的j取一个min
填表顺序: 从前往后
初始化: dp[0]是添加的辅助节点, 此时意味着target中没有任何字符, 那么此时初始化dp[0] = 0
return dp[target.length()];
 */
public class LC3291_TLE_2 {
    public int minValidStrings(String[] words, String target) {
        int n = target.length(), m = words.length, INF = 0x3f3f3f3f;
        int[] dp = new int[n + 1];

        Set<String> hashSet = new HashSet<>();
        for(String word : words){
            for(int i = 0;i < word.length();i++){
                String subString = word.substring(0, i + 1);
                if(!hashSet.contains(subString)) hashSet.add(subString);
            }
        }
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = 0;j <= i;j++){
                if(i - j >= 0 && hashSet.contains(target.substring(i - j, i))){
                    dp[i] = Math.min(dp[i], dp[i - j] + 1);
                }
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }
}
