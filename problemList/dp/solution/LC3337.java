package problemList.dp.solution;

import java.util.List;

/**
矩阵快速幂优化DP

dp[i][j] += dp[i - 1][(j + k) % 26], 其中 1 <= k <= nums[j]
观察一下这个转移方程
如果我们把后面的求和都展开, 并且按照j展开

dp[i][0] = dp[i - 1][1 % 26] + dp[i - 1][2 % 26] + dp[i - 1][3 % 26] + ... + dp[i - 1][nums[j] % 26]
dp[i][1] = dp[i - 1][2 % 26] + dp[i - 1][3 % 26] + dp[i - 1][4 % 26] + ... + dp[i - 1][(j + nums[j]) % 26]
dp[i][2] = dp[i - 1][3 % 26] + dp[i - 1][4 % 26] + dp[i - 1][5 % 26] + ... + dp[i - 1][(j + nums[j]) % 26]
...

我们发现, 在整个转移过程中, 这个转移方程都是不变的, 并且等号右侧很像是 矩阵乘法 的形式

我们可以使用矩阵的形式改写一下上面的这些式子:  

dp[i][0]      0, 1, 1, 1, 1, ..., 1, 0, 0, ...                          dp[i - 1]
dp[i][1]  =   0, 0, 1, 1, 1, ..., 1, 1, 0, ...         *                dp[i - 1]
dp[i][2]      0, 0, 0, 1, 1, ..., 1, 1, 1, ...                          dp[i - 1]
...           ...                                                       ...
26 * 1矩阵      26 * 26矩阵                                               26 * 1矩阵

分别记上面三个矩阵为 dp[i], M, dp[i - 1], 那么有 dp[i] = M * dp[i - 1]
使用递推的方式展开, 得到 dp[i] = (M ^ i) dp[0], 令上式中的i == t, 那么最终要求的dp表的最后一行 dp[t] = (M ^ t) * dp[0]

这样我们就把状态转移的递推过程, 转化成了求转移矩阵 M 的 t 次幂

 */
public class LC3337 {
    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int[][] M = new int[26][26];
        int MOD = (int)1e9 + 7;
        for(int j = 0;j < 26;j++){
            for(int k = 1;k <= nums.get(j);k++){
                M[j][(j + k) % 26] = 1;
            }
        }
        M = matrixQuickPow(M, t, MOD);
        int[][] dp0 = new int[26][1];
        for(int i = 0;i < 26;i++) dp0[i][0] = 1;
        int[][] dpt = new int[26][1];
        dpt = martixMul(M, dp0, MOD);
        int ret = 0;
        for(char c : s.toCharArray()){
            ret = (ret + dpt[c - 'a'][0]) % MOD;
        }
        return ret;
    }

    public int[][] matrixQuickPow(int[][] mat, int pow, int MOD){
        if(mat.length != mat[0].length) return null;    // 不是方阵, 无法求幂
        int n = mat.length;
        int[][] ans = new int[n][n];
        for(int i = 0;i < n;i++) ans[i][i] = 1;     // 单位阵
        int[][] x = mat;
        while(pow != 0){
            if((pow & 1) == 1){
                ans = martixMul(ans, x, MOD);
            }
            pow >>= 1;
            x = martixMul(x, x, MOD);
        }
        return ans;
    }

    public int[][] martixMul(int[][] a, int[][] b, int MOD){
        // a: m * p  b: q * n
        int m = a.length, p = a[0].length, q = b.length, n = b[0].length;
        if(p != q) return null;     // 无法相乘
        int[][] ret = new int[m][n];
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                for(int k = 0;k < p;k++){
                    ret[i][j] = (int)(((((long)a[i][k] * b[k][j]) % MOD) + ret[i][j]) % MOD);
                }
            }
        }
        return ret;
    }
}
