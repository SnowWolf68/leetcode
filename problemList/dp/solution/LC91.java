package solution;

/**
求解码方法的总数, 其实就是求有多少划分的方案数
 */
public class LC91 {
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = Math.max(1, i - 1);j <= i;j++){
                if(check(s.substring(j - 1, i))) dp[i] += dp[j - 1];
            }
        }
        return dp[n];
    }
    private boolean check(String str){
        int index = 0;
        while(index < str.length() && str.charAt(index) == '0') index++;
        if(index != 0) return false;
        int num = Integer.parseInt(str);
        if(num >= 1 && num <= 26) return true;
        else return false;
    }
}
