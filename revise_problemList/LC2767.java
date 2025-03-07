package revise_problemList;

/**
如何判断一个字符串是否是5的幂的二进制表示?
Integer.parseInt(str, radix), 第二个参数可以指定进制
 */
public class LC2767 {
    public int minimumBeautifulSubstrings(String s) {
        int n = s.length(), INF = 0x3f3f3f3f;
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = 1;j <= i;j++){
                if(check(s.substring(j - 1, i))) dp[i] = Math.min(dp[i], dp[j - 1] + 1);
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }
    private boolean check(String str){
        // 1. 不含前导0
        // 2. 是5的幂的二进制表示
        int idx = 0;
        while(idx < str.length() && str.charAt(idx) == '0') idx++;
        if(idx != 0) return false;
        int num = Integer.parseInt(str, 2);
        // 判断num是否是5的幂次
        while(num != 0 && num % 5 == 0){
            num /= 5;
        }
        return num == 1;
    }
}
