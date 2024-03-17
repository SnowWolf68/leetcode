package problems.dp.solution;

import java.util.*;

/**
这题的数据范围m, n都能够到1e5, 二维dp的时空复杂度都是O(mn), 不仅会TLE, 还会MLE
考虑如何优化: 
    首先对于题目可以进行一下转化: 题目要求的是 "最少操作次数" , 使得target变成arr的一个子序列
    那么实际上也就是求target和arr的最长公共子序列的长度(LCS), 假设LCS长度为len, target数组长度为n, 那么 "最少操作次数" 即为n - len
    这样就将题目转化成了LCS问题
    普通LCS问题的求解是二维DP, 其时空复杂度都是O(mn), 显然还需要优化
    优化点: target数组中不包含重复元素
    如果LCS的两个数组中, 有一个数组不包含重复元素, 那么此时我们可以将LCS问题转化为LIS问题, 分析如下: 
        如果target数组中不包含重复元素, 那么对于每一个元素target[i], 其有且只有一个下标i与其对应, 那么target的一个子序列, 就转化成了target下标数组的一个上升序列
            注意: 由于下标序列是严格递增的, 所有这里求LIS也必须是要求严格递增
        这时如果将arr中, 出现在target数组中的元素都替换成其在target数组中的下标, 不在target数组中的元素都删除掉
        那么原来的LCS问题就转化成了在arr数组中的LIS问题, 而LIS问题有O(n * logn)的解法
 */
public class LC1713 {
    public int minOperations(int[] target, int[] arr) {
        int m = target.length;
        // <val, idx>
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < m;i++) map.put(target[i], i);
        List<Integer> arr2 = new ArrayList<>();
        for(int x : arr){
            if(map.containsKey(x)){
                arr2.add(map.get(x));
            }
        }
        // LIS
        List<Integer> list = new ArrayList<>();
        for(int x : arr2){
            if(list.size() == 0 || list.get(list.size() - 1) < x) list.add(x);
            else{
                // 二分找第一个大于等于x的位置, 将x替换到该位置
                // 注意这里是 "大于等于" , 因为我们要求LIS必须是严格递增的
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list.get(mid) >= x) r = mid;
                    else l = mid + 1;
                }
                list.set(l, x);
            }
        }
        return m - list.size();
    }
}
