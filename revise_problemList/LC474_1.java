package revise_problemList;

import java.util.ArrayList;
import java.util.List;

/**
恰好装满 类型背包
 */
public class LC474_1 {
    public int findMaxForm(String[] strs, int m, int n) {
        List<int[]> list = new ArrayList<>();
        for(String s : strs){
            int cnt0 = 0;
            for(char c : s.toCharArray()){
                if(c == '0') cnt0++;
            }
            list.add(new int[]{cnt0, s.length() - cnt0});
        }
        int sz = list.size(), INF = 0x3f3f3f3f;
        int[][][] dp = new int[sz + 1][m + 1][n + 1];
        dp[0][0][0] = 0;
        for(int j = 1;j <= m;j++){
            for(int k = 1;k <= n;k++){
                dp[0][j][k] = -INF;
            }
        }
        for(int i = 1;i <= sz;i++){
            for(int j = 0;j <= m;j++){
                for(int k = 0;k <= n;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - list.get(i - 1)[0] >= 0 && k - list.get(i - 1)[1] >= 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - list.get(i - 1)[0]][k - list.get(i - 1)[1]] + 1);
                }
            }
        }
        int ret = 0;
        for(int j = 0;j <= m;j++){
            for(int k = 0;k <= n;k++){
                ret = Math.max(ret, dp[sz][j][k]);
            }
        }
        return ret;
    }
}
