package problemList.dp.solution;

import java.util.Arrays;

/**
灵神数位DP模版 v1.0

dfs参数: 
    1. i: 当前下标
    2. isLimit: 前面填的数是否都是s[0, i - 1]中的各个位上的数
    3. isNumber: 前面是否填了数
 */
public class LC2999_v1 {
    private String s;
    private String suffix;
    private int limit;
    private long[] memo;
    public long numberOfPowerfulInt(long start, long finish, int _limit, String _s) {
        this.suffix = _s;
        this.limit = _limit;
        s = Long.toString(start - 1);
        memo = new long[s.length()];
        Arrays.fill(memo, -1);
        long ret1 = dfs(0, true, false);
        s = Long.toString(finish);
        memo = new long[s.length()];
        Arrays.fill(memo, -1);
        long ret2 = dfs(0, true, false);
        return ret2 - ret1;
    }
                
    private long dfs(int i, boolean isLimit, boolean isNumber){
        if(i >= s.length() - suffix.length()){
            if(isLimit){
                long num1 = Long.parseLong(suffix), num2 = Long.parseLong(s.substring(i, s.length()));
                if(num1 <= num2) return 1;
                else return 0;
            }
            else return 1;
        }
        long ret = 0;
        if(!isLimit && isNumber && memo[i] != -1) return memo[i];
        if(!isNumber) ret += dfs(i + 1, false, isNumber);
        int up = isLimit ? Math.min(s.charAt(i) - '0', limit) : limit, low = isNumber ? 0 : 1;
        for(int d = low;d <= up;d++){
            ret += dfs(i + 1, isLimit & d == (s.charAt(i) - '0'), true);
        }
        if(!isLimit && isNumber) memo[i] = ret;
        return ret;
    }
}
