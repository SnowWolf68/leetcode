package problemList.dp.solution;

/**
也是这个类型的板子题

dp[state][i] 表示当前nums中元素的选择情况为state, 并且当前最后一个元素选择的是下标i, 此时符合要求的排列总数目
dp[state][i]: 枚举上一个选择的元素下标, 假设为j
    首先判断是否满足条件: nums[i] % nums[j] == 0 || nums[j] % nums[i] == 0
    如果条件满足, 那么进行转移: dp[state][i] += dp[state & (~(1 << i))][j];
初始化: 初始化所有 bitCount(state) == 1 的行, 即 dp[1 << i][i] = 1, dp表的其余位置都是0
return sum(dp[mask - 1][i])
 */
public class LC2741 {
    public int specialPerm(int[] nums) {
        int n = nums.length, mask = 1 << n, MOD = (int)1e9 + 7;
        int[][] dp = new int[mask][n];
        for(int i = 0;i < n;i++) dp[1 << i][i] = 1;
        int ret = 0;
        for(int state = 2;state < mask;state++){
            int bitCount = Integer.bitCount(state);
            if(bitCount <= 1) continue;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                for(int j = 0;j < n;j++){
                    if(j == i || ((state >> j) & 1) == 0) continue;
                    if(nums[i] % nums[j] == 0 || nums[j] % nums[i] == 0) dp[state][i] = (dp[state][i] + dp[state & (~(1 << i))][j]) % MOD;
                }
                if(state == mask - 1) ret = (ret + dp[state][i]) % MOD;
            }
        }
        return ret;
    }
}
