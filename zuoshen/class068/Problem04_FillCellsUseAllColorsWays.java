package zuoshen.class068;

import java.util.Arrays;

/**
题目4
有效涂色问题
给定n、m两个参数
一共有n个格子，每个格子可以涂上一种颜色，颜色在m种里选
当涂满n个格子，并且m种颜色都使用了，叫一种有效方法
求一共有多少种有效的涂色方法
1 <= n, m <= 5000
结果比较大请 % 1000000007 之后返回
对数器验证

好题, 琢磨琢磨

TODO: 还没想明白正确的状态转移
 */
public class Problem04_FillCellsUseAllColorsWays {
    /**
        WA
        错因: 状态表示是错的, 这种状态表示无法推出正确的状态转移方程
     */
    public int FillCellsUseAllColorsWays_WrongAnswer(int n, int m){
        int MOD = (int)1e9 + 7;
        long[][] dp = new long[n + 1][m + 1];     // dp[i][j]: 使用[0, j]中的颜料涂[0, i]区间的格子, 方案数
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = ((dp[i - 1][j] * j) % MOD + dp[i - 1][j - 1]) % MOD;
            }
        }
        for(int i = 0;i <= n;i++){
            for(int j = 0;j <= m;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return (int)dp[n][m];
    }

    /**
        状态转移方程一开始写错了
     */
    public int FillCellsUseAllColorsWays(int n, int m){
        int MOD = (int)1e9 + 7;
        long[][] dp = new long[n + 1][m + 1];     // dp[i][j]: 使用j种颜料涂[0, i]区间的格子, 方案数
        dp[0][0] = 1;
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = ((dp[i - 1][j] * j) % MOD + (dp[i - 1][j - 1] * (m - j + 1)) % MOD) % MOD;
            }
        }
        for(int i = 0;i <= n;i++){
            for(int j = 0;j <= m;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return (int)dp[n][m];
    }

    // 对拍代码来自左神 https://github.com/algorithmzuo/algorithm-journey/blob/main/src/class068/Code04_FillCellsUseAllColorsWays.java
    // 暴力方法
	// 为了验证
	public static int ways1(int n, int m) {
		return f(new int[n], new boolean[m + 1], 0, n, m);
	}

	// 把所有填色的方法暴力枚举
	// 然后一个一个验证是否有效
	// 这是一个带路径的递归
	// 无法改成动态规划
	public static int f(int[] path, boolean[] set, int i, int n, int m) {
		if (i == n) {
			Arrays.fill(set, false);
			int colors = 0;
			for (int c : path) {
				if (!set[c]) {
					set[c] = true;
					colors++;
				}
			}
			return colors == m ? 1 : 0;
		} else {
			int ans = 0;
			for (int j = 1; j <= m; j++) {
				path[i] = j;
				ans += f(path, set, i + 1, n, m);
			}
			return ans;
		}
	}

    // 左神dp代码: https://github.com/algorithmzuo/algorithm-journey/blob/main/src/class068/Code04_FillCellsUseAllColorsWays.java
    // 正式方法
	// 时间复杂度O(n * m)
	// 已经展示太多次从递归到动态规划了
	// 直接写动态规划吧
	// 也不做空间压缩了，因为千篇一律
	// 有兴趣的同学自己试试
	public static int MAXN = 5001;

	public static int[][] dp = new int[MAXN][MAXN];

	public static int mod = 1000000007;

	public static int ways2(int n, int m) {
		// dp[i][j]:
		// 一共有m种颜色
		// 前i个格子涂满j种颜色的方法数
		for (int i = 1; i <= n; i++) {
			dp[i][1] = m;
		}
		for (int i = 2; i <= n; i++) {
			for (int j = 2; j <= m; j++) {
				dp[i][j] = (int) (((long) dp[i - 1][j] * j) % mod);
				dp[i][j] = (int) ((((long) dp[i - 1][j - 1] * (m - j + 1)) + dp[i][j]) % mod);
			}
		}
		return dp[n][m];
	}

    public static void main(String[] args) {
        int n = 6, m = 5;
        int stdAns = ways1(n, m);
        int myAns = new Problem04_FillCellsUseAllColorsWays().FillCellsUseAllColorsWays(n, m);
        int zuoAns = ways2(n, m);
        if(stdAns == myAns) System.out.println("Accept");
        else {
            System.out.println("error: stdAns = " + stdAns + " myAns = " + myAns + " zuoAns = " + zuoAns);
        }
    }
}
