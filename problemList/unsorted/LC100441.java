package problemList.unsorted;

import java.util.Arrays;

// https://leetcode.cn/contest/biweekly-contest-144/problems/shift-distance-between-two-strings/

/**
dp[i] 表示将 s[0, i] 区间的子串切换为 t[0, i] 区间的子串 所需要的最小代价
dp[i]: 首先判断s[i]和t[i]是否相同
    1. s[i] == t[i]: 此时不需要切换, dp[i] = dp[i - 1]
    2. s[i] != t[i]: 此时需要切换, 对于如何切换, 此时有两种选择: 
        1. 将s[i]向后切换: 此时根据t[i]和s[i]的大小关系, 需要分两种情况来计算切换代价: 
            1. t[i] > s[i]: 此时切换代价为: nextCost[s[i], t[i] - 1] 区间的元素和
            2. t[i] < s[i]: 此时切换代价为: sum(nextCost) - nextCost[t[i], s[i] - 1] 区间的元素和
        2. 将s[i]向前切换: 此时也是需要分两种情况来计算: 
            1. t[i] > s[i]: 切换代价: sum(prevCost) - prevCost[s[i] + 1, t[i]] 区间的元素和
            2. t[i] < s[i]: 切换代价: prevCost[t[i] + 1, s[i]] 区间的元素和
        对于上面的这些可能, 此时需要取min
    假设这里我们计算出来了此时让s[i]切换成t[i]所需要的最小代价, 假设为 curCost , 那么状态转移方程为: 
    dp[i] = dp[i - 1] + curCost;
初始化: 这里i - 1有可能越界, 因此添加辅助节点 dp[0] , 并且初始化dp[0] = 0
return dp[n - 1];
 */
public class LC100441 {
    public long shiftDistance(String s, String t, int[] nextCost, int[] prevCost) {
        int n = s.length(), m = nextCost.length;
        int[] dp = new int[n + 1], nxtSum = new int[m + 1], prevSum = new int[m + 1];
        for(int i = 1;i <= m;i++){
            nxtSum[i] = nxtSum[i - 1] + nextCost[i - 1];
            prevSum[i] = prevSum[i - 1] + prevCost[i - 1];
        }
        for(int i = 1;i <= n;i++){
            if(s.charAt(i - 1) == t.charAt(i - 1)) dp[i] = dp[i - 1];
            else{
                int curCost = Integer.MAX_VALUE;
                if(t.charAt(i - 1) > s.charAt(i - 1)){
                    curCost = Math.min(nxtSum[t.charAt(i - 1) - 'a'] - nxtSum[s.charAt(i - 1) - 'a'], (prevSum[m] - (prevSum[t.charAt(i - 1) - 'a' + 1] - prevSum[s.charAt(i - 1) - 'a' + 1])));
                }else{
                    // System.out.println(m);
                    // System.out.println((nxtSum[m] - (nxtSum[s.charAt(i - 1) - 'a'] - nxtSum[t.charAt(i - 1) - 'a'])));
                    // System.out.println((prevSum[s.charAt(i - 1) - 'a' + 1] - prevSum[t.charAt(i - 1) - 'a' + 1]));
                    curCost = Math.min(curCost, Math.min(nxtSum[m] - (nxtSum[s.charAt(i - 1) - 'a'] - nxtSum[t.charAt(i - 1) - 'a']), prevSum[s.charAt(i - 1) - 'a' + 1] - prevSum[t.charAt(i - 1) - 'a' + 1]));
                }
                // if(i == 1){
                //     System.out.println((prevSum[n] - (prevSum[t.charAt(i - 1) - 'a' + 1] - prevSum[s.charAt(i - 1) - 'a' + 1])));
                // }
                dp[i] = dp[i - 1] + curCost;
                // System.out.println("i = " + i + " curCost = " + curCost);
            }
        }
        // System.out.println(Arrays.toString(dp));
        return dp[n];
    }

    public static void main(String[] args) {
        String s = "leet", t = "code";
        int[] nextCost = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int[] prevCost = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        System.out.println(new LC100441().shiftDistance(s, t, nextCost, prevCost));
    }
}