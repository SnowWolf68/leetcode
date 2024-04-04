package problemList.dp.solution;

/**
输出具体方案: 先DP, 再构造
    dp[i][j] 表示同时以s1[0, i]区间子串, 以及s2[0, j]区间子串作为子序列的最短字符串长度
        dp[i][j]: 考虑最后一个字符是s1[i]还是s2[j]
            1. s1[i] == s2[j]: dp[i][j] = dp[i - 1][j - 1] + 1; 
            2. s1[i] != s2[j]: dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + 1;
        初始化: 辅助节点, 第一行: s1为空串, dp值为s2对应区间的长度, 第一列: s2为空串, dp值为s1对应区间的长度

    考虑如何根据dp表, 构造出最终的最短超序列
    具体的, 考虑s1[0, i]区间子串 和 s2[0, j]区间子串构成的超序列如何构造
    对于超序列的最后一个字符, 其显然必须是s1[i]或s2[j]中的一个字符, 如果s1[i] == s2[j], 那么直接将s1[i](或s2[j])拼接上去即可 (因为这俩字符相同)
    但是对于s1[i] != s2[j]的情况, 此时就需要分析到底是拼接s1[i]还是拼接s2[j], 这里我们就可以通过dp表来分析
        如果拼接s1[i], 那么剩下的就是s1[0, i - 1]区间子串以及s2[0, j]区间子串构成的超序列
        如果拼接s2[j], 那么剩下的就是s1[0, i]区间子串以及s2[0, j - 1]区间子串构成的超序列
        此时要求得到的超序列是最短的, 因此我们只需要比较dp[i - 1][j]以及dp[i][j - 1]的大小, 就可以知道是拼接s1[i]更短还是s2[j]更短
    即: 如果dp[i - 1][j] < dp[i][j - 1], 那么拼接s1[i], 反之拼接s2[j]

    注: 以上比较dp[i - 1][j]和dp[i][j - 1]的情况, 只发生在s1[i] != s2[j]的前提下, 如果s1[i] == s2[j], 那么直接拼接即可, 无需比较dp值
 */
public class LC1092 {
    public String shortestCommonSupersequence(String str1, String str2) {
        char[] s1 = str1.toCharArray(), s2 = str2.toCharArray();
        int m = s1.length, n = s2.length;
        int[][] dp = new int[m + 1][n + 1];
        for(int j = 0;j <= n;j++) dp[0][j] = j;
        for(int i = 0;i <= m;i++) dp[i][0] = i;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s1[i - 1] == s2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
            }
        }
        // 构造方案
        StringBuilder ret = new StringBuilder();
        int i = m, j = n;
        while(i > 0 && j > 0){
            if(s1[i - 1] == s2[j - 1]){
                ret.append(s1[i - 1]);
                i--;
                j--;
            }else{
                if(dp[i - 1][j] < dp[i][j - 1]){
                    ret.append(s1[i - 1]);
                    i--;
                }else{
                    ret.append(s2[j - 1]);
                    j--;
                }
            }
        }
        while(i > 0){
            ret.append(s1[i - 1]);
            i--;
        }
        while(j > 0){
            ret.append(s2[j - 1]);
            j--;
        }
        return ret.reverse().toString();
    }
}
