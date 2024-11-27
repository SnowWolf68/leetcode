package problemList.dp.solution;

/**
脑筋急转弯 + 网格图dp
这题的脑筋急转弯很有意思, 由于题目要求了: 每个小朋友都必须恰好移动 n - 1 次, 并且这三个小朋友初始分别位于 左上, 右上, 左下 三个位置, 并且都要要移动到右下角
因此我们可以得出:   
    1. 对于 左上 的小朋友来说: 只能走主对角线
    2. 对于 右上 的小朋友来说: 不能越过主对角线
    3. 对于 左下 的小朋友来说: 不能越过主对角线
对于题目的另一个限制: 对于重复进入的房间来说, 其水果只被收集一次, 此时也很容易满足, 由于在上面的限制下, 只有 (n - 1, n - 1) 这一个格子有可能被重复走到
因此我们只需要限制 右上 和 左下 的这两个小朋友分别只需要走到 (n - 2, n - 1) 和 (n - 1, n - 2) 结束即可

如何计算这三个小朋友能够获得的最多水果?
对于左上角的这个小朋友, 其移动的路线显然是固定的, 因此其收集的水果也是固定的, 即 主对角线的元素和
对于右上角和左下角的这两个人来说, 此时我们只需要在其各自的区间分别进行一次 网格图dp 即可

需要注意的是: 对于右上角和左下角的这两个人来说, 其起始点和终点都具有对称性, 因此我们可以使用同一套dp代码, 只需要把这个矩阵对称一下即可

这里我们拿右上角的这个人来举例说一下网格图dp如何进行: 
dp[i][j]: 表示从[i, j]移动到[n - 2, n - 1]能够收集到的最多的水果数量
dp[i][j]: 枚举下一步移动到的格子下标: 此时可以走到下一行中相邻的三个位置, 并且注意此时不能越过主对角线
        假设枚举到的下一个移动到的位置为nx, ny, 那么有 dp[i][j] = dp[nx][ny] + fruits[i][j];
        对于所有可能的nx, ny, 此时显然是要取一个max
初始化: 要分析初始化, 首先需要分析填表顺序, 在这里的状态转移中, 显然填表顺序应该是: 从下到上, 同一行中的填表顺序无所谓
        因此我们可以初始化dp[n - 2][n - 1] = fruits[n - 2][n - 1];
return dp[0][n - 1];
 */
public class LC3362 {
    private int[] dy = new int[]{-1, 0, 1};

    public int maxCollectedFruits(int[][] fruits) {
        int n = fruits.length, ret = 0;
        for(int i = 0;i < n;i++) ret += fruits[i][i];
        ret += dp(fruits);
        for(int i = 0;i < n;i++){
            for(int j = i + 1;j < n;j++){
                int t = fruits[i][j];
                fruits[i][j] = fruits[j][i];
                fruits[j][i] = t;
            }
        }
        ret += dp(fruits);
        return ret;
    }
    
    // 将矩阵对称一下, 这样就可以使用同一份dp代码
    private int dp(int[][] fruits){
        int n = fruits.length;
        int[][] dp = new int[n][n];
        dp[n - 2][n - 1] = fruits[n - 2][n - 1];
        for(int i = n - 3;i >= 0;i--){
            for(int j = i + 1;j < n;j++){
                int nx = i + 1;
                for(int k = 0;k < 3;k++){
                    int ny = j + dy[k];
                    if(ny >= 0 && ny < n && ny > nx){
                        dp[i][j] = Math.max(dp[i][j], dp[nx][ny] + fruits[i][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }
}
