package problemList.dp.solution;

import java.util.Arrays;

/**
灵神数位DP模版 v2.0

考虑前导0的情况, 即在dfs的参数中, 添加boolean isNumber这个参数

记忆化: 记忆isHighLimit == false, isLowLimit == false, isNumber == true的情况
 */
public class LC2999_v2_2 {
    private String start, finish, s;
    private int n, limit;
    private long[] memo;
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        this.start = String.valueOf(start);
        this.finish = String.valueOf(finish);
        this.n = this.finish.length();
        this.limit = limit;
        this.start = "0".repeat(this.finish.length() - this.start.length()) + start;    // 填充前导0
        this.s = s;
        this.memo = new long[n];
        Arrays.fill(memo, -1);
        return dfs(0, true, true, false);
    }

    // 考虑前导0的情况, 即在dfs的参数中, 添加boolean isNumber这个参数
    private long dfs(int i, boolean isHighLimit, boolean isLowLimit, boolean isNumber){
        if(i == n){
            return isNumber ? 1 : 0;
        }
        if(!isHighLimit && !isLowLimit && isNumber && memo[i] != -1) return memo[i];
        long ret = 0;
        // 1. 当前数位不填
        if(i < n - s.length() && start.charAt(i) == '0' && !isNumber){
            ret += dfs(i + 1, false, true, false);
        }
        // 2. 当前数位填
        // 注意这里lo的计算
        int lo = isLowLimit ? (isNumber ? start.charAt(i) - '0' : Math.max(start.charAt(i) - '0', 1)) : (isNumber ? 0 : 1), hi = isHighLimit ? Math.min((finish.charAt(i) - '0'), limit) : limit;
        if(i < n - s.length()){
            // 需要注意的是, 这里我们不区分 当前数位填 和 当前数位不填 两种情况, 因此这里填的数字可以是0
            for(int d = lo;d <= hi;d++){
                ret += dfs(i + 1, isHighLimit && d == (finish.charAt(i) - '0'), isLowLimit && d == (start.charAt(i) - '0'), true);
            }
        }else{
            int d = s.charAt(i - (n - s.length())) - '0';
            if(d >= lo && d <= hi){
                ret += dfs(i + 1, isHighLimit && d == (finish.charAt(i) - '0'), isLowLimit && d == (start.charAt(i) - '0'), true);
            }else{
                return 0;
            }
        }
        if(!isHighLimit && !isLowLimit && isNumber) memo[i] = ret;
        return ret;
    }
}
