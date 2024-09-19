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

时间复杂度: 预处理的时间复杂度O(words.length * word[i].length())
          dp的时间复杂度: O(n ^ 2)
总的时间复杂度很高, 会T
 */
public class LC3291_TLE {
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
                if(hashSet.contains(target.subSequence(i, i + j))) dp[i] = Math.min(dp[i], dp[i + j] + 1);
            }
        }
        return dp[0] == INF ? -1 : dp[0];
    }
}
