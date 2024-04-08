package problemList.dp.solution;

import java.util.*;

/**
二维LIS: 双关键字排序 + 一维LIS

数据范围在1e5, O(n ^ 2)的常规dp会TLE, 只能用值域dp + 树状数组

注意这题两个维度都要求 "严格大于" , 因此我们需要修改一下双关键字排序的逻辑, 改为当第一个维度相等时, 第二个维度降序排列
这样就能够保证, 当在第二个维度进行LIS时, 第一个维度能够保证是 "严格大于"
 */
public class LC354 {
    private int N;
    private int[] dp;
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
    public int maxEnvelopes(int[][] envelopes) {
        List<int[]> info = new ArrayList<>();
        int max = 0;
        for(int[] en : envelopes){
            info.add(new int[]{en[0], en[1]});
            max = Math.max(max, en[1]);
        }
        N = max;
        dp = new int[N + 1];
        Collections.sort(info, (o1, o2) -> o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]);
        for(int[] x : info){
            add(x[1], query(x[1] - 1) + 1);
        }
        return query(N);
    }
}
