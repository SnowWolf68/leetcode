package problemList.dp.solution;

/**
值域dp
为什么想到值域dp? 因为本题的数据范围 1 <= nums.length <= 1e5, 因此O(n ^ 2)显然不行
而又由于对于元素val来说, 其依赖的位置是 以 val - 1 和 val + 1 结尾的子序列, 因此想到基于值域的dp

其实这题本身就属于 特殊子序列dp 类型的题目

dp[i] 表示所有以元素i结尾的符合要求的子序列的元素和
count[i] 表示所有以元素i结尾的符合要求的子序列的数目
dp[i]: 分为两种情况: 1) 元素i自己单独作为一个序列  2) 元素i和 (i - 1) 或 (i + 1) 一起组成一个子序列
    1. 元素i自己单独作为一个子序列
        dp[i] += i;
        count[i] += 1;
    2. 元素i和 (i - 1) 或 (i + 1) 一起组成一个子序列
        dp[i] += count[i - 1] * i + dp[i - 1] + count[i + 1] * i + dp[i + 1];
        count[i] += count[i - 1] + count[i + 1];

 */
public class LC3351 {
    public int sumOfGoodSubsequences(int[] nums) {
        int max = 0, MOD = (int)1e9 + 7;
        for(int x : nums){
            max = Math.max(max, x);
        }
        long[] dp = new long[max + 1];
        long[] count = new long[max + 1];
        for(int x : nums){
            dp[x] = (dp[x] + x + (x - 1 >= 0 ? count[x - 1] * x + dp[x - 1] : 0) + (x + 1 <= max ? count[x + 1] * x + dp[x + 1] : 0)) % MOD;
            count[x] = (count[x] + 1 + (x - 1 >= 0 ? count[x - 1] : 0) + (x + 1 <= max ? count[x + 1] : 0)) % MOD;
        }
        long ret = 0;
        for(long x : dp) ret = (ret + x) % MOD;
        return (int)ret;
    }
}
