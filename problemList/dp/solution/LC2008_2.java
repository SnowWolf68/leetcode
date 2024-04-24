package problemList.dp.solution;

import java.util.Arrays;

/**
第二种方法: 从区间的角度出发考虑, 二分查找能跟在前面哪个区间的后面
假设我们对区间进行编号, 编号为[0, m - 1], 共m个区间, 那么dp[i] 表示考虑下标[0, i]的区间, 此时能够得到的最大利润
    dp[i]: 此时有三种选择: 
        1: 不选当前区间: dp[i] = dp[i - 1];
        2. 只选当前区间: dp[i] = end - start + tips
        3. 将当前区间拼接在前面某个区间的后面: 二分查找结束位置小于等于当前区间start的最靠后的区间, 假设其下标为j, 那么有
            dp[i] = dp[j] + end - start + tips
    对于上面三种情况, 只需要取一个max即可
初始化: i - 1有可能越界, 添加一个辅助节点, 辅助节点初始化为0
return dp[m - 1];
 */
public class LC2008_2 {
    public long maxTaxiEarnings(int n, int[][] rides) {
        Arrays.sort(rides, (o1, o2) -> o1[1] - o2[1]);
        int m = rides.length;
        long[] dp = new long[m + 1];
        for(int i = 1;i <= m;i++){
            int start = rides[i - 1][0], end = rides[i - 1][1], tips = rides[i - 1][2];
            dp[i] = Math.max(dp[i - 1], end - start + tips);
            int l = 1, r = i - 1;
            while(l < r){
                int mid = (l + r + 1) >> 1;
                if(rides[mid - 1][1] <= start) l = mid;
                else r = mid - 1;
            }
            if(rides[l - 1][1] <= start) dp[i] = Math.max(dp[i], dp[l] + end - start + tips);
        }
        return dp[m];
    }
}
