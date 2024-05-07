package problemList.dp.solution;

import java.util.Arrays;

/**
第一种状态转移方式

注意: 这里选出来的字符串链, 不要求必须是words的子序列, 也就是说, 我们可以以任意的顺序, 从words中选单词
这里最长字符串链, 只和结尾的字符串有关, 因此只需要一维的dp即可
并且由于字符串链中的单词显然要求前一个字符串的长度要小于后一个字符串的长度, 因此我们可以首先对words中的字符串按照长度排序, 然后按照排序后的words中的顺序进行dp
    dp[i] 表示以下标i的字符串结尾的最长字符串链的长度
        dp[i]: 枚举前面的每个字符串, 假设其下标为j
            dp[i] = dp[j] + 1;
        对于所有可能的j, 只需要取一个max即可
    初始化: 不需要初始化
    return max(dp[i]);

时间复杂度: O(n * logn + n ^ 2 * m), 其中n = words.length, m = words[i].length()
 */
public class LC1048_1 {
    public int longestStrChain(String[] words) {
        int n = words.length;
        Arrays.sort(words, (o1, o2) -> o1.length() - o2.length());
        int[] dp = new int[n];
        int ret = 1;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (isPredecessor(words[j], words[i])) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
    /**
     * 判断s1是否是s2的前身
     * @param s1
     * @param s2
     * @return
     */
    private boolean isPredecessor(String s1, String s2) {
        if(s1.length() + 1 != s2.length()) return false;
        int diffCnt = 0;
        int i = 0, j = 0;
        while(i < s1.length() && j < s2.length()){
            if(s1.charAt(i) != s2.charAt(j)){
                diffCnt++;
                j++;
            }else{
                i++; j++;
            }
        }
        return diffCnt <= 1;
    }
}
