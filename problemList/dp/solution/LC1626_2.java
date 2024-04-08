package problemList.dp.solution;

import java.util.*;

/**
考虑优化: 贪心 + 二分的优化只是适用于 "长度" 的问题, 因此这里不能用, 剩下O(n * logn)的做法就剩下 值域dp 优化了
首先考虑值域dp是否可行, 由于这里在求以某个元素x结尾的dp值时, 也是查询 <= x的所有dp值中的最大值, 因此使用值域dp是可行的
这里 "查询区间最大值" 的操作可以使用树状数组优化

时间复杂度: O(n * logn)
 */
public class LC1626_2 {
    private int[] dp;
    private int N;
    private int lowbit(int x){
        return x & (-x);
    }
    private void add(int x, int u){
        for(int i = x;i <= N;i += lowbit(i)) dp[i] = Math.max(dp[i], u);
    }
    private int query(int x){
        int ret = 0;
        for(int i = x;i > 0;i -= lowbit(i)) ret = Math.max(ret, dp[i]);
        return ret;
    }
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length, max = Arrays.stream(ages).max().getAsInt();
        N = max;
        dp = new int[N + 1];
        List<int[]> info = new ArrayList<>();
        for(int i = 0;i < n;i++){
            info.add(new int[]{scores[i], ages[i]});
        }
        Collections.sort(info, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        for(int[] x : info){
            add(x[1], query(x[1]) + x[0]);
        }
        return query(N);
    }
}
