package solution;

/**
和LC91 一样
 */
public class LCR165 {
    public int crackNumber(int ciphertext) {
        String s = Integer.toString(ciphertext);
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
        if(index != 0) return str.length() == 1 ? true : false;
        int num = Integer.parseInt(str);
        if(num > 0 && num <= 25) return true;
        else return false;
    }
}
