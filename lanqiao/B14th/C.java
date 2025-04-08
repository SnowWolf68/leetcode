package lanqiao.B14th;

import java.util.Scanner;

/*
 * 数组分割: 状态机dp
 */
public class C {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t-- != 0){
            int n = sc.nextInt();
            int[] a = new int[n];
            for(int i = 0;i < n;i++){
                a[i] = sc.nextInt();
            }
            System.out.println(solve(a));
        }
        sc.close();
    }

    private static int solve(int[] a){
        int n = a.length, sum = 0, MOD = (int)1e9 + 7;
        for(int x : a) sum += x;
        if(sum % 2 != 0) return 0;
        int[][] dp = new int[n + 1][2];
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            dp[i][0] = dp[i - 1][0];
            dp[i][1] = dp[i - 1][1];
            if(a[i - 1] % 2 == 0) {
                dp[i][0] = (dp[i][0] + dp[i - 1][0]) % MOD;
                dp[i][1] = (dp[i][1] + dp[i - 1][1]) % MOD;
            }else{
                dp[i][0] = (dp[i][0] + dp[i - 1][1]) % MOD;
                dp[i][1] = (dp[i][1] + dp[i - 1][0]) % MOD;
            }
        }
        return dp[n][0];
    }
}
