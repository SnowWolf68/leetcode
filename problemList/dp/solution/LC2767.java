package problemList.dp.solution;

/**
就是一个简单的计算划分个数的 划分型dp
主要难点在于如何判断划分区间的有效性, 即如何判断一个字符串是5的幂的二进制表示
 */
public class LC2767 {
    public int minimumBeautifulSubstrings(String s) {
        int n = s.length(), INF = 0x3f3f3f3f;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = 1;j <= i;j++){
                if(check(s.substring(j - 1, i))) dp[i] = Math.min(dp[i], dp[j - 1] + 1);
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }
    private boolean check(String str){
        int index = 0;
        while(index < str.length() && str.charAt(index) == '0') index++;
        if(index != 0) return false;
        int num = Integer.parseInt(str, 2);
        while(num != 0){
            if(num == 1) return true;
            else if(num % 5 != 0) return false;
            else {
                num /= 5;
            }
        }
        return false;
    }
}
