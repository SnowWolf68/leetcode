package zuoshen.class066;

/**
题目8
不同的子序列 II
给定一个字符串 s，计算 s 的 不同非空子序列 的个数
因为结果可能很大，所以返回答案需要对 10^9 + 7 取余
字符串的 子序列 是经由原字符串删除一些（也可能不删除）
字符但不改变剩余字符相对位置的一个新字符串
例如，"ace" 是 "abcde" 的一个子序列，但 "aec" 不是
测试链接 : https://leetcode.cn/problems/distinct-subsequences-ii/


dp[i] = s[i]拼接在前面26个字母结尾的子序列后面 + s[i]单独

自己举个例子推一推
 */
public class Problem08_DistinctSubsequencesII {
    public int distinctSubseqII(String s) {
        int[] dp = new int[26];
        int MOD = (int)1e9 + 7;
        for(char c : s.toCharArray()){
            int sum = 0;
            for(int cnt : dp) sum = (sum +  cnt) % MOD;
            dp[c - 'a'] = (sum + 1) % MOD;
        }
        int sum = 0;
        for(int cnt : dp) sum = (sum + cnt) % MOD;
        return sum;
    }

    /**
     * 也可以单独维护sum, 省去内部O(26)的遍历
     */
    public int distinctSubseqII1(String s) {
        int[] dp = new int[26];
        int MOD = (int)1e9 + 7, sum = 0;
        for(char c : s.toCharArray()){
            int preDp = dp[c - 'a'];
            dp[c - 'a'] = (sum + 1) % MOD;
            sum = (sum + (dp[c - 'a'] - preDp + MOD) % MOD) % MOD;
        }
        return sum;
    }
}
