package problemList.dp.solution;

import java.util.Arrays;

/**
相邻相关 状压dp
这题的一种自然的想法是使用枚举子集的子集做, 即我在LC1681.java中写的方法, 但是这种方法的时间复杂度是O(3 ^ n) 证明见灵神题解: https://leetcode.cn/problems/parallel-courses-ii/solutions/2310878/zi-ji-zhuang-ya-dpcong-ji-yi-hua-sou-suo-oxwd/
还有一种时间复杂度更低一点的写法, 即这里的 相邻相关型 状压dp, 可以将时间复杂度降到 O(n ^ 2 * 2 ^ n)

如何转换成 相邻相关 的问题?
我们可以从本题要求的 每组内不能存在两个相等的元素 出发考虑
既然组内不能有相等的元素, 那么我们不妨从小到大依次选择每一组内的元素, 假设当前选择的元素下标为i, 前一个选择的元素的下标为pre
并且假设此时i和pre都是要放在同一组内的, 那么:
如果我们事先对数组nums升序排列, 并且保证 pre < i (我们是从前往后枚举当前这个位置要放的数字), 
那么只要此时 nums[pre] != nums[i] , 那么在这一组内当前选择的这些元素中, 就一定不会存在相等的元素
因此问题就转化成了 相邻相关 问题

需要注意的是, 由于在依次往每个组中填元素的过程中, i和pre并不总是位于同一组, 而如果pre是上一组中的最后一个元素, i是新的一组中的第一个元素
那么此时实际上pre和i并没有约束关系, 即我们不需要保证 pre < i 以及 nums[pre] != nums[i]

如何判断当前填的下标i是处于某一组中的第一个元素还是其他元素?
我们可以根据state的bitCount来判断, 这里我们令 g = n / k, 即 g 是每组的大小
那么如果 bitCount(state) % g == 1, 那么说明当前填的是某一组中的第一个元素, 此时pre和i就没有约束
如果bitCount(state) % g != 1, 那么说明当前填的不是某一组的第一个元素, 也就是i的前面, 还有和i位于同一组的元素, 因此此时为了满足 每一组内不能有重复元素 的要求
需要满足 pre < i && nums[pre] != nums[i] 的两个约束

如何统计最小不兼容性?
需要注意的是, 本题中对于这k组的不兼容性, 我们求的是 和 的最小值, 而不是k组中的最小值 (一开始我把 "和" 漏掉了, 一直想不明白怎么转移)
既然这里求的是 各组不兼容性和的最小值 , 那么显然符合dp状态转移的性质
有一点需要说明, 由于这里我们是依次考虑某一组中的每一个可能填的元素, 因此在状态转移的时候, 需要知道当i位置填了nums[i]这个元素之后, 对当前分组的不兼容性和的最小值的贡献是多少
由于这里对于每一组中的元素, 我们是从小到大填的, 因此显然当i位置填nums[i]时, 对目前所有分组的不兼容性和的最小值的贡献为 nums[i] - nums[pre]
因此就可以得出dp的状态转移方程

具体的状压dp过程见下面的分析

dp[state][i] 表示当前选择的元素集合为state, 并且当前选择的元素的下标为i时, 此时的最小不兼容性
dp[state][i]: 
    1. 如果bitCount(state) % k == 1, 说明此时这个元素是某一组的第一个元素, 那么此时没有 不重复 的约束, 因此: 
        枚举上一个组的最后一个元素, 假设为pre, 那么有
        dp[state][i] = min(dp[state ^ (1 << i)][pre])
    2. 如果bitCount(state) % k != 1, 说明当前组中前面已经有元素, 因此此时的下标为i的元素要受到前面 组内不重复 的约束, 因此:
        依旧是枚举上一个元素的下标, 假设为pre: 
        由于对于每个子集来说, 我们都是从小到大枚举, 因此此时当前子集兼容性的增量为 nums[i] - nums[pre]
        dp[state][i] = min(dp[state ^ (1 << i)][pre] + nums[i] - nums[pre])
初始化: 初始化所有dp[0][i] = 0, 从state = 1开始枚举, 其余位置首先都初始化为INF
return min(dp[mask][i])

时间复杂度: O(n ^ 2 * 2 ^ n)
 */
public class LC1681_1 {
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length, mask = 1 << n, INF = 0x3f3f3f3f, g = n / k;
        if(g == 1) return 0;    // 如果每组大小只有1, 那么组内的最大值和最小值一定相等, 返回0
        Arrays.sort(nums);
        int[][] dp = new int[mask][n];
        for(int state = 1;state < mask;state++){
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state][i] = INF;
                if(Integer.bitCount(state) % g == 1){
                    int preState = state ^ (1 << i);
                    if(preState == 0){
                        dp[state][i] = 0;
                    }else{
                        for(int pre = 0;pre < n;pre++){
                            if(((state >> pre) & 1) == 0 || pre == i) continue;
                            dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre]);
                        }
                    }
                }else{
                    for(int pre = 0;pre < i;pre++){
                        if(((state >> pre) & 1) == 0 || nums[pre] == nums[i] || pre == i) continue;
                        dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre] + nums[i] - nums[pre]);
                    }
                }
            }
        }
        int ret = INF;
        for(int i = 0;i < n;i++) ret = Math.min(ret, dp[mask - 1][i]);
        return ret == INF ? -1 : ret;
    }
}
