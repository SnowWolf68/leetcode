package problemList.dp.solution;

import java.util.Arrays;

/**
这里时间的范围还是可以到1e9, 因此如果使用第一种方法, 即从下标的角度出发考虑, 那么内存显然不够, 因此只能采用第二种方法
即从区间的角度出发考虑, 二分查找当前区间可以跟在前面哪个区间的后面
    假设对每个区间进行编号, 分别为[0, n - 1]一共有n个区间, 那么dp[i][j] 表示考虑[0, j]这些区间, 此时选择了i个区间, 那么此时能够得到的最大价值
        dp[i][j]: 二分查找前一个区间的开始时间, 假设为m, 那么
            dp[i][j] = dp[i - 1][m - 1] + value;
    初始化: i == 0本身就是辅助节点, 第一行意味着此时没有划分出来任何区间, 那么此时第一行所有位置都应该初始化为0
        m - 1也有可能越界, 因此需要添加一列辅助节点, 第一列此时意味着当前没有任何区间可以选择, 那么此时初始化dp[0][0] = 0, 其余位置都是非法, 初始化为-INF
    return dp[k][n - 1];
 */
public class LC1751_1 {
    public int maxValue(int[][] events, int k) {
        Arrays.sort(events, (o1, o2) -> o1[1] - o2[1]);
        int n = events.length, INF = 0x3f3f3f3f;
        int[][] dp = new int[k + 1][n + 1];
        for(int i = 1;i <= k;i++) dp[i][0] = -INF;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                int start = events[j - 1][0], end = events[j - 1][1], value = events[j - 1][2];
                int l 
            }
        }
    }
}
