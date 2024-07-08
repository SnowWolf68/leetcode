package problemList.stringHash.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
如何判断words[i]是不是连接词? 注意这里连接词的定义是: 由给定数组中的至少两个单词(可能相同)组成的字符串
因为连接词可能是由超过2个字符串组成的, 因此不能简单的枚举分割点进行分割
正确的做法是对每一个words[i]进行DP

dp[i] 表示考虑当前单词的[0, i]区间的子串, 这个子串 至多 是由给定数组中的几个单词组成的
dp[i]: 枚举最后一个单词的起始位置, 假设为j
    如果分割出来的最后一个单词在给定单词数组words中, 并且满足条件: 
    (dp[j - 1] > 0 || j - 1 == 0)
    因为我们需要保证, 在当前划分出来的最后一个字符串的 前面的字符串, 必须也是能够由给定的单词(字符串)数组words中的字符串拼接而成的(dp[j - 1] > 0), 或者前面根本就没有字符串(j - 1 == 0)
    如果两个条件都满足, 那么有
        dp[i] = max(dp[i], dp[j - 1] + 1)
初始化: 这里j - 1有可能越界, 因此需要添加一个辅助节点, 这个辅助节点意味着考虑当前字符串的一个空子串, 此时显然dp[0] = 0
如果dp[n - 1] >= 2, 那么说明当前单词是一个连接词, 其中n = words[i].length(), 即当前字符串的长度

对于每个单词进行DP的时间复杂度: 状态个数为n(n为当前字符串的长度), 单个状态的计算时间为: O(n * 判断某个字符串是否在words中出现过), 这里判断某个字符串是否在words中出现过, 可以使用O(1)的哈希完成
因此对于每个单词的判断时间为: O(n ^ 2)

一共的单词数为words.length, 因此总的时间复杂度为: O(words.length * words[i].length ^ 2)

通过数据范围可以估算出, 这个时间复杂度是可以通过的
 */
public class LC472_1 {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> set = new HashSet<>();
        for(String s : words) set.add(s);
        List<String> ret = new ArrayList<>();
        for(String str : words){
            int n = str.length();
            int[] dp = new int[n + 1];
            for(int i = 1;i <= n;i++){
                for(int j = 1;j <= i;j++){
                    String subString = str.substring(j - 1, i);
                    if(set.contains(subString) && (dp[j - 1] > 0 || j - 1 == 0)){
                        dp[i] = Math.max(dp[i], dp[j - 1] + 1);
                    }
                }
            }
            if(dp[n] >= 2) ret.add(str);
        }
        return ret;
    }
}
