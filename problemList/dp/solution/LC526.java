package solution;

/**
dp[state] 表示使用 state 集合中的这些整数, 此时构成优美排列的数量
    注: 这里state集合中的这些元素规定从0开始, 所以在后面取模的时候需要 +1 进行调整
dp[state]: 枚举最后放的元素i (这里为了统一, i也从0开始枚举)
    首先需要满足条件: (i + 1) % bitCount == 0 || bitCount % (i + 1) == 0
        如果条件满足, 那么: dp[state] += dp[state & (~(1 << i))];
初始化: dp[0] = 1;
return dp[mask];
 */
public class LC526 {
    public int countArrangement(int n) {
        int mask = 1 << n;
        int[] dp = new int[mask];
        dp[0] = 1;
        for(int state = 1;state < mask;state++){
            int bitCount = Integer.bitCount(state);
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                if((i + 1) % bitCount == 0 || bitCount % (i + 1) == 0){
                    dp[state] += dp[state & (~(1 << i))];
                }
            }
        }
        return dp[mask - 1];
    }
}
