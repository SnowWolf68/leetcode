package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
输出具体方案: 先dp, 再构造
注意这里需要输出具体方案, 所以状态表示只能是 "以i下标结尾的子序列" , 只有这样才能倒序构造出具体方案
dp[i] 表示考虑在[0, i]区间选一个以i结尾的, 符合要求的子序列, 此时这个子序列的最长长度
    dp[i]: 枚举上一个选的下标j, 范围[0, i - 1], 这里j需要满足要求, 如果满足, 那么有
        dp[i] = dp[j] + 1;
        对于所有可能的j, 这里需要取一个max
初始化: j可以保证不越界, 所以这里可以不初始化
return max(dp[i]);

然后再根据dp[]进行构造:
    具体的: 倒序构造, i从dp[n - 1]开始向前遍历, 如果遇到j满足dp[j] + 1 = dp[i], 那么意味着i下标前面的下标是j, 依次构造即可

时间复杂度: O(n ^ 2 * len), 其中n = words.length, len = word[i].length()
 */
public class LC2901 {
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        boolean[][] check = new boolean[n][n];
        for(int i = 0;i < n;i++){
            for(int j = i + 1;j < n;j++){
                String s1 = words[i], s2 = words[j];
                if(s1.length() != s2.length()) check[i][j] = false;
                else{
                    int cnt = 0;
                    for(int k = 0;k < s1.length();k++){
                        if(s1.charAt(k) != s2.charAt(k)) cnt++;
                    }
                    check[i][j] = cnt == 1 ? true : false;
                }
            }
        }
        int[] dp = new int[n];
        int maxIdx = 0, max = 0;
        for(int i = 0;i < n;i++){
            dp[i] = 1;
            for(int j = 0;j < i;j++){
                if(check[j][i] && groups[j] != groups[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            if(dp[i] > max) {
                max = dp[i];
                maxIdx = i;
            }
        }
        List<String> ret = new ArrayList<>();
        ret.add(words[maxIdx]);
        int preIdx = maxIdx;
        maxIdx--;
        while(maxIdx >= 0){
            if(dp[maxIdx] + 1 == max && check[maxIdx][preIdx] && groups[maxIdx] != groups[preIdx]){
                max--;
                ret.add(0, words[maxIdx]);
                preIdx = maxIdx;
            }
            maxIdx--;
        }
        return ret;
    }
}
