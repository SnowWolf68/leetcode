package solution;

/**
dp[state] 表示完成state集合中表示的这些工作, 最少需要多少个工作时间段
dp[state]: 枚举最后一个时间段完成的工作集合, 假设为p
    首先需要满足p中的工作能够在一个时间段中完成, 如果条件满足, 那么有: 
    dp[state] = min(dp[state & (~p)] + 1)
初始化: dp[0] = 0
return dp[mask - 1]
 */
public class LC1986 {
    public int minSessions(int[] tasks, int sessionTime) {
        int n = tasks.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[] time = new int[mask];
        for(int state = 0;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                time[state] += tasks[i];
            }
        }
        int[] dp = new int[mask];
        for(int state = 1;state < mask;state++){
            dp[state] = INF;
            for(int p = state;p > 0;p = (p - 1) & state){
                if(time[p] <= sessionTime) dp[state] = Math.min(dp[state], dp[state & (~p)] + 1);
            }
        }
        return dp[mask - 1];
    }
}
