package problemList.dp.solution;

/**
* 字符串问题dp的经典状态表示

O(n ^ 2)的解法比较好想, 看成子序列问题, 定义dp[i]表示以下标i结尾的符合要求的子序列的最长长度
计算dp[i]的时候, 只需要遍历[0, i - 1]的所有下标, 然后判断i是否可以跟在j后面即可
这样做的时间复杂度到了O(n ^ 2), 在这题的数据范围1e5下, 肯定会T

这里就用到了字符串问题的经典的状态表示

dp[ch] 表示 以ch这个字符结尾 的符合要求的子序列的最长长度
    那么我们从前往后遍历字符串s, 假设当前s[i] = ch
    dp[ch]: 遍历[ch - k, ch + k]区间的所有字符prvCh, 考虑ch跟在prvCh后面即可
        但是需要注意的是: 这里在一次遍历prvCh的时候, 一定至少一次遍历到ch这个字符
        所以下面的这种状态转移方程是错误的: dp[ch] = Math.max(dp[ch], dp[prvCh] + 1]);
        因为当遍历到当前这个字符ch时, 那么此时有两种情况: 
            1) 这个ch的下标就是i: 那么此时显然我们不能让下标为i的字符继续更新下标为i的字符的dp值, 因此此时我们不能考虑dp[ch]
            2) 这个ch的下标不是i: 此时由于这两个ch在s中的下标不同, 因此可以使用dp[ch]来更新dp[ch]
        对于这两种情况我们不好分类处理, 因此我们可以这样做: 
        首先在遍历prvCh的时候, 使用max{dp[prvCh]}来更新dp[ch]
        然后在遍历prvCh完之后, 再让dp[ch]++, 这样就可以完美解决上述两种情况产生的不一致问题
    注意这里dp[ch]有可能会被多次更新到, 因此我们在每次更新的时候, 都需要取一个max
初始化: ch - k, ch + k我们可以在计算的时候手动判断来保证其不越界, 因此这里我们不需要考虑越界问题
    考虑如何初始化才能够保证 "初始化的值能够保证后续更新的正确性"
    结合上面状态转移的分析, 不难想到, 这里我们只需要将所有值都初始化为0即可
return max(dp[ch])
 */
public class LC2370 {
    public int longestIdealString(String s, int k) {
        int[] dp = new int[26];
        int ret = 0;
        for(char c : s.toCharArray()){
            int ch = c - 'a';
            for(int prvCh = Math.max(0, ch - k); prvCh <= Math.min(25, ch + k); prvCh++){
                dp[ch] = Math.max(dp[ch], dp[prvCh]);
            }
            dp[ch]++;
            ret = Math.max(ret, dp[ch]);
        }
        return ret;
    }
}
