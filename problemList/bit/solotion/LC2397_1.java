package problemList.bit.solotion;

/**
由于本题的数据范围较小 (1 <= m, n <= 12) , 因此考虑暴力枚举
枚举选择的列的所有可能, 对于每一种选择, 统计此时有多少行被覆盖
 */
public class LC2397_1 {
    public int maximumRows(int[][] matrix, int numSelect) {
        int m = matrix.length, n = matrix[0].length, mask = 1 << n - 1, ret = 0;
        for(int s = mask;s != 0;s = (s - 1) & mask){
            if(Integer.bitCount(s) != n) continue;
            int curRet = 0;
            for(int i = 0;i < m;i++){
                boolean contains = true;
                for(int j = 0;j < n;j++){
                    if(((s >> j) & 1) == 1 && matrix[i][j] == 1){
                        contains = false;
                        break;
                    }
                }
                if(contains) curRet++;
            }
            ret = Math.max(ret, curRet);
        }
        return ret;
    }
}
