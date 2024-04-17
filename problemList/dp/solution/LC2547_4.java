package solution;

import java.util.*;

/**
我想尝试一下不对状态转移方程进行变形, 直接做能不能使用线段树优化

首先我们需要将unique的计算放在dp的过程中

尝试使用线段树优化: 状态转移方程: dp[i] = Math.min(dp[i], dp[j - 1] + i - j + 1 + k - unique);
    因此线段树需要维护的信息就是dp[j - 1] + i - j + 1 + k - unique(j, i)
    对于线段树中的每一个元素的下标j, 我们将上式中的量分为三类: 1) 常数  2) 只与j有关  3) 和i有关
        (k + 1) + (dp[j - 1] - j) + (i - unique(j, i))
    发现这里上式除了dp[j - 1]之外, 还有j这一项, 其不好更新, 并且这里的i也不好更新

临时的结论: 因此如果不对状态转移方程进行变形, 就无法得到简化后的状态转移方程, 也就无法使用线段树进行优化
TODO: 上面结论的正确性
 */
public class LC2547_4 {
    public int minCost(int[] nums, int k) {
        int n = nums.length, max = Arrays.stream(nums).max().getAsInt(), INF = 0x3f3f3f3f;
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            int[] cnt = new int[max + 1];
            int unique = 0;
            for(int j = i;j > 0;j--){
                cnt[nums[j - 1]]++;
                if(cnt[nums[j - 1]] == 1) unique++;
                else if(cnt[nums[j - 1]] == 2) unique--;
                dp[i] = Math.min(dp[i], dp[j - 1] + i - j + 1 + k - unique);
            }
        }
        return dp[n];
    }
}
