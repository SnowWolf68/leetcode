package problemList.dp.solution;

import java.util.Arrays;

/**
灵神数位DP模版 v1.0

dfs参数: 
    1. i: 当前下标
    2. isLimit: 前面填的数是否都是s[0, i - 1]中的各个位上的数
    3. isNumber: 前面是否填了数
 */
public class LC2999 {
    private String s;
    private String suffix;
    private int limit;
    private int[] memo;
    public long numberOfPowerfulInt(long start, long finish, int _limit, String _s) {
        this.suffix = _s;
        this.limit = _limit;
        s = Long.toString(start - 1);
        memo = new int[s.length()];
        Arrays.fill(memo, -1);
        int ret1 = dfs(0, true, false);
        s = Long.toString(finish);
        memo = new int[s.length()];
        Arrays.fill(memo, -1);
        System.out.println("-----------------------");
        int ret2 = dfs(0, true, false);
        return ret2 - ret1;
    }

    private int dfs(int i, boolean isLimit, boolean isNumber){
        if(i >= s.length()){
            return 1;
        }
        if(i >= s.length() - suffix.length()){
            if(isLimit){
                long num1 = Long.parseLong(suffix), num2 = Long.parseLong(s.substring(i, s.length()));
                if(num1 <= num2) return 0;
            }
            else return 1;
        }
        int ret = 0;
        if(!isLimit && isNumber && memo[i] != -1) return memo[i];
        if(!isNumber) ret += dfs(i + 1, false, isNumber);
        int up = isLimit ? Math.min(s.charAt(i) - '0', limit) : limit, low = isNumber ? 0 : 1;
        System.out.println("i = " + i + " up = " + up + " s[i] - '0' = " + (s.charAt(i) - '0') + " low = " + low + " isLimit = " + isLimit + " isNumber = " + isNumber + " low = " + low + " up = " + up);
        for(int d = low;d <= up;d++){
            System.out.println("i = " + i + " d = " + d);
            ret += dfs(i + 1, isLimit & d == (s.charAt(i) - '0'), true);
        }
        if(!isLimit && isNumber) memo[i] = ret;
        System.out.println("i = " + i + " isLimit = " + isLimit + " isNumber = " + isNumber + " ret = " + ret);
        return ret;
    }

    public static void main(String[] args) {
        long start = 1, finish = 971;
        int limit = 9;
        String s = "72";
        System.out.println(new LC2999().numberOfPowerfulInt(start, finish, limit, s));
    }
}
