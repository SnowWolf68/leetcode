package problemList.dp.solution;

/**
dp[i] 表示完成前i + 1个日期的旅行, 此时所需要的最小花费
    dp[i]: 当前有三种购买方式: 1, 7, 30天, 假设用t表示当前买了长为t天的通行证
        查找前面的日期中小于等于days[i] - t的最大值, 假设其下标为j, 那么有
        dp[i] = dp[j] + cost(t), 其中cost(t)表示购买t天的通行证所需要的花费
        特别的, 如果j不存在, 也就是意味着购买当前t天的通行证, 可以包括前面的所有天数, 那么此时直接令dp[i] = cost(t)即可
        对于所有t的可能, 此时只需要取一个min即可
    初始化: 查找的时候我们可以保证j不越界, 因此不需要初始化
    return dp[n - 1];   // 其中n = days.length;

    时间复杂度: O(n * logn)
 */
public class LC983 {
    public int mincostTickets(int[] days, int[] costs) {
        int n = days.length, INF = 0x3f3f3f3f;
        int[] dp = new int[n];
        for(int i = 0;i < n;i++){
            dp[i] = INF;
            int t = 1;
            if(days[0] <= days[i] - t){
                int l = 0, r = i - 1;
                while(l < r){
                    int mid = (l + r + 1) >> 1;
                    if(days[mid] <= days[i] - t) l = mid;
                    else r = mid - 1;
                }
                dp[i] = Math.min(dp[i], dp[l] + costs[0]);
            }else{
                dp[i] = Math.min(dp[i], costs[0]);
            }
            t = 7;
            if(days[0] <= days[i] - t){
                int l = 0, r = i - 1;
                while(l < r){
                    int mid = (l + r + 1) >> 1;
                    if(days[mid] <= days[i] - t) l = mid;
                    else r = mid - 1;
                }
                dp[i] = Math.min(dp[i], dp[l] + costs[1]);
            }else{
                dp[i] = Math.min(dp[i], costs[1]);
            }
            t = 30;
            if(days[0] <= days[i] - t){
                int l = 0, r = i - 1;
                while(l < r){
                    int mid = (l + r + 1) >> 1;
                    if(days[mid] <= days[i] - t) l = mid;
                    else r = mid - 1;
                }
                dp[i] = Math.min(dp[i], dp[l] + costs[2]);
            }else{
                dp[i] = Math.min(dp[i], costs[2]);
            }
        }
        return dp[n - 1];
    }
}
