package solution;

public class LC1416 {
    public int numberOfArrays(String s, int k) {
        int n = s.length(), MOD = (int)1e9 + 7;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = Math.max(1, i - 9);j <= i;j++){
                if(check(s.substring(j - 1, i), k)) dp[i] = (dp[i] + dp[j - 1]) % MOD;
            }
        }
        return dp[n];
    }
    private boolean check(String str, int k){
        int index = 0;
        while(index < str.length() && str.charAt(index) == '0') index++;
        if(index != 0) return false;
        if(str.length() == 10 && str.charAt(0) != '1') return false;
        int num = Integer.parseInt(str);
        if(num >= 1 && num <= k) return true;
        else return false;
    }
}
