package problemList.dp.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
第一种方法: 从下标的角度出发考虑, 划分型dp

这种方法理论上可行, 但是实际上由于endTime范围能到1e9, 因此会MLE
 */
public class LC1235_1_MLE {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        Map<Integer, List<int[]>> map = new HashMap<>();
        int n = startTime.length;
        int max = 0;
        for(int i = 0;i < n;i++){
            int start = startTime[i] - 1, end = endTime[i] - 1, pro = profit[i];
            max = Math.max(max, end);
            List<int[]> list = map.getOrDefault(end, new ArrayList<>());;
            list.add(new int[]{start, pro});
            map.put(end, list);
        }
        int[] dp = new int[max + 1];
        for(int i = 0;i <= max;i++){
            if(i != 0) dp[i] = dp[i - 1];
            List<int[]> list = map.getOrDefault(i, new ArrayList<>());
            for(int[] arr : list){
                int start = arr[0], pro = arr[1];
                dp[i] = Math.max(dp[i], dp[start] + pro);
            }
        }
        return dp[max];
    }
}
