package problemList.dp.solution;

import java.util.*;

/**
O(n * logn) 的基于值域dp + 树状数组优化

dp[i] 表示以元素i结尾的子序列中, 最长递增子序列的长度
    dp[i] = max(dp[j] + 1), 其中, j < i
return max(dp[i]);
 */
public class LC300_4 {
    private int[] dp;
    private int N;
    private int lowbit(int x){
        return x & (-x);
    }
    private void update(int x, int u){
        for(int i = x;i <= N;i += lowbit(i)) dp[i] = Math.max(dp[i], u);
    }
    private int query(int x){
        int ret = 0;
        for(int i = x;i > 0;i -= lowbit(i)) ret = Math.max(ret, dp[i]);
        return ret;
    }
    public int lengthOfLIS(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int x : nums) set.add(x);
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        // <原始值, 离散化后的值>
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0;i < list.size();i++){
            map.put(list.get(i), i + 1);
        }
        N = list.size();
        dp = new int[N + 1];
        for(int num : nums){
            int x = map.get(num);
            update(x, query(x - 1) + 1);
        }
        return query(N);
    }
}
