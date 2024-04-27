package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
假设我们把s1, s2中所有不同下标拿出来, 组成ids集合, 假设ids集合的长度为m, 那么
特别的: 如果m为奇数, 那么此时不能通过操作一, 操作二来使得s1, s2相等, 那么直接返回-1
    dp[i] 表示翻转ids集合中[0, i]区间的这些元素, 此时所需要的最小操作代价
        dp[i]: 此时有两种选择, 对于这两种选择, 由于具体和前面哪一个元素交换还不确定, 因此我们还需要具体分析: 
            第一种操作: 选择前面的某一个下标j, 让i, j同时翻转, 操作的代价为x
                在这种操作的情况下, 由于j这个下标不确定在哪里, 因此我们可以将x的代价 "均摊" 到这两个被操作的下标上
                即翻转i, j其中的一个下标, 所需要的代价为x / 2
                这样dp[i]的计算就和j的位置无关, 那么有
                    dp[i] = dp[i - 1] + x / 2;
            第二种操作: 翻转相邻的两个元素, 操作代价为1
                这种操作实际上可以连续翻转, 并且达到第一种的效果, 假设我们要翻转j, i两个下标, 其中j < i, 并且ids[j], ids[i]在s1, s2的下标中不相邻
                那么我们可以连续使用第二种操作, 此时的代价为ids[i] - ids[j], 其中i, j指的是ids中的下标, ids[i], ids[j]指的是在s1, s2中的下标
                分析到目前为止, j还是不确定的, 因此我们还需要继续分析
                由于使用第二种操作, 其所需要的代价是ids[i] - ids[j], 其与i, j在s1, s2中的下标之间的距离有关, 那么显然我们让j越靠近i, 此时操作的代价越小
                因此我们得到: 此时如果使用第二种操作, 那么j = i - 1
                那么有状态转移方程: dp[i] = dp[i - 2] + ids[i] - ids[i - 1];
            对于上面的两种操作, 此时我们只需要取一个min即可
    初始化: 这里i - 1, i - 2均有可能越界, 因此我们需要初始化前两个节点
        由于这里需要初始化两个节点, 因此这里我们不添加辅助节点, 而是选择直接初始化前两个值
        dp[0]: 此时显然不能和前面的一个下标联合使用第二种操作来翻转, 那么只能使用第一种操作, 那么dp[0] = x / 2;
        dp[1]: 此时既可以选择第一种操作, 也可以选择第二种操作, 那么dp[1]应该取两者中的一个最小值, 即dp[1] = Math.min(x, ids[1] - ids[0]);
    return dp[m - 1];
时间复杂度: O(n)
 */
public class LC2896 {
    public int minOperations(String s1, String s2, int x) {
        int n = s1.length();
        List<Integer> ids = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(s1.charAt(i) != s2.charAt(i)){
                ids.add(i);
            }
        }
        int m = ids.size();
        if(m % 2 != 0) return -1;
        if(m == 0) return 0;
        double[] dp = new double[m];
        dp[0] = (double)x / 2;
        dp[1] = Math.min(x, ids.get(1) - ids.get(0));
        for(int i = 2;i < m;i++){
            dp[i] = Math.min(dp[i - 1] + (double)x / 2, dp[i - 2] + ids.get(i) - ids.get(i - 1));
        }
        return (int)dp[m - 1];
    }
}
