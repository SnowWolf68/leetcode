package zuoshen.class066;

/**
题目6
最长有效括号
给你一个只包含 '(' 和 ')' 的字符串
找出最长有效（格式正确且连续）括号子串的长度。
测试链接 : https://leetcode.cn/problems/longest-valid-parentheses/

dp[i]: s以下标i结尾的子串的最长匹配长度
dp[i]: 
    1. s[i] = '(' : dp[i] = 0
    2. s[i] = ')' : 
        dp[i]: 由于当前是')', 则前面肯定要有一个'('与之闭合, 即看一下s[i - dp[i - 1] - 1]是否是'('
            如果是, 那么可以闭合, 拼接上 dp[i - 1] + 2 的长度, 并且前面还可以拼接上再往前面的dp[i - dp[i - 1] - 2]
            如果不是, 说明不能闭合, dp[i] = 0
dp[0] = 0;
return max(dp[i]);
 */
public class Problem06_LongestValidParentheses {
    public int longestValidParentheses(String s) {
        int n = s.length(), ret = 0;
        int[] dp = new int[n];
        for(int i = 0;i < n;i++){
            if(s.charAt(i) == '(') dp[i] = 0;
            else{
                if(i - 1 >= 0 && i - dp[i - 1] - 1 >= 0){
                    if(s.charAt(i - dp[i - 1] - 1) == '('){
                        dp[i] = dp[i - 1] + 2 + ((i - dp[i - 1] - 2) >= 0 ? dp[i - dp[i - 1] - 2] : 0);
                    }else dp[i] = 0;
                }
            }
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }
}
