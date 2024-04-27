package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
对于s来说, 此时可以从前面移除, 也可以从后面移除, 因此不能直接dp, 需要 前后缀分解
具体的:
    这里我们还是首先将所有违禁品车厢的下标拿出来, 假设为ids集合, 假设m为ids.size, 那么
        pre[i] 表示移除ids中[0, i]区间的违禁品车厢, 此时所需要花费的最小时间
            pre[i]: 对于ids[i]这个下标的车厢, 此时有两种选择: 
                1. 移除[0, ids[i]]区间的所有车厢: 此时花费的时间为ids[i] + 1, 并且前面所有的违禁品车厢都会被移除, 那么有: 
                    pre[i] = ids[i] + 1;
                2. 单独移除ids[i]这一个车厢: 单独移除这一个车厢所需要花费的时间为2, 那么有
                    pre[i] = pre[i - 1] + 2;
            对于上面的两种选择, 我们只需要取一个min即可
        初始化: 这里i - 1可能会越界, 由于后续我们还需要将前后缀组合起来, 如果添加辅助节点的话, 那么下标的映射处理起来比较复杂, 因此这里采取直接初始化dp[0]的方式
            初始化dp[0] = Math.min(ids[0] + 1, 2);
        suf[i] 表示移除ids中[i, m - 1]区间的违禁品车厢, 此时所需要花费的最小时间
            suf[i]: 和上面类似, 假设n = s.length()
                1. suf[i] = n - ids[i];
                2. suf[i] = suf[i + 1] + 2;
            对于上面的两种情况, 还是需要取一个min
        初始化: 这里i + 1可能会发生越界, 和上面同理, 我们使用直接初始化dp[m - 1]这个节点的方式
            初始化dp[m - 1] = Math.min(n - ids[m - 1], 2);

前后缀分解完之后, 我们只需要枚举分割点i, 那么此时总的消耗的时间为pre[i] + suf[i + 1], 这里i的范围是[0, m - 2];
然后我们还需要考虑只有pre和只有suf的情况, 因此需要将上面遍历分割点得到的ret再对pre[m - 1], suf[0]再取一个min
 */
public class LC2167 {
    public int minimumTime(String s) {
        int n = s.length();
        List<Integer> ids = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(s.charAt(i) == '1') ids.add(i);
        }
        int m = ids.size();
        if(m == 0) return 0;
        int[] pre = new int[m];
        pre[0] = Math.min(ids.get(0) + 1, 2);
        for(int i = 1;i < m;i++){
            pre[i] = Math.min(ids.get(i) + 1, pre[i - 1] + 2);
        }
        int[] suf = new int[m];
        suf[m - 1] = Math.min(n - ids.get(m - 1), 2);
        for(int i = m - 2;i >= 0;i--){
            suf[i] = Math.min(n - ids.get(i), suf[i + 1] + 2);
        }
        if(m == 1){
            return Math.min(pre[0], suf[0]);
        }
        int ret = Integer.MAX_VALUE;
        for(int i = 0;i <= m - 2;i++){
            ret = Math.min(ret, pre[i] + suf[i + 1]);
        }
        ret = Math.min(ret, Math.min(pre[m - 1], suf[0]));
        return ret;
    }
}
