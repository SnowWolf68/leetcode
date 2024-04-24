package problemList.dp.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
第一种方法: 从下标的角度出发考虑, 划分型dp
dp[i] 表示考虑[0, i]区间的这些站, 此时的最多盈利
    dp[i]: 此时显然需要找出所有从下标为i的站下车的旅客找出来, 遍历每一个旅客, 假设其对应上车站点为j, 那么
        dp[i] = dp[j] + end - start + tips;
    对于所有可能的j, 这里只需要取一个max即可
初始化: 由于能够保证j不越界, 因此不需要初始化
return dp[n - 1];
 */
public class LC2008_1 {
    public long maxTaxiEarnings(int n, int[][] rides) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        for(int[] ride : rides){
            int start = ride[0] - 1, end = ride[1] - 1, tips = ride[2];
            List<int[]> list = map.getOrDefault(end, new ArrayList<>());;
            list.add(new int[]{start, tips});
            map.put(end, list);
        }
        long[] dp = new long[n];
        for(int i = 0;i < n;i++){
            if(i != 0) dp[i] = dp[i - 1];
            List<int[]> list = map.getOrDefault(i, new ArrayList<>());
            for(int[] arr : list){
                int start = arr[0], tips = arr[1];
                dp[i] = Math.max(dp[i], dp[start] + i - start + tips);
            }
        }
        return dp[n - 1];
    }
}
