package problemList.dp.solution;

import java.util.Arrays;

/**
其实这题我们可以把基料和配料一起考虑, 放在一起进行dp, 这样只需要一次dp, 就能得到基料和配料的所有搭配下的所有可能的成本
我们只需要改动一下dp的初始化即可
在原来的初始化中, 我们添加了一行辅助节点, 第一行意味着当前没有任何配料, 当时我们初始化dp[0][0] = true, 其余位置都是false
实际上, 此时虽然没有任何配料, 但是此时我们可以选择任意一种基料, 因此, 我们需要将dp[0][baseCost]这个位置也初始化为true
    baseCost指的是baseCosts数组中的每一个元素
并且, 由于这里我们把基料和配料一起考虑, 因此这里dp[0][0] 就不能是true了, 而需要初始化为false
这样dp[i][j]就是考虑任意一种基料和[0, i]区间的配料, 是否能够凑整j这个成本 // 同理, 这里为了解决 "同一种配料可以使用两次" 的问题, 我们还是遍历两次toppingCosts数组
这样就可以不再遍历一遍baseCosts, 将基料和配料搭配起来考虑最接近的成本了
 */
public class LC1774_3 {
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int n = toppingCosts.length;
        int min = Arrays.stream(baseCosts).min().getAsInt();
        int upper = 2 * target - min;
        if(upper <= 0) return min;
        boolean[][] dp = new boolean[2 * n + 1][upper];
        for(int baseCost : baseCosts){
            // 加一个判断的目的是防止baseCost越界
            if(baseCost < upper) dp[0][baseCost] = true;
        }
        for(int i = 1;i <= 2 * n;i++){
            for(int j = upper - 1;j >= 0;j--){
                dp[i][j] = dp[i - 1][j];
                if(j - toppingCosts[(i - 1) % n] >= 0) dp[i][j] |= dp[i - 1][j - toppingCosts[(i - 1) % n]];
            }
        }
        // ret一定要初始化成min!!!
        int ret = min;
        for(int i = 0;i < upper;i++){
            if(dp[2 * n][i] && Math.abs(ret - target) > Math.abs(i - target)){
                ret = i;
            }else if(dp[2 * n][i] && Math.abs(ret - target) == Math.abs(i - target)){
                ret = Math.min(ret, i);
            }
        }
        return ret;
    }
}
