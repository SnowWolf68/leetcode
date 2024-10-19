package problemList.dp.solution;

import java.util.Arrays;

/**
做这题之前先去做 LC629 这道题 https://leetcode.cn/problems/k-inverse-pairs-array/description/?envType=problem-list-v2&envId=z2BnCgtB
这题和LC629这题很像, 只是在LC629这题的基础上, 额外添加了一个条件

在LC629这道题中, 我们对排列的要求是, 恰好要拥有k个逆序对, 但是在这题中, 这个限制条件更复杂了
在这题中, 我们有一个requirements数组, 其中给出了 [0, endi]这个区间 的逆序对数量 cnti
因此我们可以在LC629这题的基础上稍加修改得到
首先我们需要处理一下requirements这个数组, 需要处理得到一个req数组, 其中req[i]表示[0, i]区间内的逆序对的要求
特别的, 如果这个区间没有逆序对要求, 那么req[i] = -1

那么在进行dp的过程中, 当在进行i的遍历的时候, 我们就可以首先看当前的这个区间[0, i]上是否有逆序对的数量要求
如果req[i] > 0, 说明当前的这个区间上的逆序对有数量要求, 换句话说, 此时dp表的下标为i的行中, 只有dp[i][req[i]]这个位置是合法的
因为对于这一行的其他位置来说, 其对应的排列, 在[0, i]区间内的逆序对数量显然不符合requirements的要求, 因此对应的排列种类数显然只能是0

但是, 添加了requirements这个限制条件之后, 对原本的dp也有很大影响
    1. dp表的第二个维度应该开多大空间? 
        由于题目有一个限制: 输入保证至少有一个 i 满足 endi == n - 1
        换句话说, req[n - 1] != -1, 也就是说, 对于排列的最后一个下标n - 1, [0, n - 1]区间内一定会有逆序对数量限制
        因此dp表的第二个维度可以开到 req[n - 1] + 1 (+1是因为添加辅助节点) 这么大
    2. 返回值? 
        通过第一点的分析可以看出来, return dp[n][req[n - 1]];
    3. 初始化有什么影响?
        在LC629中, 辅助节点的第一列可以直接初始化成1, 但是在这里, 直接初始化成1是不对的
            因为第一列意味着 此时没有逆序对 的情况, 在LC629中, 由于只有对最终[0, n - 1]区间的逆序对的一个限制, 因此对于第一列而言, 
            一定会有不存在逆序对的排列, 即升序的排列, 因此第一列都是可能的情况, 并且只有升序一种情况, 因此可以把第一列初始化成1
        而对于本题而言, 由于requirements对[0, endi]区间的排列的逆序对数量有限制, 因此并不能够保证对于某一个[0, i]区间来说, 
        没有逆序对的排列一定存在, 比如[0, i - 1]区间要求逆序对数量 != 0, 那么对于[0, i]区间来说, 其逆序对数量就不可能为0
        进一步, dp[i + 1][0] != 0  (同样的, 这里 +1 是因为添加了辅助节点)

        因此, 我们只能初始化dp[0][0] = 1, 对于第一列的其余位置, 应该初始化为dp[i][0] = dp[i - 1][0]
            即: 看上一行是否存在逆序对数量限制, 如果上一行可以没有逆序对, 那么这一行也可以没有逆序对
                如果上一行不存在没有逆序对的情况, 那么这一行也不能没有逆序对
        
        需要注意的是, dp[i][0] = dp[i - 1][0] 这样初始化之后, 如果dp[i - 1][0] = 1, 也不意味着dp[i][0]一定是1
        因为还需要看req[i - 1]是否是-1, 即看当前区间是否有逆序对数量要求, 如果有逆序对数量要求, 那么显然dp[i][0] = 0
        即应该将dp[i][0]再重置成0, 因为在这种情况下, 当前行只有dp[i][req[i - 1]]是合法的, 其余位置都应该是0

    时间复杂度: O(n * k * min(n, k))
 */
public class LC3193_1 {
    public int numberOfPermutations(int n, int[][] requirements) {
        int MOD = (int)1e9 + 7;
        int[] req = new int[n];
        Arrays.fill(req, -1);
        int m = 0;
        for(int[] requirement : requirements) {
            req[requirement[0]] = requirement[1];
            m = Math.max(m, requirement[1]);
        }
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            dp[i][0] = dp[i - 1][0];
            if(req[i - 1] == -1){
                for(int j = 1;j <= m;j++){
                    for(int k = 0;k <= Math.min(i - 1, j);k++){
                        dp[i][j] = (dp[i - 1][j - k] + dp[i][j]) % MOD;
                    }
                }
            }else{
                dp[i][0] = 0;
                int j = req[i - 1];
                for(int k = 0;k <= Math.min(i - 1, j);k++){
                    dp[i][j] = (dp[i - 1][j - k] + dp[i][j]) % MOD;
                }
            }
        }
        return dp[n][req[n - 1]];
    }
}
