package problemList.dp.solution;

import java.util.*;

/**
从LIS的角度出发考虑

操作数最少 -> 删除的元素最少 -> 保留的元素最多
那么题目转化为从arr1中选出一个LIS即可

但是和普通的LIS不同的是: 题目要求arr1 "全都" 需要满足严格递增的条件, 也就是说, 对于选出的LIS之外的那些元素, 必须能够满足 "使用arr2中元素替换后, 能够和选出来的LIS序列一起, 满足arr1严格递增的要求"
换句话说, 选出来的LIS之外的那些元素, 其必须满足: arr2中的元素能够满足这些元素的替换要求

具体的: 对于两个相邻的 "没有被替换的元素(即在我们选出的LIS当中的元素)" i, j来说(j < i), 其之间的元素个数i - j + 1必须满足以下条件: 
    我们首先对arr2进行升序排序
    假设startIdx为arr2中, 大于arr1[j]的最小值的元素下标, endIdx为arr2中, 小于arr1[i]的最大值的元素下标
    那么只要满足endIdx - startIdx + 1 >= i - j + 1, 那么意味着arr2中的元素, 能够满足arr1中(j, i)区间的元素的 "替换需求"

也就是说, 我们只需要在常规的一维LIS中, 加上一个O(logm)的二分查找的判断即可

细节: 
    1. 由于arr1的最后一个元素也有可能发生替换, 而dp[i]表示的是下标为i的元素不发生替换的情况下, 此时保留元素的最大值, 因此需要在arr1的最后添加一个INF, 这样INF一定不会发生替换
    2. 同理, 由于arr1的第一个元素也可能发生替换, 因此和上面类似, 需要在arr1前面添加一个-INF
    3. 由于arr2中可能出现重复元素, 因此使用endIdx - startIdx + 1进行计数时, 就有可能出现不准确的情况, 因此我们可以首先对arr2进行去重
    4. 由于dp[i]有可能找不到依赖的dp[j], 即有可能出现下标为i的元素一定需要替换的情况, 那么此时我们使用dp[i] = -1表示i一定需要替换的情况
    5. dp的初始化问题: 这里由于我们规定当dp[i]不存在的时候(即只能替换)对应的值为-1, 因此我们在循环中需要首先将dp[i]初始化为-1, 那么为了保证填表的正确性, 我们需要将dp[0]单独初始化为1, 同时填表从i == 1开始填, 这样就可以正确初始化
    6. 特别的: 在计算dp[i]时, 一定要保证其依赖的dp[j] != -1, 即dp[j]存在

时间复杂度: O(n ^ 2 * logm)
 */
public class LC1187_2 {
    public int makeArrayIncreasing(int[] _arr1, int[] _arr2) {
        int n = _arr1.length + 2, INF = 0x3f3f3f3f;
        int[] arr1 = new int[n];
        for(int i = 1;i < n - 1;i++) arr1[i] = _arr1[i - 1];
        arr1[n - 1] = INF; arr1[0] = -INF;
        Set<Integer> set = new HashSet<>();
        for(int x : _arr2) set.add(x);
        int m = set.size();
        int[] arr2 = new int[m];
        int index = 0;
        for(int x : set) arr2[index++] = x;
        Arrays.sort(arr2);
        int[] dp = new int[n];
        dp[0] = 1;
        for(int i = 1;i < n;i++){
            dp[i] = -1;
            for(int j = 0;j < i;j++){
                // 满足LIS的基本条件
                if(arr1[j] > arr1[i]) continue;
                int l = 0, r = m - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(arr2[mid] > arr1[j]) r = mid;
                    else l = mid + 1;
                }
                int startIdx = -1;
                if(arr2[l] > arr1[j]) startIdx = l;
                l = 0; r = m - 1;
                while(l < r){
                    int mid = (l + r + 1) >> 1;
                    if(arr2[mid] < arr1[i]) l = mid;
                    else r = mid - 1;
                }
                int endIdx = -1;
                if(arr2[l] < arr1[i]) endIdx = l;
                // 特别的: 如果j + 1 == i, 那么此时(j, i)中间没有其他元素, 此时不需要使用endIdx - startIdx + 1进行判断
                if((startIdx != -1 && endIdx != -1 && startIdx <= endIdx && endIdx - startIdx + 1 >= i - j - 1 && dp[j] != -1)
                || (j + 1 == i && dp[j] != -1)){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n - 1] == -1 ? -1 : n - dp[n - 1];
    }
}
