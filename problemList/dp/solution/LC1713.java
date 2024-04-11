package problemList.dp.solution;

import java.util.*;

/**
在LCS问题中, 如果其中一个数组不包含重复元素, 那么此时可以将LCS问题转化为LIS问题
关键: 其中一个数组中不包含重复元素
    假设有两个数组arr1, arr2, 我们需要求这两个数组的LCS, 并且已知arr1中不包含重复元素, arr2中包含重复元素
    那么由于arr1中不包含重复元素, 那么意味着对于arr1中的每一个元素arr1[i], 都有且仅有一个下标i与其对应
    那么此时我们只需要将arr2中 在arr1中出现的元素 都使用其在arr1中的下标替换, 不在arr1中出现的元素都删掉
    那么求LCS就变成了求arr2中的LIS

本题也是这个道理, 本题要求最少的插入次数, 也就是保留的元素最多, 也就是两个数组的LCS
 */
public class LC1713 {
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
    public int minOperations(int[] target, int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0;i < target.length;i++){
            // 由于树状数组的下标需要从1开始, 所以这里用i + 1
            map.put(target[i], i + 1);
        }
        List<Integer> list = new ArrayList<>();
        for(int x : arr){
            if(map.containsKey(x)){
                list.add(map.get(x));
            }
        }
        if(list.isEmpty()) return target.length;
        int max = list.stream().reduce(Integer::max).get();
        N = max;
        dp = new int[N + 1];
        for(int x : list){
            add(x, query(x - 1) + 1);
        }
        return target.length - query(N);
    }
}
