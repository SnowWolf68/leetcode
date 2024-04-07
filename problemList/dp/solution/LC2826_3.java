package problemList.dp.solution;

import java.util.*;

/**
不是严格递增的LIS
值域dp + 树状数组: O(n * logn)
 */
public class LC2826_3 {
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
    public int minimumOperations(List<Integer> nums) {
        int max = nums.stream().max(Integer::compareTo).get();
        N = max;
        dp = new int[N + 1];
        for(int x : nums){
            add(x, query(x) + 1);
        }
        return nums.size() - query(N);
    }
}
