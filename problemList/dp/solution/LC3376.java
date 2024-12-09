package problemList.dp.solution;

import java.util.Arrays;
import java.util.List;

/**
相邻无关 状压DP

之所以是 相邻无关 而不是 相邻相关 , 是因为 已经开锁的数量为 bitCount(state), 那么对应的x就应该为 1 + bitCount(state) * k

dp[state] 表示当前已经破解的锁的集合为state, 此时破解锁的最小时间
dp[state]: 枚举最后一个要开的锁的下标, 假设为i, 那么开最后这把锁的时间为: ceil(strength[i] / (1 + bitCount(state ^ (1 << i)) * k))
    dp[state] = min(dp[state ^ (1 << i)] + ceil(strength[i] / (1 + bitCount(state ^ (1 << i)) * k)))
初始化: dp[0] = 0;
return dp[mask - 1];
 */
public class LC3376 {
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size(), mask = 1 << n, INF = 0x3f3f3f3f;
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int state = 1;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state] = Math.min(dp[state], dp[state ^ (1 << i)] + (int)Math.ceil((double)strength.get(i) / (1 + Integer.bitCount(state ^ (1 << i)) * k)));
            }
        }
        return dp[mask - 1];
    }
}
