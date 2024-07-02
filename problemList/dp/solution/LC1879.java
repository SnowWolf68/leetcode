package solution;

import java.util.Arrays;

/**
dp[state] 表示从 nums2 中选出 state(这是一个集合) 这些元素, 此时最小的异或值之和
dp[state]: 枚举上一个选的元素下标, 假设为i
    dp[state] = min(dp[state & (~(1 << i))] + (nums1[Integer.bitCount(state) - 1] ^ nums2[i]));
    注意: (nums1[Integer.bitCount(state) - 1] ^ nums2[i]) 这里一定要加括号, 否则运算顺序不正确
初始化: dp[0] = 0;
return dp[mask - 1];
 */
public class LC1879 {
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int n = nums2.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int state = 1;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state] = Math.min(dp[state], dp[state & (~(1 << i))] + (nums1[Integer.bitCount(state) - 1] ^ nums2[i]));
            }
        }
        return dp[mask - 1];
    }
}
