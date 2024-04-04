package problemList.dp.solution;

/**
考虑如何优化: 
    通过状态转移方程不难发现, 此时dp[i][j]仍然是dp[i - 1]中一些元素的和, 但是和上一题不一样的是, 这些元素不再是连续的
    因此不能使用普通的前缀和来进行优化, 而我们发现, 这些元素虽然不是连续的, 但是其下标的间隔(差)却是固定的, 都是types[i][1], 因此我们可以使用类似前缀和的方式 -- 同余前缀和, 进行优化

    首先分析dp[i][j]依赖dp[i - 1]这一行中哪些位置的dp值的和
    for(int k = 0;k <= types[i - 1][0];k++){
        if(j - k * types[i - 1][1] >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - k * types[i - 1][1]]) % MOD;
    }
    对于这层循环, 不难发现j的范围是[j - k * types[i - 1][1], j], 并且下标相隔types[i - 1][1]
    那么分析k的上界, 通过循环条件以及if条件, 不难确定k的上界是: Math.min(j / types[i - 1][1], types[i - 1][0])
    那么显然需要分类讨论两种情况: 
        1. k == Math.min(j / types[i - 1][1], types[i - 1][0]) == j / types[i - 1][1]: 此时有j / types[i - 1][1] <= types[i - 1][0], 即j <= types[i - 1][1] * types[i - 1][0], 将k的上界带入j的范围, 得到j的范围是[0, j]
        2. k == Math.min(j / types[i - 1][1], types[i - 1][0]) == types[i - 1][0]: 此时有j / types[i - 1][1] > types[i - 1][0], 即j > types[i - 1][1] * types[i - 1][0], 将k的上界带入j的范围, 得到j的范围是[j - types[i - 1][1] * types[i - 1][0], j]
    也就是说: 
        1. j <= types[i - 1][1] * types[i - 1][0]: 范围是[0, j]
        2. j > types[i - 1][1] * types[i - 1][0]: 范围是[j - types[i - 1][0] * types[i - 1][1], j]
    那么对于dp[i][j]: 
        1. 如果j <= types[i - 1][1] * types[i - 1][0]: dp[i][j] = dp[i - 1]这一行中, [0, j]区间中, 从j往前数, 每隔types[i - 1][1]个下标的所有dp值的和
        2. 如果j > types[i - 1][1] * types[i - 1][0]: dp[i][j] = dp[i - 1]这一行中, [j - types[i - 1][0] * types[i - 1][1], j]区间中, 从j往前数, 每隔types[i - 1][1]个下标的所有dp值的和
    我们可以使用同余前缀和进行计算: 

    同余前缀和: 
        定义s[j + 1]表示dp[i - 1]这一行中, [0, j]区间内的所有k, 满足k mod types[i][1] == j mod types[i][1]的所有dp[i - 1][k]的元素和
        那么类似普通前缀和, 有递推计算公式: s[j + 1] = s[j + 1 - types[i][1]] + dp[i - 1][j];
        利用同余前缀和计算原数组某一段区间的间隔一定下标的值的和, 和使用前缀和来计算原数组中某一段区间的和, 有很大不同: 
            由于s[j]和s[j - 1]所代表的mod k的那些下标不同, 因此如果要计算[a, b]区间, 从b开始往前数, 间隔k的那些元素的和, 我们不能直接s[b + 1] - s[a - 1 + 1]
            因为s[b + 1]和s[a]所代表的下标mod k并不相同, 因此我们只能采取 "先减去, 再加上" 的策略, 即s[b + 1] - s[a + 1] + nums[a]的方式来计算
    
    具体说一下dp[i][j]的计算, 以及同余前缀和的更新:
    由于这里的dp初始化显然需要添加一行辅助节点, 那么接下来的所有分析, 都是建立在 "dp表添加了一行辅助节点" 的基础之上
    首先在计算dp[1]这一行的时候, 我们首先需要使用dp[0]这一行, 以及types[0][1], 将s[]数组初始化好
        注意这里初始化dp[i]这一行的同余前缀和数组时, 其用到的是dp[i - 1]这一行的数据, 并且间隔是types[i - 1][1]
    这个初始化就可以按照同余前缀和的递推计算方式来进行初始化, 由于同余前缀和数组类似前缀和数组, 其也需要添加s[0] = 0这样一个节点, 因此我们可以让j在[0, target]中循环, 并且每次计算s[j + 1]这个位置
        由于s[j + 1]依赖s[j + 1 - types[0][1]], 因此需要判断j + 1 - types[0][1]是否 >= 0
            1. 如果j + 1 - types[0][1] >= 0: s[j + 1] = s[j + 1 - types[0][1]] + dp[0][j];
            2. 如果j + 1 - types[0][1] < 0: s[j + 1] = dp[0][j];
    然后考虑在填表的时候, 如何使用同余前缀和数组s, 在O(1)时间内计算出dp[i][j], 以及如何更新同余前缀和数组s
    为了在一次j的循环中, 同时完成计算和更新, 这里我们可以先创建一个数组preS = s.clone(), 计算dp[i][j]就使用preS数组, 更新就直接更新s数组, 这样就使得计算和更新互不干扰
        1. 计算dp[i][j]: 通过之前的分析, 我们需要根据j的范围, 分成两种情况讨论: 
            1. j < types[i - 1][0] * types[i - 1][1]: 此时dp[i][j] = dp[i - 1]这一行中, [0, j]区间内, 从j开始向前数, 每隔types[i - 1][1]个下标的, 这些dp值的和, 对应同余前缀和数组就是: preS[j + 1]
            2. j >= types[i - 1][0] * types[i - 1][1]: 此时dp[i][j] = dp[i - 1]这一行中, [j - types[i - 1][0] * types[i - 1][1], j]区间内, 从j开始向前数, 每隔types[i - 1][1]个下标的, 这些dp值的和, 对应同余前缀和数组就是: preS[j + 1] - preS[j - types[i - 1][0] * types[i - 1][1] + 1] + dp[i - 1][j - types[i - 1][0] * types[i - 1][1]];
        2. 更新同余前缀和数组s: 
            对于当前i这一行来说, 此时更新s数组需要用到i + 1这一行的types值, 为了避免越界, 我们需要首先加上一个条件(i != n)
            然后还是使用同余前缀和的递推计算方式进行更新: s[j + 1] = s[j + 1 - types[i][1]] + dp[i][j];
            为了处理越界, 还是分为两种情况: 
                1. j + 1 - types[i][1] >= 0: s[j + 1] = s[j + 1 - types[i][1]] + dp[i][j];
                2. j + 1 - types[i][1] < 0: s[j + 1] = dp[i][j];
    
 */
public class LC2585_2 {
    public int waysToReachTarget(int target, int[][] types) {
        int n = types.length, MOD = (int)1e9 + 7;
        long[][] dp = new long[n + 1][target + 1];
        dp[0][0] = 1;
        long[] s = new long[target + 2];
        for(int j = 0;j <= target;j++){
            if(j + 1 - types[0][1] >= 0) s[j + 1] = (s[j + 1 - types[0][1]] + dp[0][j]) % MOD;
            else s[j + 1] = dp[0][j];
        }
        for(int i = 1;i <= n;i++){
            long[] preS = s.clone();
            for(int j = 0;j <= target;j++){
                if(j >= types[i - 1][0] * types[i - 1][1]) dp[i][j] = (preS[j + 1] - preS[j - types[i - 1][0] * types[i - 1][1] + 1] + dp[i - 1][j - types[i - 1][0] * types[i - 1][1]] + MOD) % MOD;
                else dp[i][j] = preS[j + 1];
                if(i != n){
                    if(j + 1 - types[i][1] >= 0) {
                        s[j + 1] = (s[j + 1 - types[i][1]] + dp[i][j]) % MOD;
                    }
                    else s[j + 1] = dp[i][j];
                }
            }
        }
        return (int)dp[n][target];
    }
}
