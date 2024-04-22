package problemList.dp.solution;

/**
dp[i][j] 表示将[0, j]区间的元素分成i份, 此时符合要求的分割方案数
    dp[i][j]: 枚举最后一个划分区间的起始位置m, m的范围: [0, j - minLength + 1], 并且最后一个区间的nums[m]和nums[j]还需要满足题目要求
        dp[i][j] += dp[i - 1][m - 1]
    这里计算+= dp[i - 1][m - 1]可以使用前缀和优化到O(1)
    即在上一行dp值计算的同时, 计算前缀和数组sum[]
    特别的, 由于这里限制了只有s[m - 1] - '0'是质数时, 才累加dp[i - 1][m - 1], 因此我们的前缀和数组sum只有在满足条件isPrime(s.charAt(m - 1) - '0'时, 才累加dp[i - 1][m - 1]到sum[m]中

    假设这里我们有前缀和数组sum[], 其中sum[j]表示dp[][0, j - 1]区间符合要求的dp值的和
    由于是否累加dp[][j], 需要看s[j]这个字符对应的数字是否是质数, 而j的范围是[1, n], 那么这里j其实可能会越界
    为了避免越界, 我们可以限制更新条件为j != n, 因为每次更新dp[i][j], 其都用到上一行中[0, j - minLength]区间的符合要求的dp值的和, 其中j - minLength最大值就是n - 1
    因此其实我们也不需要计算n这个位置的前缀和sum[n + 1], 因此这里我们限制j != n也是合理的

    还有一点需要说明的是, 由于只有当isPrime(s.charAt(j) - '0')时, 才累加dp[][j]到sum[j + 1]中, 并且如果不满足, 那么dp[][j]不累加, 但是j前面的那些符合要求的dp值, 还是需要累加的
    因此我们可以使用一个prev变量来记录每一次的前缀和, 只有当j满足条件isPrime(s.charAt(j - 1) - '0'), 才累加dp[][j]到prev中, 然后每次直接将sum[j + 1]更新为prev即可

    容易忽略的地方: 没加优化之前, 我们判断if(isPrime(s.charAt(j - 1) - '0'))时, 直接continue, 但是这里我们需要计算前缀和, 所以即使当前结尾元素不符合要求, 但是由于我们需要计算前缀和
    所以我们不能直接continue, 还是需要正常计算完当前的前缀和, 才能继续下一次循环, 只不过此时不再更新dp[i][j]而已
初始化: i == 0本身就是辅助节点, 初始化dp[0][0] = 1, 其余位置都是0
    第一列添加一列辅助节点, dp[0][0] = 1, 其余位置都是0
return dp[k][n];
 */
public class LC2478_1 {
    public int beautifulPartitions(String s, int k, int minLength) {
        int n = s.length(), MOD = (int)1e9 + 7;
        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;
        int[] sum = new int[n + 2];
        int prev = 0;
        for(int j = 0;j <= n;j++){
            if(j != n) {
                prev = (prev + (isPrime(s.charAt(j) - '0') ? dp[0][j] : 0)) % MOD;
                sum[j + 1] = prev;
            }
        }
        for(int i = 1;i <= k;i++){
            int[] nextSum = new int[n + 2];
            prev = 0;
            for(int j = 1;j <= n;j++){
                if(!isPrime(s.charAt(j - 1) - '0')) dp[i][j] = sum[Math.max(0, j - minLength + 1)];
                if(j != n) {
                    prev = (prev + (isPrime(s.charAt(j) - '0') ? dp[i][j] : 0)) % MOD;
                    nextSum[j + 1] = prev;
                }
            }
            sum = nextSum;
        }
        return dp[k][n];
    }
    private boolean isPrime(int num){
        if(num == 2 || num == 3 || num == 5 || num == 7) return true;
        else return false;
    }
}
