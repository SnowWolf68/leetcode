package problemList.dp.solution;

import java.util.ArrayDeque;
import java.util.Deque;

/**
同理也可以使用单调队列进行优化: 
    计算dp[i]的时候, 需要查询dp表中[i + 1, 2 * i + 2]区间的最小值
 */
public class LC2944_4 {
    public int minimumCoins(int[] prices) {
        int n = prices.length;
        int[] dp = new int[n];
        Deque<int[]> deque = new ArrayDeque<>();
        int lower = (int)Math.ceil((double)(n - 2) / 2);
        for(int i = lower;i < n;i++) dp[i] = prices[i];
        for(int i = n - 1;i >= lower;i--){
            while(!deque.isEmpty() && deque.peekLast()[1] >= dp[i]){
                deque.pollLast();
            }
            deque.addLast(new int[]{i, dp[i]});
        }
        for(int i = lower - 1;i >= 0;i--){
            while(!deque.isEmpty() && deque.peekFirst()[0] > 2 * i + 2){
                deque.pollFirst();
            }
            int min = deque.peekFirst()[1];
            dp[i] = min + prices[i];
            while(!deque.isEmpty() && deque.peekLast()[1] >= dp[i]){
                deque.pollLast();
            }
            deque.addLast(new int[]{i, dp[i]});
        }
        return dp[0];
    }
}
