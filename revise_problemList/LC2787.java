package revise_problemList;

import java.util.ArrayList;
import java.util.List;

public class LC2787 {
    public int numberOfWays(int n, int x) {
        int MOD = (int)1e9 + 7;
        List<Integer> list = new ArrayList<>();
        for(int i = 1;pow(i, x) <= n;i++){
            list.add(pow(i, x));
        }
        int m = list.size();
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= n;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - list.get(i - 1) >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - list.get(i - 1)]) % MOD;
            }
        }
        return dp[m][n];
    }
    // n ^ x
    private int pow(int n, int x){
        if(x == 0) return 1;
        return n * pow(n, x - 1);
    }
}
