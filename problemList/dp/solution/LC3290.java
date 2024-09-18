package problemList.dp.solution;

/**
dp[i][j] 表示考虑a的[0, i]区间的元素, 和b的[0, j]区间的元素, 此时的最大得分
dp[i][j]: 选过不选. 根据选或不选b[j]这个元素, 此时有两种情况
    1. 不选b[j]: dp[i][j] = dp[i][j - 1];
    2. 选b[j]:  dp[i][j] = dp[i - 1][j - 1] + a[i] * b[j];
    上面两种情况取一个max
初始化: i - 1, j - 1 都有可能越界, 添加一行一列的辅助节点
    这里的初始化很有意思, 很容易错
        第一行全部初始化为0
        第一列除了最上面的位置初始化为0, 其余位置都初始化为-INF, 即这些位置都是非法

        其实也不难理解, 对于第一行(包括左上角的元素), 此时都意味着a数组中没有考虑任何元素, 并且b中考虑[0, j]区间的元素(或者b中不考虑任何元素)
        那么此时显然b中也不能选元素, 但是这种情况其实也是符合逻辑的

        但是对于第一列(除去最上面的元素)来说, 此时意味着第一列没有考虑任何元素, 但是第一行考虑了[0, i]区间的元素, 也就意味着此时a中一定选了元素
        但是b中没有选任何元素, 那么此时显然是不行的, 因此这些位置的dp值都是非法, 应该初始化为-INF
        
return dp[4][b.length];
 */
public class LC3290 {
    public long maxScore(int[] a, int[] b) {
        int n = b.length;
        long INF = Long.MAX_VALUE / 2;
        long[][] dp = new long[5][n + 1];
        for(int i = 1;i < 5;i++) dp[i][0] = -INF;
        for(int i = 1;i < 5;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - 1] + (long)a[i - 1] * b[j - 1]);
            }
        }
        return dp[4][n];
    }
}
