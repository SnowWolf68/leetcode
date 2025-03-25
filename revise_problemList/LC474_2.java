package revise_problemList;

import java.util.ArrayList;
import java.util.List;

/**
不超过 类型背包
 */
public class LC474_2 {
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
        for(int i = 1;i <= sz;i++){
            for(int j = 0;j <= m;j++){
                for(int k = 0;k <= n;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - list.get(i - 1)[0] >= 0 && k - list.get(i - 1)[1] >= 0) dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - list.get(i - 1)[0]][k - list.get(i - 1)[1]] + 1);
                }
            }
        }
        return dp[sz][m][n];
    }
}
