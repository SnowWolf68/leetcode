package problemList.dp.solution;

import java.util.*;

/**
dp空间优化的版本
 */
public class LC1774_2 {
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int n = toppingCosts.length;
        int min = Arrays.stream(baseCosts).min().getAsInt();
        int upper = 2 * target - min;
        if(upper <= 0) return min;
        boolean[] dp = new boolean[upper];
        dp[0] = true;
        for(int i = 1;i <= 2 * n;i++){
            for(int j = upper - 1;j >= 0;j--){
                if(j - toppingCosts[(i - 1) % n] >= 0) dp[j] |= dp[j - toppingCosts[(i - 1) % n]];
            }
        }
        int ret = Integer.MAX_VALUE;
        for(int baseCost : baseCosts){
            if(baseCost >= target){
                if(Math.abs(ret - target) > Math.abs(baseCost - target)){
                    ret = baseCost;
                }else if(Math.abs(ret - target) == Math.abs(baseCost - target)){
                    ret = Math.min(ret, baseCost);
                }
            }
            else{
                for(int i = 2 * target - baseCost;i >= baseCost;i--){
                    if(dp[i - baseCost] && Math.abs(ret - target) > Math.abs(i - target)){
                        ret = i;
                    }else if(dp[i - baseCost] && Math.abs(ret - target) == Math.abs(i - target)){
                        ret = Math.min(ret, i);
                    }
                }
            }
        }
        return ret;
    }
}
