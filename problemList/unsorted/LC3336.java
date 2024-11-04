package problemList.unsorted;

/**
子序列问题: 相邻无关, 可以从 选或不选 出发考虑
对于示例1: nums = [1, 2, 3, 4]
    考虑4的几种情况: 
        1. 4不选    2. 4选, 放在第一个子序列    3. 4选, 放在第二个子序列
    
dp[i][j][k] 表示考虑nums下标在[0, i]区间的元素, 第一个子序列gcd = j, 第二个子序列gcd = k, 此时满足条件的子序列对总数
dp[i][j][k]: 
    1. i不选: dp[i][j][k] = dp[i - 1][j][k];
    2. i选 
        放在第一个子序列: dp[i][j][k] = dp[i - 1][gcd()]

    由于计算gcd()没有逆运算, 因此这里需要用 刷表法, 而不能是填表法
 */
public class LC3336 {
    public int subsequencePairCount(int[] nums) {
        
    }
}
