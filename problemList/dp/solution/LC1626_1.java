package problemList.dp.solution;

import java.util.*;

/**
双关键字排序 + LIS

第一个维度升序排列, 第二个维度LIS问题
注意这里的排序, 指的是 "双关键字排序"
注意这里求的不是 "长度" 而是 "最大分数" , 因此dp的状态定义需要更改一下

dp[i] 表示考虑[0, i]区间的队员, 并且以下标为i的队员结尾的情况下, 此时能够得到的最大分数
    dp[i]: 枚举前一个队员j, 要求满足ages[j] <= ages[i]
        此时dp[i] = Math.max(dp[i], dp[j] + scores[i])
return max(dp[i]);  // 即dp表中的最大值

时间复杂度: O(n ^ 2)
 */
public class LC1626_1 {
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length;
        List<int[]> info = new ArrayList<>();
        for(int i = 0;i < n;i++){
            info.add(new int[]{scores[i], ages[i]});
        }
        Collections.sort(info, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        int[] dp = new int[n];
        dp[0] = info.get(0)[0];
        for(int i = 1;i < n;i++){
            dp[i] = info.get(i)[0];
            for(int j = 0;j < i;j++){
                if(info.get(j)[1] <= info.get(i)[1]) dp[i] = Math.max(dp[i], dp[j] + info.get(i)[0]);
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
