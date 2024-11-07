package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC902 {
    private List<Integer> digits = new ArrayList<>();
    private String s;
    private int n;
    private int[] memo;
    public int atMostNGivenDigitSet(String[] digits, int n) {
        for(String s : digits){
            this.digits.add(s.charAt(0) - '0');
        }
        this.s = String.valueOf(n);
        this.n = s.length();
        this.memo = new int[this.n];    // 注意这里是this.n而不是n
        Arrays.fill(memo, -1);
        return dfs(0, true, false);
    }

    private int dfs(int i, boolean isLimit, boolean isNumber){
        if(i == n){
            return isNumber ? 1 : 0;
        }
        if(!isLimit && isNumber && memo[i] != -1) return memo[i];
        int ret = 0;
        if(!isNumber) ret += dfs(i + 1, false, false);
        int lo = isNumber ? 0 : 1, hi = isLimit ? (s.charAt(i) - '0') : 9;
        for(int d : digits){
            if(d >= lo && d <= hi){
                ret += dfs(i + 1, isLimit && (d == s.charAt(i) - '0'), true);
            }
        }
        if(!isLimit && isNumber) memo[i] = ret;
        return ret;
    }
}
