package problemList.dp.solution;

import java.util.*;

/**
    显然同一个栈中的所有元素是一个组, 但是题目的意思是每组中可以选一个或多个, 但是由于栈中的元素不相同, 因此这不是多重背包
    而分组背包的情景, 是同一组内的物品至多/恰好选一个, 因此这也不是一个分组背包问题
    考虑题目转化: 题目中限制了每组取数只能是按照栈的规则取, 即每次只能取栈顶的元素, 那么如果我们把每个栈中的元素从栈顶到栈底求一个前缀和, 那么实际上每一次取的就是某一个前缀和, 那么这样就符合分组背包的要求
    这样就将题目转化成了一个分组背包

    dp[i][j] 表示考虑[0, i]区间的分组, 当前进行k次操作时, 能够得到的硬币面值的最大和
        dp[i][j]: 假设第i个分组中取的是前p个元素的和
            dp[i][j] = dp[i - 1][j - p];
        对于k的所有可能, 取一个max即可
    初始化: 初始化i这个维度, 添加一行辅助节点, 此时第一行意味着当前没有任何分组, 那么此时肯定不会执行任何操作, 那么初始化dp[0][0] = 0, 其余位置都是非法, 初始化为-INF
    return dp[n - 1][k]

    代码实现中, 我们可以把计算前缀和放在dp的i这层循环中进行计算
 */
public class LC2218 {
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int n = piles.size(), INF = 0x3f3f3f3f;
        int[][] dp = new int[n + 1][k + 1];
        Arrays.fill(dp[0], -INF);
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            // 计算前缀和
            List<Integer> cur = piles.get(i - 1);
            List<Integer> preSum = new ArrayList<>();
            preSum.add(0);
            for(int j = 0;j < cur.size();j++){
                preSum.add(preSum.get(j) + cur.get(j));
            }
            for(int j = 0;j <= k;j++){
                for(int p = 0;p < preSum.size();p++){
                    if(j - p >= 0) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - p] + preSum.get(p));
                }
            }
        }
        return dp[n][k];
    }
}
