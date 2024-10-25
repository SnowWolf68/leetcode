package problemList.dp.solution;

import java.util.Arrays;

/**
这里为了表示方便, 将rewardValues简写成nums
首先需要明白一点: 如果一开始就选择比较大的nums[i], 那么显然对于所有小于nums[i]的元素, 此时就都不能选择了
因此利用类似 贪心 的思想, 可以得出在nums[]中进行选择的顺序: 应该从小到大选择

因此我们可以首先对nums进行升序排序, 然后就可以按照下标i从小到大开始选择
并且对于每一个nums[i], 此时都有选或不选两种选择, 存在重复子问题, 可以使用dp解决, 更进一步, 可以发现, 这其实是一个背包dp

dp[i][j] 表示考虑[0, i]区间的元素, 是否能够得到奖励j
dp[i][j]: 选或不选
        1. 选nums[i]: 首先需要满足条件: j - nums[i] < nums[i], 然后问题转化成从[0, i - 1]中是否能够得到奖励j - nums[i]
            即: dp[i][j] = dp[i - 1][j - nums[i]]
        2. 不选nums[i]: dp[i][j] = dp[i - 1][j];
    这里由于上述两种均有可能, 因此这里需要取一个 或 ( | )
    即: dp[i][j] = dp[i - 1][j - nums[i]] | dp[i - 1][j];
初始化: 这里j - nums[i]可以手动判断是否越界, 因此有可能越界的就只剩下i - 1, 因此添加一行辅助节点
        第一行此时意味着不考虑任何元素, 那么此时显然奖励只能是0, 因此dp[0][0] = true, 第一行其余位置都是非法, 初始化为false
return: 满足dp[n][j] == true的最大的j

在coding部分, 我将dp表第二个维度开到了 2 * max(nums[i]) 的大小, 这是第一个要注意的地方

时间复杂度: O(m * n), 这个时间复杂度可以通过本题的简单版本: LC3180, 但是本题的数据范围保证 m 和 n 都能到 5 * 1e4  的范围, 使用O(m * n)复杂度的dp显然会TLE
 */
public class LC3181_TLE_1 {
    public int maxTotalReward(int[] nums) {
        int n = nums.length, m = 0;
        Arrays.sort(nums);
        for(int x : nums) m = Math.max(m, x);
        m *= 2;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= m;j++){
                dp[i][j] = dp[i - 1][j];
                if(j - nums[i - 1] >= 0 && j - nums[i - 1] < nums[i - 1]) dp[i][j] |= dp[i - 1][j - nums[i - 1]];
            }
        }
        for(int j = m;j >= 0;j--){
            if(dp[n][j]) return j;
        }
        return 0;
    }
}
