package problemList.dp.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
这类题的第一种解法: 从划分型dp的角度出发考虑, 实际上就是从 房屋 的角度出发考虑
由于我们是从划分型dp的角度出发考虑, 那么对应的dp[i]表示考虑[0, i]区间的房屋, 此时能够得到的最大收入
显然我们需要遍历此时最后一个区间的所有可能, 即确定最后一个区间的起始下标和结束下标
我们不妨从结束下标开始入手分析: 由于我们考虑的区间是[0, i], 那么此时无非只有两种可能: 
    1. 结束下标不是i: 此时显然dp[i] = dp[i - 1];
    2. 结束下标是i: 还需要确定起始下标
        由于这里的区间都是固定的, 即题目给定了所有可能的区间范围, 那么此时我们就需要找: 以i为结束下标的所有区间的起始下标
        换句话说, 我们需要将题目给出的区间, 按照结束下标分组, 那么此时我们就是遍历当前结束下标(i)这一组的所有区间, 假设其对应起始下标为j, 得到金币为gold, 那么有: 
            dp[i] = dp[j- 1] + gold;
        对于所有可能的起始下标j, 我们只需要取一个max即可
初始化: j - 1有可能越界, 那么我们需要添加一个辅助节点, 并且初始化dp[0] = 0即可
return dp[n - 1];

时间复杂度: O(n + m), 其中m = offers.size()
 */
public class LC2830_1 {
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        // <end, [start, gold]>
        Map<Integer, List<int[]>> map = new HashMap<>();
        for(List<Integer> offer : offers){
            int start = offer.get(0), end = offer.get(1), gold = offer.get(2);
            List<int[]> list = map.getOrDefault(end, new ArrayList<>());
            list.add(new int[]{start, gold});
            map.put(end, list);
        }
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = dp[i - 1];
            List<int[]> list = map.getOrDefault(i - 1, new ArrayList<>());
            for(int[] arr : list){
                int start = arr[0], gold = arr[1];
                dp[i] = Math.max(dp[i], dp[start] + gold);
            }
        }
        return dp[n];
    }
}
