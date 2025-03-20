package revise_problemList;

public class LCSF02 {
    public int minRemainingSpace(int[] N, int V) {
        int n = N.length;
        boolean[][] dp = new boolean[n + 1][V + 1];
        for(int i = 0;i <= n;i++) dp[i][0] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= V;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - N[i - 1] >= 0) dp[i][j] |= dp[i - 1][j - N[i - 1]];
            }
        }
        // for(int i = 0;i <= n;i++){
        //     for(int j = 0;j <= V;j++){
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        for(int j = V;j >= 0;j--){
            if(dp[n][j]) return V - j;
        }
        return -1;
    }
}
