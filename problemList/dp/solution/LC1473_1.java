package problemList.dp.solution;

import java.util.Arrays;

/**
每个房子都有可能刷[1, n]中的任意一种颜色, 因此我们需要给dp加上一个维度
dp[i][j][k] 表示将[0, j]区间的房屋分成i个街道, 并且最后一个房屋刷的颜色是第k种颜色, 此时的最小总花费
    dp[i][j][k]: 对于下标为j的房屋, 此时有两种可能: 1) 已经被粉刷过  2) 没有被粉刷过
        1. 没有被粉刷过: 枚举最后一个街道的起始位置p
            dp[i][j][k] = min(dp[i - 1][p - 1][ != k的所有位置]) + cost[p, j][k]区间的和
                特别的: 如果p - 1 == 0, 意味着其前一个位置是 "没有任何房屋" , 那么此时我们不再需要保证前一个房屋的颜色q != k
                其中cost[p, j][k]区间的和, 我们可以倒序遍历p, 同时使用一个变量cost来进行维护
                并且需要注意的是, 在p向前遍历的过程中, 如果遇到houses[p] != 0, 即下标为p的墙已经被粉刷过, 那么此时需要判断p这面墙的颜色houses[p] - 1是否等于当前这个街区的颜色k
                    如果相等, 此时不需要累加curCost, 如果不相等, 直接break, 无需继续向前寻找
                min(dp[i - 1][p - 1][ != k的所有位置])我们可以使用遍历来求
            对于所有的m, 我们只需要取一个min即可
        2. 已经被粉刷过: 相当于此时k只能等于houses[j] - 1, 我们只需让k = houses[j] - 1, 重复上面过程. 注意此时这面墙的颜色已经固定, 那么我们还需要将这面墙其余的可能颜色对应的dp值都初始化为非法, 即INF
初始化: i, j需要初始化, i == 0本来就相当于是辅助节点, 此时意味着分成0个街道, 那么dp[0][0][..] = 0, 其余位置都是非法, 初始化为INF
    j添加一面辅助节点, j == 0意味着没有任何房屋, 此时dp[0][0][..] = 0, 其余位置都是非法, 初始化为INF
return min(dp[target][m - 1][..]);   // 这里m = houses.length, 若min == INF, 说明没有合法方案, 返回-1

时间复杂度: O(target * m ^ 2 * n ^ 2) 带入题目中数据范围, 计算量到了1e8, 勉强能过, 但是比较极限
 */
public class LC1473_1 {
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int INF = 0x3f3f3f3f;
        int[][][] dp = new int[target + 1][m + 1][n];
        for(int j = 0;j <= m;j++){
            for(int k = 0;k < n;k++) dp[0][j][k] = INF;
        }
        for(int i = 0;i <= target;i++){
            for(int k = 0;k < n;k++) dp[i][0][k] = INF;
        }
        Arrays.fill(dp[0][0], 0);
        for(int i = 1;i <= target;i++){
            for(int j = 1;j <= m;j++){
                if(houses[j - 1] == 0){
                    for(int k = 0;k < n;k++){
                        dp[i][j][k] = INF;
                        int curCost = 0;
                        for(int p = j;p > 0;p--){
                            if(houses[p - 1] == 0) curCost += cost[p - 1][k];
                            else if(houses[p - 1] - 1 != k) break;
                            int min = INF;
                            for(int q = 0;q < n;q++){
                                if(p - 1 != 0 && q != k) min = Math.min(min, dp[i - 1][p - 1][q]);
                                else if(p - 1 == 0) min = Math.min(min, dp[i - 1][p - 1][q]);
                            }
                            dp[i][j][k] = Math.min(dp[i][j][k], min + curCost);
                        }
                    }
                }else{
                    int k = houses[j - 1] - 1;
                    Arrays.fill(dp[i][j], INF);
                    int curCost = 0;
                    for(int p = j;p > 0;p--){
                        if(houses[p - 1] == 0) curCost += cost[p - 1][k];
                        else if(houses[p - 1] - 1 != k) break;
                        int min = INF;
                        for(int q = 0;q < n;q++){
                            if(p - 1 != 0 && q != k) min = Math.min(min, dp[i - 1][p - 1][q]);
                            else if(p - 1 == 0) min = Math.min(min, dp[i - 1][p - 1][q]);
                        }
                        dp[i][j][k] = Math.min(dp[i][j][k], min + curCost);
                    }
                }
            }
        }
        int ret = INF;
        for(int k = 0;k < n;k++){
            ret = Math.min(ret, dp[target][m][k]);
        }
        return ret == INF ? -1 : ret;
    }
}
