package solution;

/**
其实将 中心拓展法判断是否回文 和 dp 分开来也行, 下面我就分开写一下
 */
public class LC2472_4 {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] info = new boolean[n][n];
        for(int i = 0;i < 2 * n - 1;i++){
            int l = i / 2, r = l + i % 2;
            while(l >= 0 && r < n && s.charAt(l) == s.charAt(r)){
                if(r - l + 1 >= k){
                    info[l][r] = true;
                    break;
                }
                l--;
                r++;
            }
        }
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = dp[i - 1];
            for(int j = i - k + 1;j >= 1;j--){
                if(info[j - 1][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j - 1] + 1);
                    break;
                }
            }
        }
        return dp[n];
    }
}
