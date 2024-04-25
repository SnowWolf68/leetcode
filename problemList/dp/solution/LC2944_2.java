package problemList.dp.solution;

import java.util.ArrayDeque;
import java.util.Deque;

/**
优化: 在k的这一层遍历中, 我们寻找的是dp表中某一个区间的最小值信息, 这个过程可以使用单调队列优化
    具体的: dp[i][0] = dp表中k属于[lower1, i - 1]区间的dp[k][1]的最小值
        dp[i][1] = dp表中k属于[lower2, i - 1]区间的dp[k][1]的最小值 + prices[i]
    
单调队列的思想: 
    这里假设我们维护的是一个单调递增(从队头到队尾)的单调队列, 其中队列中的每个元素维护的都是一个二元组信息[idx, val]
    那么在队头我们需要做的操作是: 每次判断当前元素对应的idx是否在区间内, 如果不在区间内, 则pop
        在队尾需要做的操作是: 每次判断当前元素对应的val是否 <= 队尾元素的val, 如果 <= , 那么pop队尾中所有val >= 当前元素的二元组, 同时让当前元素对应的二元组入队

时间复杂度: 使用单调队列能够在O(1)的时间内得到min, 因此时间复杂度优化到了O(n);
 */
public class LC2944_2 {
    public int minimumCoins(int[] prices) {
        int n = prices.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[n][2];
        dp[0][0] = INF; dp[0][1] = prices[0];
        Deque<int[]> deque = new ArrayDeque<>();
        deque.addFirst(new int[]{0, dp[0][1]});
        for(int i = 1;i < n;i++){
            dp[i][0] = dp[i][1] = INF;
            int lower1 = (int)Math.ceil((double)(i - 1) / 2);
            while(!deque.isEmpty() && deque.peekFirst()[0] < lower1){
                deque.pollFirst();
            }
            int min = deque.peekFirst()[1];
            dp[i][0] = min;
            int lower2 = (int)Math.ceil((double)(i - 2) / 2);
            if(lower2 != lower1) min = Math.min(min, dp[lower2][1]);
            dp[i][1] = min + prices[i];
            while(!deque.isEmpty() && deque.peekLast()[1] >= dp[i][1]){
                deque.pollLast();
            }
            deque.addLast(new int[]{i, dp[i][1]});
        }
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }
}
