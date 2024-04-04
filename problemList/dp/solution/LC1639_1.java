package problemList.dp.solution;

/**
    首先看到 "如果用了word第j个字符串的第k个字符, 那么所有下标小于等于k的字符都不能够被使用", 不难想到应该按照下标对word中的字符分组
    其中, 第i组就是word的单词中, 所有下标为i的字符的集合
    然后考虑dp
    dp[i][j] 表示使用第[0, j]组的字符, 拼成target[0, i]区间的子串, 此时所有方案的种数   特别的, 由于每一组的字符再每一次拼target的时候, 只能用一次, 因此这里有i <= j
        dp[i][j]: 考虑target[i]是由哪些字符拼成
            1. 不使用第j组的字符: dp[i][j] = dp[i][j - 1];
            2. 使用第j组的字符: dp[i][j] = dp[i - 1][j - 1] * (第j组字符中, s[i]出现的次数)
                注意: 这里是用的 * , 而不是 + , 因为首先使用[0, j - 1]这些组的字符, 组成target[0, i - 1]区间的子串, 就有dp[i - 1][j - 1]这些可能
                    然后使用第j组的字符, 拼成target[i]这个字符, 又有cnt[j][target[i] - 'a']这么多可能, 根据乘法原理, 应该是dp[i - 1][j - 1] * cnt[j][target[i] - 'a']
            由于最后求的是 "方案数" , 因此对于上面两种情况, dp[i][j]应该求的是一个sum
    初始化: 通过状态表示的分析得到, 这里dp[i][j] 满足i <= j, 因此只需要填写dp表的 右上半部分 区域
        考虑添加辅助节点, 第一行: 此时target为空串, 那么此时方案只有一种, 第一行所有值都是1
            第一列: 此时没有任何字符, 那么此时除了dp[0][0] = 1, 其余位置都是0
    特别的: 由于我们只需要用到word分组后 "第j组字符中, s[i]出现的次数" , 因此我们对于每一组可以直接开一个26长度的数组, 来存放每一个字符出现的次数
 */
public class LC1639_1 {
    public int numWays(String[] words, String target) {
        int m = words[0].length(), n = target.length(), MOD = (int)1e9 + 7;
        long[][] cnt = new long[m][26];
        for(String word : words){
            for(int i = 0;i < m;i++){
                cnt[i][word.charAt(i) - 'a']++;
            }
        }
        // dp
        long[][] dp = new long[n + 1][m + 1];
        for(int j = 0;j <= m;j++) dp[0][j] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = dp[i][j - 1];
                dp[i][j] = (dp[i][j] + (dp[i - 1][j - 1] * cnt[j - 1][target.charAt(i - 1) - 'a']) % MOD) % MOD;
            }
        }
        return (int)dp[n][m];
    }
}
