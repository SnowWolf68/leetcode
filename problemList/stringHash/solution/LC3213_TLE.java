package problemList.stringHash.solution;
// https://leetcode.cn/problems/construct-string-with-minimum-cost/description/

/**
dp[i] 表示构成target[0, i]区间的子串, 所需要的最小代价
dp[i]: 枚举最后一个子串在words中的下标, 假设为j, 那么有
    dp[i] = min(dp[i - words[j].length()] + costs[j])
初始化: 这里i - words[j].length()可以手动保证不越界, 因此不需要初始化
    需要注意的是, 如果i - words[j].length() == -1, 说明此时target[0, i]区间只有words[i]这一个字符串
    那么此时dp[-1]应该等于0

    或者另外的一种初始化方式就是添加一个辅助节点, dp[0]表示此时target中没有任何字符的情况, 那么此时dp[0] = 0
    如果添加了一个辅助节点, 那么后续的代码中就需要注意下标的映射关系
return dp[n - 1];

时间复杂度: 状态个数: n, 单个状态的计算时间: O(words.length * words[i].length())
    因此总的时间复杂度为: O(n * words.length * words[i].length)
    TLE
 */
public class LC3213_TLE {
    public int minimumCost(String target, String[] words, int[] costs) {
        int n = target.length(), INF = 0x3f3f3f3f;
        // 这里采用添加辅助节点的初始化方式
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = 0;j < words.length;j++){
                if(i - words[j].length() >= 0 && words[j].equals(target.substring(i - words[j].length(), i))){
                    dp[i] = Math.min(dp[i], dp[i - words[j].length()] + costs[j]);
                }
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }
}
