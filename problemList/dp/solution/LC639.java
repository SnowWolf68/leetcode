package solution;

public class LC639 {
    public int numDecodings(String s) {
        int n = s.length(), MOD = (int)1e9 + 7;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = Math.max(1, i - 1);j <= i;j++){
                int checkRet = check(s.substring(j - 1, i));
                if(checkRet != -1) dp[i] = (dp[i] + (int)(((long)dp[j - 1] * checkRet) % MOD)) % MOD;
            }
        }
        return dp[n];
    }
    private int check(String str){
        int index = 0;
        while(index < str.length() && str.charAt(index) == '0') index++;
        if(index != 0) return -1;
        if(str.length() == 1){
            return str.charAt(0) == '*' ? 9 : 1;
        }else{
            if(str.charAt(0) == '*' && str.charAt(1) == '*') return 15;
            else if(str.charAt(0) == '*') {
                if(str.charAt(1) <= '6') return 2;
                else return 1;
            }
            else if(str.charAt(1) == '*'){
                if(str.charAt(0) == '1') return 9;
                else if(str.charAt(0) == '2') return 6;
                else return -1;
            }else{
                int num = Integer.parseInt(str);
                if(num >= 1 && num <= 26) return 1;
                else return -1;
            }
        }
    }
}
