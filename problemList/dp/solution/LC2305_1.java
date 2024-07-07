package solution;

import java.util.Arrays;

/**
这题的关键是要能够看出来: 一个孩子可以分多个零食包
    通过这个信息就能够知道, 这应该是一个枚举子集的子集的状压DP
dp[i][state] 表示将state集合中的饼干分给[0, i]区间的孩子, 此时最小不公平程度
dp[i][state]: 枚举最后一个孩子分配到的饼干集合p, 那么有
    dp[i][state] = min(max(dp[i - 1][state & (~p)], cnt(p)))   , 其中cnt(p)表示p集合中的饼干总数
    需要注意的是, 内层取max, 目的是计算当前最后一个孩子分配p这个饼干集合下的不公平程度
    外层取min, 目的是计算最后一个孩子分配所有可能的p的情况下的最小不公平程度
初始化: 这里i - 1有可能越界, 因此添加一行辅助节点, 第一行意味着此时没有任何孩子, 因此初始化dp[0][0] = 0, 其余位置都是非法, 初始化为INF
return dp[k - 1][mask];     // k表示孩子总数
 */
public class LC2305_1 {
    public int distributeCookies(int[] cookies, int k) {
        int n = cookies.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[] cnt = new int[mask];
        for(int state = 0;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                cnt[state] += cookies[i];
            }
        }
        int[][] dp = new int[k + 1][mask];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int state = 0;state < mask;state++){
                dp[i][state] = INF;
                for(int p = state;p != 0;p = (p - 1) & state){
                    dp[i][state] = Math.min(dp[i][state], Math.max(dp[i - 1][state & (~p)], cnt[p]));
                }
            }
        }
        return dp[k][mask - 1];
    }
}
