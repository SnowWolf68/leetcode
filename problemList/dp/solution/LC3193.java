package problemList.dp.solution;

/**
dp[i][j] 表示考虑[0, i]区间的元素, 逆序对的数量为j, 此时有多少种不同的排列
dp[i][j]: 考虑枚举最后一个元素能够和前面的元素组成多少个逆序对
            假设最后一个元素能够和前面的元素组成了k个逆序对, k的范围: [0, Math.min(i, j)]
        dp[i][j] += dp[i - 1][j - k]
初始化: 这里的i - 1, j - k有可能越界, 因此添加一行一列的辅助节点
        第一行: 第一行意味着没有任何元素, 那么此时只有dp[0][0] = 1, 其余位置都不存在合法的排列, 其余位置都是0
        第一列: 第一列意味着此时逆序对数量为0, 那么此时只有一种情况, 即全都升序排列, 因此第一列全都初始化成1
return dp[]
 */
public class LC3193 {
    public int numberOfPermutations(int n, int[][] requirements) {
        int[] req = new int[n];
        int m = 0;
        for(int[] requirement : requirements) {
            req[requirement[0]] = requirement[1];
            m = Math.max(m, requirement[1]);
        }
        int[][] dp = new int[n + 1][m + 1];
        for(int i = 0;i <= n;i++) dp[0][i] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                
            }
        }
    }
}
