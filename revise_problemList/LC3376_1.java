package revise_problemList;

import java.util.Arrays;
import java.util.List;

/*
 * 通过枚举每分钟内的选择肯定是不行的, 因为这样一定要记忆化strength[i]这个参数, 肯定会MLE
 * 
 * 注意到题目中有一个关键点: 每打开一把锁之后, 剑的能量都会变为0
 * 也就是说, 打开上一把锁之后不会有剩下的能量
 * 因此我们可以单独考虑打开每一把锁的时间
 * 
 * 如果当前打开锁的数量已知, 我们也能够知道当前的 增长因子X 的值
 * 因此: 如果知道了当前已经开的锁的数量, 那么当前打开剩余的某把锁所需要的时间也一定能够求出来
 * 考虑到n比较小, 使用状压DP
 */
public class LC3376_1 {
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size(), mask = 1 << n, INF = 0x3f3f3f3f;
        int[] dp = new int[mask];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int state = 1;state < mask;state++){
            int x = 1 + k * (Integer.bitCount(state) - 1);
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 1){
                    dp[state] = Math.min(dp[state], dp[state & (~(1 << i))] + (int)Math.ceil((double)strength.get(i) / x));
                }
            }
        }
        return dp[mask - 1];
    }
}
