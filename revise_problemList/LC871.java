package revise_problemList;

import java.util.Arrays;

public class LC871 {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][n + 1];
        Arrays.fill(dp[0], startFuel);
        for(int i = 1;i <= n;i++) dp[0][i] = -INF;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                if(dp[i - 1][j - 1] > stations[i - 1][1]){
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + stations[i][1]);
                }
            }
        }
        // for(int i = 0;i <= n;i++){
        //     for(int j = 0;j <= n;j++){
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        for(int i = 0;i <= n;i++){
            if(dp[n][i] > target) return i;
        }
        return -1;
    }
}
