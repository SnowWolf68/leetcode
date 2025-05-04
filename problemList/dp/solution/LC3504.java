package problemList.dp.solution;

/**
dp和核心在于具体问题本身, 而不在于dp有多难

这题一开始做的时候我错误的认为两个串的分割点就是回文中心, 因此就有了LC3503_WA中错误的代码
后来发现回文中心并不好确定, 于是就不会做了

灵神题解中的做法: 
假设s串中选择的子串为x, t串中选择的子串为y, 那么最终拼成的串就是 x + y
接下来根据回文中心位置的不同, 分为三种情况来处理: 
1. x.length == y.length     2. x.length > y.length      3. x.length < y.length

第一种情况 (x.length == y.length): 
    此时x, y应该满足的条件为: x = reverse(y), 其中x, y分别为s, t中的子串
    并且此时最终的回文串长度为 2 * x.length (或2 * y.length)
    因此问题转换成了: 求s, reverse(t)中的最长公共子串的长度
        怎么求s, t中的最长公共子串长度? -- dp (类似 LCS)
        dp[i][j]: s串中以下标i结尾的子串, 和t中以下标j结尾的子串的最长公共子串长度
        dp[i][j]: 
            1. s[i] == t[j]: dp[i][j] = dp[i - 1][j - 1] + 2;
            2. s[i] != t[j]: dp[i][j] = 0;
        初始化: 添加一行一列辅助节点, 辅助节点的值全都初始化为0
    有意思的是, 这里计算出来的dp表不仅在这里用到, 在后面两种情况中也会用到
第二种情况: (x.length > y.length)
    此时x, y需要满足的条件是: 1) x中长度为x.length - y.length的后缀必须是回文串   2) 假设x中长度为y.length的前缀为ss, 则应该有ss = reverse(y)
    注意到2)这个条件可以用第一种情况中计算出来的dp表, 因此这里我们可以首先考虑第一个条件
    枚举s中所有的回文串, 假设这个回文串就是 1) 中的回文串, 即这个回文串就是x中长度为x.length - y.length的回文后缀
        假设当前枚举到的这个回文串的范围是[l, r], 那么2)中的前缀ss的结束下标就应该是l - 1
        因此最终的回文串长度就是 (r - l + 1) + 2 * (s中以下标l - 1结尾的子串和reverse(t)中的最长公共子串长度)
            上式中第一部分 (r - l + 1) 这部分的长度就是 1) 中的回文串的长度
            第二部分 2 * ... 的长度就是 2) 中ss + reverse(y)的长度
        因此问题转化为求: s中以下标l - 1结尾的子串和reverse(t)中的最长公共子串长度 --> 对应max(dp[l - 1])  其中这里的dp就是第一种情况中计算出来的dp表
    "枚举s中所有的回文串" 这一步可以使用中心扩展法实现, 依次枚举 2 * n - 1 个回文中心 (n个奇回文中心, n - 1个偶回文中心, 这里偶回文中心不包括起始字符之前和结束字符之后的两个回文中心, 只考虑位于串内部的偶回文中心)
第三种情况: (x.length < y.length)
    由于回文串具有对称性, 因此可以看做是 s' = reverse(t), t' = reverse(s) 转化之后的第二种情况  (s'对应第二种情况中的s, t'对应第二种情况中的t)

在具体实现中, 由于后两种情况的处理方式相同, 因此这里将第二种情况的处理方式单独抽取成一个函数, 即代码中的calc(s, t)
 */
public class LC3504 {
    public int longestPalindrome(String s, String t) {
        int m = s.length(), n = t.length();
        String tt = reverse(t);
        int[][] dp = new int[m + 1][n + 1];
        int[] maxL = new int[m + 1];
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s.charAt(i - 1) == tt.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                maxL[i] = Math.max(maxL[i], dp[i][j]);
            }
        }

        // x.length == y.length
        int ret1 = 0;
        for(int i = 0;i <= m;i++) ret1 = Math.max(ret1, 2 * maxL[i]);

        // x.length > y.length
        int ret2 = calc(s, t);

        // x.length < y.length
        int ret3 = calc(reverse(t), reverse(s));

        return Math.max(ret1, Math.max(ret2, ret3));
    }

    private int calc(String s, String t){
        int ret = 0, m = s.length(), n = t.length();
        String tt = reverse(t);
        int[][] dp = new int[m + 1][n + 1];
        int[] maxL = new int[m + 1];
        for(int i = 1;i <= m;i++){
            for(int j = 1;j <= n;j++){
                if(s.charAt(i - 1) == tt.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                maxL[i] = Math.max(maxL[i], dp[i][j]);
            }
        }
        for(int i = 0;i <= 2 * m - 1;i++){
            int l = -1, r = -1, curLen = -1;
            if(i % 2 == 0){
                // 奇回文
                l = i / 2 - 1;
                r = i / 2 + 1;
                curLen = 1;
            }else{
                // 偶回文
                l = i / 2;
                r = (i + 1) / 2;    // 等价于 i / 2 上取整
                curLen = 0;
            }
            while(l >= 0 && r <= m - 1 && s.charAt(l) == s.charAt(r)){
                l--;
                r++;
                curLen += 2;
            }
            ret = Math.max(ret, curLen + (l >= 0 ? 2 * maxL[l + 1] : 0));
        }
        return ret;
    }

    private String reverse(String s){
        StringBuilder sb = new StringBuilder();
        for(int i = s.length() - 1;i >= 0;i--) sb.append(s.charAt(i));
        return sb.toString();
    }

    public static void main(String[] args) {
        // String s = "abcde", t = "ecdba";     // 5
        // String s = "b", t = "aaaa";             // 4
        // String s = "mrb", t = "r";              // 3
        String s = "tsd", t = "mdsgmq";            // 5
        System.out.println(new LC3504().longestPalindrome(s, t));
    }
}
