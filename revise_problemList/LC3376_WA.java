package revise_problemList;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * dp[state][x][curStrength]:
 *      1. dp[state][x][curStrength - 1] + 1
 *      2. dp[state | (1 << i)][x + k][0] +
 * 
 */
public class LC3376_WA {
    private int sum = 0, INF = 0x3f3f3f3f;
    private int[][][] memo;
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size(), mask = (1 << n) - 1, maxStrength = 0;
        for(int s : strength) {
            sum += s;
            maxStrength = Math.max(maxStrength, s);
        }
        memo = new int[mask + 1][85][sum + 1];
        for(int i = 0;i <= mask;i++){
            for(int j = 0;j < 85;j++){
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(strength, k, mask, 0, 1, 0);
    }

    private int dfs(List<Integer> strength, int k, int mask, int state, int x, int curStrength){
        if(state == mask) return 0;
        if(curStrength > sum) return INF;
        if(memo[state][x][curStrength] != -1) return memo[state][x][curStrength];
        int ret = INF, n = strength.size();
        // 什么都不做
        ret = dfs(strength, k, mask, state, x, curStrength + x) + 1;
        // 尝试开第i把锁
        for(int i = 0;i < n;i++){
            if(curStrength >= strength.get(i)){
                ret = Math.min(ret, dfs(strength, k, mask, state | (1 << i), x + k, 0) + 1);
            }
        }
        memo[state][x][curStrength] = ret;
        return ret;
    }
}
