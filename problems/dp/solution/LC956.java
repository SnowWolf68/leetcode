package problems.dp.solution;

import java.util.*;

/**
考虑首先将所有的钢筋都放到左边的支架, 此时左边的支架高度为sum, 右边的支架高度为0
那么接下来需要的操作就是: 将左边的钢筋中的一部分移动到右边, 使得两边的支架的高度相等, 求两个支架的最大高度
正难则反 -> 求两个支架的最大高度, 即求 丢掉的钢筋的最小高度
    dp[i][j] 表示考虑[0, i]区间的钢筋, 此时左右两边的支架高度差为j时, 此时丢掉的钢筋的最小高度
        dp[i][j] 考虑最后一个钢筋有哪些选择
            1. 丢掉rods[i]: dp[i][j] = dp[i - 1][j - rods[i]] + rods[i];
            2. 将rods[i]从左边的支架移动到右边的支架: dp[i][j] = dp[i - 1][j - 2 * rods[i]];
        对于上面两种选择, 我们取一个min即可
    初始化: 对于第二维, 我们可以通过特判来避免越界(负数)的情况 (这里第二维如果是负数, 此时显然是不合法的, 因为一旦j为负数, 由于接下来的操作只能是让j变小, 因此一定不能让两边高度差为0)
            第一维添加一行辅助节点, 第一行意味着当前左边的支架没有钢筋
 */
public class LC956 {
    public int tallestBillboard(int[] rods) {
        int n = rods.length, INF = 0x3f3f3f3f, sum = 0;
        for(int x : rods) sum += x;
        int[][] dp = new int[n + 1][sum + 1];
        Arrays.fill(dp[0], INF);
        // dp[0][0] = sum;
        dp[0][0] = 0;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= sum;j++){
                dp[i][j] = INF;
                if(j - rods[i - 1] >= 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - rods[i - 1]] + rods[i - 1]);
                if(j - rods[i - 1] * 2 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 2 * rods[i - 1]]);
            }
        }
        for(int i = 0;i <= n;i++){
            for(int j = 0;j <= sum;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return (sum - dp[n][0]) / 2;
    }
    public static void main(String[] args) {
        int[] rods = new int[]{1,2,3,6};
        System.out.println(new LC956().tallestBillboard(rods));
    }
}
