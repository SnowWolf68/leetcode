package problems.dp.solution;

import java.util.*;

/**
题意: 给你两个字符串s, t, 求s的子串x, t的子序列y, 满足x和y相等的不同(x, y)的对数
dp[i][j] 表示考虑s中以下标i结尾的子串, t中[0, j]区间的子序列, 此时满足要求的不同(x, y)的对数
    注意这里的状态表示: 对于s的子串来说, 是 "以下标i结尾的子串" , 而对于t的子序列来说, 是 "[0, j]区间的子序列"
    dp[i][j]: 考虑dp[i][j]的来源
        1. 从dp[i][j - 1]中继承: dp[i][j] += dp[i][j - 1]
        2. 如果s[i] == t[j], 那么s[i]和t[j]分别单独作为一个字符串来进行匹配: dp[i][j] += 1;
        3. 如果s[i] == t[j], 可以从dp[i - 1][j - 1]这里分别拼接上s[i]和t[j], 从而延长得来: dp[i][j] += dp[i - 1][j - 1];
初始化: 添加一行一列的辅助节点, 辅助节点全都初始化成0
return dp[i][n - 1];     // 其中(0 <= i < m), 即dp表最后一列的元素和
 */
public class CF163A {

    private int solve(String s, String t){
        int m = s.length(), n = t.length(), MOD = (int)1e9 + 7;
        int[][] dp = new int[m + 1][n + 1];
        int ret = 0;
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] = ((dp[i][j - 1] + dp[i - 1][j - 1]) % MOD + 1) % MOD;
                else dp[i][j] = dp[i][j - 1];
                if(j == n) ret = (ret + dp[i][j]) % MOD;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String t = sc.nextLine();
        System.out.println(new CF163A().solve(s, t));
    }
}
