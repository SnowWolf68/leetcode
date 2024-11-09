package problemList.dp.solution;

import java.util.Arrays;

public class LC600 {
    private String s;
    private int n;
    private int[][] memo;
    public int findIntegers(int n) {
        this.s = getBinaryStr(n);
        this.n = s.length();
        this.memo = new int[this.n][2];
        for(int[] row : memo) Arrays.fill(row, -1);
        return dfs(0, true, false);
    }

    private String getBinaryStr(int n) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < 32 - Integer.numberOfLeadingZeros(n);i++){
            if(((n >> i) & 1) == 1) sb.append(1);
            else sb.append(0);
        }
        return sb.reverse().toString();
    }

    private int dfs(int i, boolean isLimit, boolean isOne){
        if(i == n){
            return 1;
        }
        int one = isOne ? 1 : 0, ret = 0, hi = isLimit ? (s.charAt(i) - '0') : 1;
        if(!isLimit && memo[i][one] != -1) return memo[i][one];
        if(isOne){
            ret += dfs(i + 1, isLimit && (0 == (s.charAt(i) - '0')), false);
        }else{
            for(int d = 0;d <= hi;d++){
                ret += dfs(i + 1, isLimit && (d == (s.charAt(i) - '0')), d == 1);
            }
        }
        if(!isLimit) memo[i][one] = ret;
        return ret;
    }
}
