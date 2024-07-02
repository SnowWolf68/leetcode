package solution;

/**
有了前面几道题的基础, 不难看出这题也是一个 排列形 相邻无关 的状压DP 问题
- 相当于保持学生顺序不变, 将mentor的顺序重排列

dp[state] 表示当前匹配情况为state, 此时的最大兼容性评分和
dp[state]: 枚举最后一个mentor, 假设为j
    dp[state] = max(dp[state & (~(1 << j))] + score[Integer.bitCount(state) - 1][j]);
初始化: dp[0] = 0;
return dp[mask - 1];
 */
public class LC1947 {
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        int n = students.length, m = students[0].length;
        // score[i][j] 表示下标为i的学生和下标为j的老师的兼容性评分
        int[][] score = new int[n][n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                for(int k = 0;k < m;k++){
                    if(students[i][k] == mentors[j][k]) score[i][j]++;
                }
            }
        }
        int mask = 1 << n;
        int[] dp = new int[mask];
        for(int state = 1;state < mask;state++){
            int bitCount = Integer.bitCount(state);
            for(int j = 0;j < n;j++){
                if(((state >> j) & 1) == 0) continue;
                dp[state] = Math.max(dp[state], dp[state & (~(1 << j))] + score[bitCount - 1][j]);
            }
        }
        return dp[mask - 1];
    }
}
