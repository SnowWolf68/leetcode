package zuoshen.class066;

/**
题目7
环绕字符串中唯一的子字符串
定义字符串 base 为一个 "abcdefghijklmnopqrstuvwxyz" 无限环绕的字符串
所以 base 看起来是这样的：
"..zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd.."
给你一个字符串 s ，请你统计并返回 s 中有多少 不同、非空子串 也在 base 中出现
测试链接 : https://leetcode.cn/problems/unique-substrings-in-wraparound-string/

 */
public class Problem07_UniqueSubStringsInWraparoundString {
    public int findSubstringInWraproundString(String s) {
        int n = s.length(), ret = 0;
        int[] dp = new int[n];  // dp[i]: s以下标i结尾的子串在base中出现的最长长度 
        dp[0] = 1;
        int[] cnt = new int[26];    // cnt[i]: 以字母i结尾的子串的最长长度 (目的是去重)
        cnt[s.charAt(0) - 'a'] = 1;
        for(int i = 1;i < n;i++){
            dp[i] = 1;
            if(s.charAt(i - 1) + 1 == s.charAt(i) || (s.charAt(i - 1) == 'z' && s.charAt(i) == 'a')){
                dp[i] += dp[i - 1];
            }
            cnt[s.charAt(i) - 'a'] = Math.max(cnt[s.charAt(i) - 'a'], dp[i]);
        }
        for(int num : cnt) ret += num;
        return n == 1 ? 1 : ret;
    }
}
