package problemList.dp.solution;

import java.util.Arrays;

/**
两个问题: 
    1. cnt: 前面的位中填过多少个1
    2. memo数组的第二个维度应该开多大: 根据cnt的定义, 对于 0 <= n <= 1e9 的数据, 显然dfs参数的cnt不会超过9
        因此memo第二个维度开10长度就够用了
 */
public class LC233 {
    private String s;
    private int n;
    private int[][] memo;
    public int countDigitOne(int n) {
        this.s = String.valueOf(n);
        this.n = s.length();
        this.memo = new int[this.n][10];
        for(int[] row : memo) Arrays.fill(row, -1);
        return dfs(0, true, 0);
    }

    private int dfs(int i, boolean isLimit, int cnt){
        if(i == n){
            return cnt;
        }
        if(!isLimit && memo[i][cnt] != -1) return memo[i][cnt];
        int lo = 0, hi = isLimit ? (s.charAt(i) - '0') : 9, ret = 0;
        for(int d = lo;d <= hi;d++){
            ret += dfs(i + 1, isLimit && (d == s.charAt(i) - '0'), cnt + (d == 1 ? 1 : 0));
        }
        if(!isLimit) memo[i][cnt] = ret;
        return ret;
    }
}
