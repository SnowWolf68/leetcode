package problemList.dp.solution;

import java.util.HashSet;
import java.util.Set;

/**
dp[i] 表示组成target[i, target.length - 1]区间的字符串, 所需要的最少有效字符串数量
dp[i]: 类似划分型DP
        遍历接下来的这个有效字符串的长度, 假设为j
        如果target.substring(i, i + j)这个子串是一个有效字符串, 那么dp[i] = dp[i + 1] + 1
        对上面所有可能的j取一个min
填表顺序: 从后往前填写
初始化: 添加辅助节点, dp[target.length] = 0;
return dp[0];

时间复杂度: 预处理的时间复杂度O(words.length * word[i].length()), 本题数据范围sum(words[i].length) <= 10^5, 即这部分的时间复杂度最多为O(10 ^ 5)
          dp的时间复杂度: O(n ^ 3)  注意这里dp的时间复杂度是O(n ^ 3)而不是O(n ^ 2), 其原因在于target.substring()这个方法的时间复杂度是O(n), 而不是O(1)
          本题中数据范围n <= 5 * 10 ^ 3, 三次方的算法显然会T
 */
public class LC3291_TLE_1 {
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

        for(int i = n - 1;i >= 0;i--){
            dp[i] = INF;
            for(int j = 0;j <= n - i;j++){
                if(hashSet.contains(target.substring(i, i + j))) dp[i] = Math.min(dp[i], dp[i + j] + 1);
            }
        }
        return dp[0] == INF ? -1 : dp[0];
    }
}
