package zuoshen.class067;

import java.util.Scanner;

/**
题目5
节点数为n高度不大于m的二叉树个数
现在有n个节点，计算出有多少个不同结构的二叉树
满足节点个数为n且树的高度不超过m的方案
因为答案很大，所以答案需要模上1000000007后输出
测试链接 : https://www.nowcoder.com/practice/aaefe5896cce4204b276e213e725f3ea

注意高度是 "不超过"

转移方程中对 k == 0 以及 j - 1 - k == 0 的特判, 实际上就相当于初始化dp表dp[i][0]这一列为1
 */
public class Problem05_NodeNHeightNotLargerThanM {
    private static int calcNum(int n, int m){
        int MOD = (int)1e9 + 7;
        long[][] dp = new long[m + 1][n + 1];
        dp[0][0] = 1;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= n;j++){
                for(int k = 0;k <= j - 1;k++){
                    dp[i][j] = (dp[i][j] + (((k == 0 ? 1 : dp[i - 1][k]) * ((j - 1 - k == 0) ? 1 : dp[i - 1][j - 1 - k])) % MOD)) % MOD;
                }
            }
        }
        return (int)dp[m][n];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt(), m = in.nextInt();
        System.out.println(calcNum(n, m));
    }
}
