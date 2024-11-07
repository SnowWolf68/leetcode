package problemList.dp.solution;

import java.util.Arrays;

/**
灵神数位DP模版 v2.0
 */
public class LC2719 {
    private String start, finish;
    private int n, minSum, maxSum;
    private int[][] memo;
    private int MOD = (int)1e9 + 7;
    public int count(String num1, String num2, int min_sum, int max_sum) {
        this.minSum = min_sum;
        this.maxSum = max_sum;
        this.start = "0".repeat(num2.length() - num1.length()) + num1;
        this.finish = num2;
        this.n = finish.length();
        this.memo = new int[n][maxSum + 1];
        for(int[] row : memo) Arrays.fill(row, -1);
        return dfs(0, true, true, false, 0);
    }

    private int dfs(int i, boolean isHighLimit, boolean isLowLimit, boolean isNumber, int digitSum){
        if(i == n){
            return isNumber && (digitSum >= minSum && digitSum <= maxSum) ? 1 : 0;
        }
        if(digitSum > maxSum) return 0;
        if(!isHighLimit && !isLowLimit && isNumber && memo[i][digitSum] != -1) return memo[i][digitSum];
        int ret = 0;
        if(start.charAt(i) == '0' && !isNumber){
            ret = (ret + dfs(i + 1, false, true, false, 0)) % MOD;
        }
        int lo = isLowLimit ? (isNumber ? start.charAt(i) - '0' : Math.max(start.charAt(i) - '0', 1)) : (isNumber ? 0 : 1), hi = isHighLimit ? (finish.charAt(i) - '0') : 9;
        for(int d = lo;d <= hi;d++){
            ret = (ret + dfs(i + 1, isHighLimit && (d == finish.charAt(i) - '0'), isLowLimit && (d == start.charAt(i) - '0'), true, digitSum + d)) % MOD;
        }
        if(!isHighLimit && !isLowLimit && isNumber) memo[i][digitSum] = ret;
        return ret;
    }
}
