package solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
也是排列型状压DP, 但是枚举子集

dp[state] 表示当前nums选择情况为state时, 此时的最小不兼容性
dp[state]: 枚举最后一个子集, 这个子集要求 1) 大小为 nums.length / k   2) 不包含重复元素
    假设枚举到的子集为cur
    dp[state] = min(dp[state & (~cur)] + cur这一组中的不兼容性)
初始化: dp[0] = 0
return dp[mask - 1] == INF ? -1 : dp[mask - 1]

需要注意的是, 每次枚举state的子集cur的时候都需要计算cur这个子集的不兼容性, 因此可以将所有符合要求的cur的不兼容性预处理出来(不符合要求的cur的不兼容性可以初始化为INF)
 */
public class LC1681 {
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length, mask = 1 << n, size = n / k, INF = 0x3f3f3f3f;
        int[] info = new int[mask];
        Arrays.fill(info, INF);
        for(int state = mask - 1;state > 0;state = (state - 1) & (mask - 1)){
            if(Integer.bitCount(state) != size) continue;
            int min = INF, max = -INF;
            Set<Integer> set = new HashSet<>();
            boolean avai = true;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                if(set.contains(nums[i])) {
                    avai = false;
                    break;
                }
                set.add(nums[i]);
                min = Math.min(min, nums[i]);
                max = Math.max(max, nums[i]);
            }
            if(avai) info[state] = max - min;
        }
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int state = 1;state < mask;state++){
            if(Integer.bitCount(state) % size != 0) continue;
            for(int cur = state;cur > 0;cur = (cur - 1) & state){
                dp[state] = Math.min(dp[state], dp[state & (~cur)] + info[cur]);
            }
        }
        return dp[mask - 1] == INF ? -1 : dp[mask - 1];
    }
}
