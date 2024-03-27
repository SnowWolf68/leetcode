package problemList.dp.solution;

import java.util.*;

/**
使用 "不超过" 类型背包的做法

"恰好装满" 类型背包 和 "不超过" 类型背包 的区别在于: 1) 初始化  2) 返回值
其余地方都没有区别
 */
public class LC474_2 {
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        List<int[]> list = new ArrayList<>();
        for(String s : strs){
            int[] cur = new int[2];
            for(char c : s.toCharArray()){
                if(c == '0') cur[0]++;
                else cur[1]++;
            }
            list.add(cur);
        }
        int[][][] dp = new int[len + 1][m + 1][n + 1];
        for(int i = 1;i <= len;i++){
            for(int j = 0;j <= m;j++){
                for(int k = 0;k <= n;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - list.get(i - 1)[0] >= 0 && k - list.get(i - 1)[1] >= 0)
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - list.get(i - 1)[0]][k - list.get(i - 1)[1]] + 1);
                }
            }
        }
        return dp[len][m][n];
    }
}
