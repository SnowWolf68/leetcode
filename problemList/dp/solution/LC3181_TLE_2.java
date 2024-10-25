package problemList.dp.solution;

import java.math.BigInteger;
import java.util.Arrays;

/**
注意到在dp进行状态转移的时候, 转移方程为: dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i - 1]]
也就是dp[i][j], 只会依赖dp表上一行中的一些值, 因此考虑能否有一些好用的优化来优化计算dp[i][j]的过程

由于这里状态转移方程中用到了 或 ( | ) 这个位运算, 因此优化计算的过程我们也要往位运算方面去想
假设我们将dp表的每一行都压缩到一个整数中 (这里暂且不管int只能保存32位信息的限制, 后面我们会用BitInteger来保证一个整数一定能够装下这么多信息)
    假设转换之后的数组是dp1[], 即dp1[i]保存的就是原来dp表dp[i][]这一行中的信息
    具体来说, 如果dp[i][j] == true, 那么dp1[i]中从低到高, 下标为j的这一位就是1, 反之这一位就是0
    还有一点需要注意, 当我们把dp[i][]这一行的信息压缩到dp1[i]这一个整数之后, 原本dp[i][]这一个数组中的低下标对应dp1[i]这个整数中的低位(右侧)
    即: dp1[i] 从低位(右边) 到高位(左边) 依次表示dp[i][j]中的j从小到大
那么我们思考一下, 在整数的位运算中, 如何表示上述的状态转移过程
总结一下之前的状态转移方程: 对于所有满足 j - nums[i - 1] >= 0 && j - nums[i - 1] < nums[i - 1] 条件的 j, dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i - 1]];
                        对于不满足这个条件的j, 那么此时 dp[i][j] = dp[i - 1][j];
其中 j - nums[i - 1] >= 0 && j - nums[i - 1] < nums[i - 1] 这个条件可以转化为: nums[i - 1] <= j < 2 * nums[i - 1]
因此对于 nums[i - 1] <= j < 2 * nums[i - 1] 范围内的j来说, dp[i][j] = dp[i - 1][j - nums[i - 1]] | dp[i - 1][j];
对于其余位置的j, dp[i][j] = dp[i - 1][j];

换句话说, 我们可以首先让dp[i][]这一行全都设置成dp[i - 1][]的值, 然后再对于 nums[i - 1] <= j < 2 * nums[i - 1] 区间的dp[i][j], 或上dp[i - 1][j - nums[i - 1]]即可
需要注意的是, 对于每一个符合要求的j, 其需要或上的值, 都是在它前面距离nums[i - 1]位置的dp值, 因此我们可以使用位运算进行统一处理

因此对于将dp[i][]这一行压缩到dp1[i]这个整数之后, 状态转移变成:
计算dp1[i]: 首先将dp1[i]初始化成dp1[i - 1], 然后对于dp1[i]中下标在[nums[i - 1], 2 * nums[i - 1] - 1]区间的这些位来说  (这里的下标, 是从低位开始数的, 即从右侧开始数)
每个位都需要 或 上它前面距离nums[i - 1]位置的二进制位
换句话说, 其实就相当于将dp1[i - 1]在[0, nums[i - 1] - 1]区间的这些二进制位, 整体或到dp1[i]的下标在[nums[i - 1], 2 * nums[i - 1] - 1]的这些二进制位上

因此使用位运算描述上述状态转移, 就是: dp1[i] = (dp1[i - 1] & ((1 << nums[i - 1]) - 1)) | dp[i - 1];

显然经过位运算之后的状态转移, 可以在O(1)的时间内计算得到, 因此整体dp的时间复杂度从O(m * n), 优化到了O(n)

返回值: dp1[n]最高位的1所在的下标, 即 dp1[n]的二进制位长度 - 1, 需要注意的是, BigInteger中有一个bitLength()方法可以快速得到当前这个大整数的二进制位长度

除此之外, 还有几个地方需要注意

    1. 由于int只有32位, 只能保存32个二进制数, 而这题m的范围比较大, 能够到2 * (5 * 1e4)的范围, 使用int显然不够
        因此这里就用到了Java中的BigInteger
    2. 如果直接开一个长度为n的BigInteger数组, 会爆内存, MLE, 因此这里还需要用到 滚动数组 来优化空间, 具体实现比较简单, 可以直接看coding
    3. 即使加上了上面所有的优化, 也会T, 原因在于nums中可能会有很多重复元素, 而对于这些重复元素, 显然我们是不能选的 (如果想不明白就回去再读一遍题), 因此我们首先应该对nums进行一次去重
    4. 虽然但是, 加上上面的所有优化, 还是会T, 具体如何继续优化我放在下一个文件中说
        下面的代码只能通过 581 / 582 个测试用例
    5. 需要注意的是, 下面的代码超时有可能是因为Java的BigInteger的常数太大, 使用同样的思路, 使用Python就可以过
 */
public class LC3181_TLE_2 {
    public int maxTotalReward(int[] nums) {
        nums = Arrays.stream(nums).distinct().sorted().toArray();
        int n = nums.length;
        BigInteger dp = BigInteger.ONE;
        for(int i = 1;i <= n;i++){
            // dp[i] = ((dp[i - 1] & ((1 << nums[i - 1]) - 1)) << nums[i - 1]) | dp[i - 1]
            dp = dp.and(BigInteger.ONE.shiftLeft(nums[i - 1]).subtract(BigInteger.ONE)).shiftLeft(nums[i - 1]).or(dp);
        }
        return dp.bitLength() - 1;
    }
}
