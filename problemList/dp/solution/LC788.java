package problemList.dp.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LC788 {
    private List<Integer> digit = Arrays.asList(0, 1, 2, 5, 6, 8, 9);
    private Set<Integer> same = new HashSet<>();
    private String finish;
    private int n;
    private int[][] memo;
    public int rotatedDigits(int n) {
        Collections.addAll(same, 0, 1, 8);
        this.finish = String.valueOf(n);
        this.n =finish.length();
        this.memo = new int[n][2];
        for(int[] row : memo) Arrays.fill(row, -1);
        return dfs(0, true, false, false);
    }
    
    private int dfs(int i, boolean isLimit, boolean isNumber, boolean isDifference){
        if(i == n){
            return isNumber && isDifference ? 1 : 0;
        }
        int isDiff = isDifference ? 1 : 0;
        if(!isLimit && isNumber && memo[i][isDiff]!= -1) return memo[i][isDiff];
        int ret = 0;
        if(!isNumber) ret += dfs(i + 1, false, false, false);
        int lo = isNumber ? 0 : 1, hi = isLimit ? (finish.charAt(i) - '0') : 9;
        for(int d : digit){
            if(d >= lo && d <= hi){
                ret += dfs(i + 1, isLimit && d == (finish.charAt(i) - '0'), true, isDifference || !same.contains(d));
            }
        }
        if(!isLimit && isNumber) memo[i][isDiff] = ret;
        return ret;
    }
}
