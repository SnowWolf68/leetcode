package problemList.dp.solution;

import java.util.Collections;
import java.util.List;

/**
第一种解法的角度是 从房屋出发考虑
那么第二种解法的角度就是 从区间的角度出发考虑, 即考虑当前区间能够拼接到前面哪个区间的后面, 即二分查找小于当前区间的起始下标的 最大的区间结束下标
    那么当前区间就可以跟在这个区间的后面
    这个查找过程显然可以使用二分来加速
这样做的前提显然是我们需要将所有的区间按照结束下标升序排好序, 这样才能保证计算当前dp值的时候, 其依赖的dp值(其实就是前面的dp值)一定已经被正确更新过
这里你可能会有疑问, 为什么符合要求的 最大的区间结束下标 对应的区间就一定是上一个区间呢? 为什么就不能是再前面的区间呢
这个疑问完全正确, 的确, 找到的区间不一定就是当前区间所要拼接的上一个区间, 但是在dp的处理过程中, 我们定义dp[i]表示考虑[0, i]的这些区间, 此时的最大收入
那么对于下标为i的区间来说, 此时有选或不选两种可能
也就是说, 这里即使我们找到了当前区间i前面, 符合要求的 结束下标最大的区间j, 此时我们的状态转移有dp[i] = dp[j] + goldi, 但是这里我们的dp[j]中, 不一定会使用j这个区间
    有可能会不使用j这个区间, 而使用前面的一些区间
因此这里我们只需要找到对应的j, 使用dp[j]进行转移一定是正确的
初始化: 不需要初始化, 这里我们寻找j的时候, 就可以保证j的范围一定是符合要求的
return dp[m - 1];   // 这里m = offers.size()

需要注意的是: 这里对于当前下标为i的这个区间, 此时有三种可能: 1) 不选当前下标为i的区间 2) 只选当前下标为i的区间  3) 将当前下标为i的区间拼接在前面的某个区间后面

时间复杂度: O(m * logm)
 */
public class LC2830_2 {
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        int m = offers.size();
        Collections.sort(offers, (o1, o2) -> o1.get(1) - o2.get(1));
        int[] dp = new int[m];
        for(int i = 0;i < m;i++){
            // 当前区间不选的情况
            if(i != 0) dp[i] = dp[i - 1];
            // 别忘了只选当前这个的情况
            dp[i] = Math.max(dp[i], offers.get(i).get(2));
            int start = offers.get(i).get(0), gold = offers.get(i).get(2);
            int l = 0, r = i - 1;
            while(l < r){
                int mid = (l + r + 1) >> 1;
                if(offers.get(mid).get(1) < start) l = mid;
                else r = mid - 1;
            }
            if(offers.get(l).get(1) < start) dp[i] = Math.max(dp[i], dp[l] + gold);
        }
        return dp[m - 1];
    }
}
