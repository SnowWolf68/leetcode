package notes.Martrix.solution;

public class MatrixPow {
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
