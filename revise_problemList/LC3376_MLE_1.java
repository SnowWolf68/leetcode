package revise_problemList;

import java.util.Arrays;
import java.util.List;

/**
 * 最朴素的想法
 * 每次(每分钟)的选择: 1)什么都不做 2)开第i把锁
 * dfs
 * 
 * 这种写法的curStrength的上限是 sum(strength) , 这个上限会比较高, 因此可能会爆内存
 */
public class LC3376_MLE_1 {
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
            if(curStrength >= strength.get(i) && ((state >> i) & 1) == 0){
                /*
                 * 为什么这里不用 dfs(...) + 1 ?
                 * 因为从示例1可以看到, 如果在某时刻选择开锁, 那么到下一分钟时, 还是可以继续累加剑的能量的
                 * 因此可以看做是: 开锁不需要花费时间
                 * 故这里不需要 + 1
                 */
                ret = Math.min(ret, dfs(strength, k, mask, state | (1 << i), x + k, 0));
            }
        }
        memo[state][x][curStrength] = ret;
        return ret;
    }
}
