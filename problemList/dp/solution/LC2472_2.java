package solution;

/**
优化: j从后往前枚举, 当找到一个长度大于等于k的回文子串时, 就不需要再往前找了, break即可

原因: 由于长为l的回文子串一定包含长为l - 2的回文子串, 并且每次遍历i的时候, 都会首先使用dp[i - 1]来初始化dp[i], 即dp[i] = dp[i - 1]
    那么我们需要证明的是: 在考虑[l, r]区间的回文子串时, 所计算出来的dp[r], 在满足[l - 1, r + 1]区间的子串也是回文子串时, 使用dp[r + 1] = dp[r]来更新dp[r + 1]是正确的, 这里的前提是r - l + 1 >= k, 即前提是找到了一个长度 >= k的回文子串
    证明如下: 
        1. 假设l - 1下标的字符只存在于[l - 1, r + 1]区间的回文子串中, 即l - 1下标的字符只存在于最后的这一个回文子串中: 那么显然有dp[l - 1] = dp[l - 2]
        2. 假设l - 1下标的字符不只存在于[l - 1, r + 1]区间的回文子串中, 还存在于[..., l - 1]区间的回文子串中: 那么有dp[l - 1] >= dp[l - 2]
        又由于dp[r] = dp[l - 1], dp[r + 1] = dp[l - 2], 因此有dp[r] >= dp[r + 1], 也就是说, 使用dp[r + 1] = dp[r]来更新得到的dp[r + 1]一定是正确的
        如果不明白考虑两个例子: ababcba, abaabcba, 其中k = 3
通过上面的证明我们就可以得到优化的关键: 计算dp[i]时, 我们需要从i - k + 1向前找, 当找到第一个回文串时, 就不需要继续向前找了, 直接break, 继续计算dp[i + 1]就可以了, 这里我们的dp[i] = dp[i - 1]就可以保证每次更新的正确性

时间复杂度: 通过优化, dp的时间复杂度可以降到O(n * k), 但是这里dp预处理每一个子串还是需要O(n ^ 2)的时间, 因此总的时间复杂度还是O(n ^ 2)
    如果想让总的时间复杂度也降到O(n * k), 就需要优化 "判定某一个区间的子串是不是回文" 的过程, 这个过程我们可以使用 "中心拓展法" 来进行优化
 */
public class LC2472_2 {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] info = new boolean[n][n];
        for(int i = 0;i < n;i++) info[i][i] = true;
        for(int i = n - 1;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                if(s.charAt(i) == s.charAt(j)) info[i][j] = i + 1 == j ? true : info[i + 1][j - 1];
                else info[i][j] = false;
            }
        }
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = dp[i - 1];
            for(int j = i - k + 1;j >= 1;j--){
                if(info[j - 1][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j - 1] + 1);
                    break;
                }
            }
        }
        return dp[n];
    }
}
