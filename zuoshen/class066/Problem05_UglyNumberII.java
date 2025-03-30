package zuoshen.class066;

/**
题目5
丑数 II
丑数 就是只包含质因数 2、3 或 5 的正整数
默认第1个丑数是1，前几项丑数为:
1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, 16, 18, 20,
24, 25, 27, 30, 32, 36, 40, 45, 48, 50, 54, 60,
64, 72, 75, 80, 81, 90, 96, 100, 108, 120, 125..
给你一个整数n ，请你找出并返回第n个丑数
比如，n = 37，返回125
测试链接 : https://leetcode.cn/problems/ugly-number-ii/

考虑因子, 使用三指针枚举每个因子, 每次选一个最小的, 选完当前指针向后移动
 */
public class Problem05_UglyNumberII {
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        int p2 = 0, p3 = 0, p5 = 0, idx = 1;    // p2, p3, p5 分别是 * 2, * 3, * 5 的指针
        for(int i = 1;i < n;i++){
            int ans2 = dp[p2] * 2, ans3 = dp[p3] * 3, ans5 = dp[p5] * 5;
            if(ans2 <= ans3 && ans2 <= ans5){
                dp[idx++] = ans2;
                p2++;
                if(ans2 == ans3) p3++;
                if(ans2 == ans5) p5++;
            }else if(ans3 <= ans2 && ans3 <= ans5){
                dp[idx++] = ans3;
                p3++;
                if(ans3 == ans2) p2++;
                if(ans3 == ans5) p5++;
            }else{
                dp[idx++] = ans5;
                p5++;
                if(ans5 == ans2) p2++;
                if(ans5 == ans3) p3++;
            }
        }
        return dp[n - 1];
    }    
}
