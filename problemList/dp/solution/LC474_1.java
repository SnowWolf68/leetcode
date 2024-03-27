package problemList.dp.solution;

import java.util.*;

/**
使用 "恰好装满" 类型背包的做法

预处理strs[i]中0和1的个数, 使用int[]存储在List<int[]>中
dp[i][j][k] 表示考虑[0, i]区间的str, 当前0的个数为j, 1的个数为k, 此时最大子集的长度
    dp[i][j][k]: 对于str[i], 考虑选或不选
        1. 选: dp[i][j][k] = dp[i - 1][j - str[i][0]][k - str[i][1]] + 1;
                // 其中, str[i][0] 表示str[i]中0的个数, str[i][1]表示str[i]中1的个数
        2. 不选: dp[i][j][k] = dp[i - 1][j][k];
    上面两者取一个max即可
初始化: 初始化i这个维度, 添加辅助节点, 这里需要添加一面的辅助节点, i == 0表示此时没有任何str, 那么此时只有dp[0][0][0] = 0, 其余位置都是非法, 初始化为-INF
return max(dp[strs.length][..][..]);     // 即dp表i == strs.length这一面的所有dp值的最大值
 */
public class LC474_1 {
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length, INF = 0x3f3f3f3f;
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
        for(int j = 0;j <= m;j++){
            for(int k = 0;k <= n;k++){
                dp[0][j][k] = -INF;
            }
        }
        dp[0][0][0] = 0;
        int ret = 0;
        for(int i = 1;i <= len;i++){
            for(int j = 0;j <= m;j++){
                for(int k = 0;k <= n;k++){
                    dp[i][j][k] = dp[i - 1][j][k];
                    if(j - list.get(i - 1)[0] >= 0 && k - list.get(i - 1)[1] >= 0)
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - list.get(i - 1)[0]][k - list.get(i - 1)[1]] + 1);
                    if(i == len) ret = Math.max(ret, dp[i][j][k]);
                }
            }
        }
        return ret;
    }
}
