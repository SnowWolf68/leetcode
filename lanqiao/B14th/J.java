package lanqiao.B14th;

import java.util.Scanner;

/*
 * 蜗牛
 */
public class J {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0;i < n;i++){
            arr[i] = sc.nextInt();
        }
        int[][] trans = new int[n - 1][2];
        for(int i = 0;i < n - 1;i++){
            trans[i][0] = sc.nextInt();
            trans[i][1] = sc.nextInt();
        }
        System.out.println(solve(n, arr, trans));
    }

    private static int solve(int n, int[] arr, int[][] trans) {
        double[][] dp = new double[n][2];     // [底, 传送门]
        dp[0][0] = arr[0];
        dp[0][1] = arr[0] + trans[0][0] / 0.7;
        for(int i = 1;i < n;i++){
            dp[i][0] = Math.min(dp[i - 1][0] + arr[i] - arr[i - 1], dp[i - 1][1] + trans[i - 1][1] / 1.3);
            dp[i][1] = Math.min(dp[i - 1][0] + arr[i] - arr[i - 1] + trans[i - 1][1])
        }
    }
}
