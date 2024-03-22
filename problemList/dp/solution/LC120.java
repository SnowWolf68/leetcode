package problemList.dp.solution;

import java.util.*;

/**
dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle[i][j];
 */
public class LC120 {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        if(n == 1) return triangle.get(0).get(0);
        int[][] dp = new int[n][n];
        dp[0][0] = triangle.get(0).get(0);
        int ret = Integer.MAX_VALUE;
        for(int i = 1;i < n;i++){
            for(int j = 0;j <= i;j++){
                if(j == i) dp[i][j] = dp[i - 1][j - 1] + triangle.get(i).get(j);
                else if(j == 0) dp[i][j] = dp[i - 1][j] + triangle.get(i).get(j);
                else dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle.get(i).get(j);
                if(i == n - 1){
                    ret = Math.min(ret, dp[i][j]);
                }
            }
        }
        return ret;
    }
}
