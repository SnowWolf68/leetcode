package solution;

import java.util.*;

/**
考虑优化: 
    dp[i] = min(dp[j - 1] + i - j + 1 - unique(j, i) + k)
    -> dp[i] = i + 1 + k + min(dp[j - 1] - j - unique(j, i));
    假设我们规定g[i] = dp[i] - i - 1, 那么有dp[i] = g[i] + i + 1
    -> dp[i] - i - 1 = k + min(dp[j - 1] - j - unique(j, i))
    -> g[i] = k + min(g[j - 1] - unique(j, i))

时间复杂度: O(n ^ 2)  这里只是修改了一下状态转移方程, 总的时间复杂度还是没有变的
 */
public class LC2547_2 {
    public int minCost(int[] nums, int k) {
        int n = nums.length, INF = 0x3f3f3f3f;
        int[] g = new int[n + 1];
        g[0] = -1;
        int max = Arrays.stream(nums).max().getAsInt();
        for(int i = 1;i <= n;i++){
            int[] cnt = new int[max + 1];
            int min = INF, unique = 0;
            for(int j = i;j > 0;j--){
                cnt[nums[j - 1]]++;
                if(cnt[nums[j - 1]] == 1) unique++;
                else if(cnt[nums[j - 1]] == 2) unique--;
                min = Math.min(min, g[j - 1] - unique);
            }
            g[i] = k + min;
        }
        return g[n] + n + 1;
    }
}
