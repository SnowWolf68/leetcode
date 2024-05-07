package problemList.dp.solution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
第二种状态转移方式

第一种状态转移方式中, 我们主要考虑的是: 当前字符串可以拼接到前面哪一个字符串的后面, 形成一个符合要求的字符串链
由于本题的数据范围: words.length的范围到了1e3, 而words[i].length()的范围只有16, 那么我们还可以这样考虑: 
    对于words[i]这个字符串来说, 我们可以考虑去掉其中的某一个字符, 此时只需要看前面是否有以去掉这个字符后的字符串为结尾的字符串链即可, 具体来说: 
    由于这里words[i]去掉一个字符以后的字符串, 我们需要查找这个字符串在之前的字符串链中是否出现过, 因此这里dp表就不能再使用数组, 而是应该使用哈希表, 其中key为结尾的String, val为对应的字符串链的长度
        dp[str] 表示考虑以字符串str为结尾的最长字符串链的长度
            dp[str]: 遍历其中的所有字符, 假设遍历到的字符下标为j, 那么
                dp[str] = dp[str.substring(0, j) + str.substring(j + 1, str.length())] + 1;
            对于所有可能的j, 我们只需要取一个max即可
        初始化: 不需要初始化
        return max(dp[str]);

注意这里的dp的顺序应该也是按照字符串的长度来dp, 即首先对words中的字符串按照长度升序排序, 然后按照排序后的words中的字符串顺序进行dp

时间复杂度: O(n * logn + n * L ^ 2), 其中n = words.length, L = words[i].length()
注: substring()的时间复杂度为O(L)
 */
public class LC1048_2 {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Map<String, Integer> dp = new HashMap<>();
        int ret = 1;
        for(String word : words){
            for(int j = 0;j < word.length();j++){
                dp.put(word, Math.max(dp.getOrDefault(word, 1), 
                    dp.getOrDefault(word.substring(0, j) + word.substring(j + 1, word.length()), 0) + 1));
            }
            ret = Math.max(ret, dp.get(word));
        }
        return ret;
    }
}
