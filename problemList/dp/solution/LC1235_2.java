package problemList.dp.solution;

import java.util.Arrays;

/**
第二种思路: 从区间的角度出发考虑, 二分查找当前区间可以拼接在哪一个区间后面
 */
public class LC1235_2 {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] info = new int[n][3];
        for(int i = 0;i < n;i++){
            info[i][0] = startTime[i] - 1;
            info[i][1] = endTime[i] - 1;
            info[i][2] = profit[i];
        }
        Arrays.sort(info, (o1, o2) -> o1[1] - o2[1]);
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            int start = info[i - 1][0], pro = info[i - 1][2];
            dp[i] = Math.max(dp[i - 1], info[i - 1][2]);
            int l = 1, r = i - 1;
            while(l < r){
                int mid = (l + r + 1) >> 1;
                if(info[mid - 1][1] <= start) l = mid;
                else r = mid - 1;
            }
            if(info[l - 1][1] <= start) dp[i] = Math.max(dp[i], dp[l] + pro);
        }
        return dp[n];
    }
}
