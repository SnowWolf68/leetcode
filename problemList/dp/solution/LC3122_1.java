package solution;

/**
关键: 题目翻译: 
    题目中两个要求, 翻译过来就是: 每一列的元素必须相等, 相邻两列的元素必须不同
使用逆向思维, 要求改变的元素最小, 其实就是求保留的元素最多, 然后使用m * n - 保留的最多元素 = 改变的元素最少
再分析一下, 由于这里每一列的所有元素都相等, 那么其实就是需要考虑, 所有的列在所有可能的数的情况下, 保留的元素最多是多少
这里显然可以通过dp这种 "优雅的暴力" 来解决

dp[i][j] 表示考虑[0, i]这些列, 并且当前列当前全都变为j时, 此时保留的最多元素
    dp[i][j]: 枚举前一列的所有可能
        首先保证j != k, 然后有: dp[i][j] = dp[i - 1][k] + 当前列保留的最多元素curCnt
        其中, curCnt = cnt[i][j], cnt[i][j]表示下标为i的列中, 等于j的元素的数量
        对于所有k的可能, 我们只需要取一个max即可
初始化: i - 1这里可能越界, 因此初始化dp表第一行, 第一行对应grid第一列的情况, 因为此时grid没有前一列, 因此这里dp[0][j] = cnt[0][j]
        (此时对于所有等于j的元素, 可以全部保留)
return m * n - max(dp[n - 1][i]), 其中(0 <= i <= 9)

时间复杂度: O(100n), 其中n为grid的列数
 */
public class LC3122_1 {
    public int minimumOperations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] cnt = new int[n][10];
        for(int j = 0;j < n;j++){
            for(int i = 0;i < m;i++){
                int num = grid[i][j];
                cnt[j][num]++;
            }
        }
        int[][] dp = new int[n][10];
        int ret = 0;
        for(int i = 0;i < 10;i++) {
            ret = Math.max(ret, cnt[0][i]);
            dp[0][i] = cnt[0][i];
        }
        for(int i = 1;i < n;i++){
            for(int j = 0;j < 10;j++){
                for(int k = 0;k < 10;k++){
                    if(j != k){
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + cnt[i][j]);
                    }
                }
                if(i == n- 1) ret = Math.max(ret, dp[i][j]);
            }
        }
        return m * n - ret;
    }
}
