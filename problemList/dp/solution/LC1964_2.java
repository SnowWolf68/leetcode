package problemList.dp.solution;

import java.util.*;

/**
值域dp + 树状数组: O(n * logn)
 */
public class LC1964_2 {
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
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int max = Arrays.stream(obstacles).max().getAsInt(), n = obstacles.length;
        N = max;
        dp = new int[N + 1];
        int[] ret = new int[n];
        for(int i = 0;i < n;i++){
            int queryRet = query(obstacles[i]);
            ret[i] = queryRet + 1;
            add(obstacles[i], queryRet + 1);
        }
        return ret;
    }
}
