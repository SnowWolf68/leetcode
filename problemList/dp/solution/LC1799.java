package solution;

/**
dp[state] 表示 删除的集合为 state 时, 此时获得的最大分数
dp[state]: 枚举最后两个删除的元素, 假设下标分别为i, j
    dp[state] = max(dp[state & (~((1 << i) | (1 << j)))] + gcd(nums[i], nums[j]) * Integer.bitCount(state) / 2);
初始化: dp[0] = 0;
return dp[mask - 1];
 */
public class LC1799 {
    public int maxScore(int[] nums) {
        int n = nums.length, mask = 1 << n;
        // 为了加快计算, 这里预处理出所有可能用到的gcd, gcd[i][j] = gcd(nums[i], nums[j]);
        int[][] gcd = new int[n][n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                gcd[i][j] = gcd(nums[i], nums[j]);
            }
        }
        int[] dp = new int[mask];
        for(int state = 1;state < mask;state++){
            int bitCount = Integer.bitCount(state);
            // state集合中的元素数量必须是偶数
            if(bitCount % 2 != 0) continue;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                for(int j = 0;j < n;j++){
                    if(i == j || (((state) >> j) & 1) == 0) continue;
                    dp[state] = Math.max(dp[state], dp[state & (~((1 << i) | (1 << j)))] + gcd[i][j] * bitCount / 2);
                }
            }
        }
        return dp[mask - 1];
    }

    public int gcd(int num1, int num2) {
        while (num2 != 0) {
            int temp = num1;
            num1 = num2;
            num2 = temp % num2;
        }
        return num1;
    }
}
