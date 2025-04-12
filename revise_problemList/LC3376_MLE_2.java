package revise_problemList;

import java.util.Arrays;
import java.util.List;

/**
 * 改进: 可以强制规定: 如果curStrength到达了还未开锁的锁的能量的最小值, 那么强制开这把锁
 * 因此curStrength的上限可以被减小到 max(strength)
 * 
 * 其实这种思路一定会爆空间, 因为strength[i]能到1e6, 因此无法记忆化strength[i]这个参数
 */
public class LC3376_MLE_2 {
    private int INF = 0x3f3f3f3f, maxStrength = 0;
    private int[][][] memo;
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size(), mask = (1 << n) - 1;
        for(int s : strength) {
            maxStrength = Math.max(maxStrength, s);
        }
        memo = new int[mask + 1][85][2 * maxStrength + 1];
        for(int i = 0;i <= mask;i++){
            for(int j = 0;j < 85;j++){
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(strength, k, mask, 0, 1, 0);
    }

    private int dfs(List<Integer> strength, int k, int mask, int state, int x, int curStrength){
        if(state == mask) return 0;
        if(curStrength > 2 * maxStrength) return INF;
        if(memo[state][x][curStrength] != -1) return memo[state][x][curStrength];
        int ret = INF, n = strength.size();
        // 什么都不做
        ret = dfs(strength, k, mask, state, x, curStrength + x) + 1;
        // 尝试开第i把锁
        for(int i = 0;i < n;i++){
            if(curStrength >= strength.get(i) && ((state >> i) & 1) == 0){
                ret = Math.min(ret, dfs(strength, k, mask, state | (1 << i), x + k, 0));
            }
        }
        memo[state][x][curStrength] = ret;
        return ret;
    }
}
