package revise_problemList;

public class LC1049_1 {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length, sum = 0;
        for(int num : stones) sum += num;
        boolean[][] dp = new boolean[n + 1][2 * sum + 1];
        dp[0][sum] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= 2 * sum;j++){
                if(j - stones[i - 1] >= 0) dp[i][j] |= dp[i - 1][j - stones[i - 1]];
                if(j + stones[i - 1] <= 2 * sum) dp[i][j] |= dp[i - 1][j + stones[i - 1]];
            }
        }
        for(int j = sum;j <= 2 * sum;j++){
            if(dp[n][j]) return j - sum;
        }
        return -1;
    }
}
