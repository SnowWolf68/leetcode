package problemList.dp.solution;

import java.util.*;

/**
这题1700? 还是说我太菜了?

由于基料必须选择一份, 但是配料可以选择多份, 因此我们需要对于每一种基料, 考虑所有配料的组合, 并且找一种成本最接近target的方案
因此我们可以首先 预处理 出 所有符合要求的配料的组合能够组成的 所有可能的成本, 然后遍历每一种基料, 计算当前最接近target的成本是多少, 最后取一个最接近的成本即可

考虑如何预处理出 所有符合要求的配料的组合能够组成的 所有可能的成本
-> 换句话说, 即考虑 某种成本 能否由 某种符合要求的配料的组合 构成
这个问题可以使用0-1背包解决
然而0-1背包问题要求每个物品最多只能选用一次, 而这里对于每一种配料, 其可以选择至多2次, 因此这里我们可以选择 遍历两次配料数组 来转化为 至多选1次 的情况
dp[i][j] 表示考虑[0, i]区间的配料, 当前是否能够组成j这个成本
这里需要首先分析一下j的上界, 假设min = min(baseCosts), 那么对于j >= 2 * target - min的这些情况, 此时显然只选min是最优的, 那么对于这些情况, 我们就无需考虑
因此j的上界upper就是2 * target - min(取不到)
    dp[i][j]: 考虑下标为i的配料是否选用
        1. 选: dp[i][j] = dp[i - 1][j - toppingCosts[i]];
        2. 不选: dp[i][j] = dp[i - 1][j];
    对于上面两种情况, 只需要有一种情况为true即可, 因此可以取一个 |
初始化: 对i这个维度进行初始化, 添加一行辅助节点, 第一行意味着当前没有任何配料, 那么此时只有dp[0][0] = true, 其余位置都是false
返回值: 如果想要知道j这个成本能不能由toppingCosts组成, 只需要看dp[2 * n][j]这个节点的值是true还是false即可

dp结束之后, 我们只需要再遍历一次baseCosts数组, 搭配所有toppingCosts的可能, 计算出此时最接近target的成本即可
 */
public class LC1774_1 {
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int n = toppingCosts.length;
        int min = Arrays.stream(baseCosts).min().getAsInt();
        int upper = 2 * target - min;
        if(upper <= 0) return min;
        boolean[][] dp = new boolean[2 * n + 1][upper];
        dp[0][0] = true;
        for(int i = 1;i <= 2 * n;i++){
            for(int j = upper - 1;j >= 0;j--){
                dp[i][j] = dp[i - 1][j];
                if(j - toppingCosts[(i - 1) % n] >= 0) dp[i][j] |= dp[i - 1][j - toppingCosts[(i - 1) % n]];
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
                    if(dp[2 * n][i - baseCost] && Math.abs(ret - target) > Math.abs(i - target)){
                        ret = i;
                    }else if(dp[2 * n][i - baseCost] && Math.abs(ret - target) == Math.abs(i - target)){
                        ret = Math.min(ret, i);
                    }
                }
            }
        }
        return ret;
    }
}
