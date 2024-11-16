package problemList.slidingWindow.solution;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
这题有两种做法, 一种做法是 差分, 另外一种是 三指针 + 滑窗
这里写的是第一种做法, 即 差分

我们可以这样考虑, 对于nums中的每一个元素, 都可以找到这个元素 nums[i] 可以变成的元素范围 [nums[i] - k, nums[i] + k]
假设这里有一个数组 cnt[], 其中 cnt[i] 表示 值为i 的元素, 可以由多少个 nums[] 中的元素变得
    如何计算 cnt[] 数组? 
    遍历nums, 对于每一个nums[i], 都让 cnt[nums[i] - k, nums[i] + k] 区间的元素全都 ++ 
计算得到 cnt[] 数组之后, 此时 max(cnt[i]) 就是答案   (需要注意的是, 这里没有考虑 numOperations 这个限制, 这个限制会放到最后考虑)

只是这样显然不够, 因为这题中的数据范围: 1 <= nums.length <= 1e5, 1 <= nums[i] <= 1e9, 0 <= k <= 1e9
因此上面每遍历到一个nums[i], 都需要使用 O(k) 的时间去更新 cnt[] , 这显然会超时
考虑到每一次对 cnt[] 的更新操作, 都是对 cnt[] 中的一段区间 (子数组) 进行 +1 操作, 因此我们可以使用 差分数组 优化
因此这里有一个对于 cnt[] 的差分数组 diff[] , 每次更新diff[] 即可, 具体来说, 对于nums[i], 此时更新: diff[nums[i] - k]++; diff[nums[i] + k + 1]--;
等到diff[]计算完毕之后, 我们只需要对差分数组diff[]求一个前缀和, 就可以得到cnt[]

但是这里还有一个问题, 使用差分数组之后, 能够把时间复杂度优化到 O(n), 但是这里 1 <= nums[i] <= 1e9, 0 <= k <= 1e9, 如果使用数组的话, 显然会爆内存
因此我们需要使用哈希表来代替数组
需要注意的是, 为了保证答案计算的正确性 (即能够考虑到 originCnt[nums[i]]的影响) , 我们需要将 nums[i] 也添加到diff中

最后一个问题: 如何处理 numOperations 的限制?
其实此时还有另外一个问题, 即如何考虑 原来的nums[i] 出现此时的影响
即对于 cnt[i] 来说, 此时如果 i 这个元素本身就在 nums[] 中, 那么此时对于原本就出现在 nums[i] 中的这些元素来说, 其不需要消耗操作次数
假设最终答案为ret, 那么我们可以这样更新ret: ret = Math.max(ret, Math.min(numOperations, cnt[nums[i]] - originCnt[nums[i]]) + originCnt[nums[i]]);
特别的, 由于 1 <= nums[i] <= 1e9 , 因此originCnt[]也需要使用哈希表来存

需要注意的是, 这里的 diff 这个哈希表, 由于我们后来需要遍历diff计算前缀和得到 cnt[], 因此我们需要保证diff这个哈希表能够按照val从小到大遍历
因此这个哈希表我们需要用 TreeMap, 而originCnt这个哈希表, 由于我们没有顺序遍历的需求, 因此可以使用HashMap

这里关于离散差分的部分, 我还想多说几句, 也就是为什么要把 x 添加到diff中, 以及为什么只需要将 x, x - k, x + k 添加到diff中?
对于朴素的差分来说, diff[]是用数组表示的, 那么此时我们只需要对diff[]求一个前缀和, 就能够得到cnt[]
但是这里nums[i]值域很大, 因此使用数组存diff[]显然存不下, 因此我们需要使用Map离散存储diff
而这里显然我们不能将 [nums[i] - k, nums[i] + k] 区间的所有元素都添加到Map中, 那么此时应该怎么处理?
首先对于每次更新的两个端点, 其显然是要添加到diff中的, 即 nums[i] - k, nums[i] + k + 1 这两个key肯定是要添加到Map中
那么对于开区间 (nums[i] - k, nums[i] + k + 1) 中的这些值, 我们应该将哪些值添加到diff中?
 -- 此时我们只需要将 nums[i] 添加到diff中即可
1. 为什么要添加 nums[i] 而不是其他值?
因为 nums[i] 是在nums[]数组中出现过的, 如果最后出现次数最多的元素是nums[i]的话, 我们需要考虑 originCnt[nums[i]] 的影响
因此我们需要保证在下面计算ret的循环中, 一定能够遍历到 nums[i]
2. 为什么只需要添加 nums[i] , 而不需要添加其他的值?
即使在 [nums[i] - k, nums[i] + k] 区间内还有其他出现在nums[]中的元素 假设为nums[j], 那么对于nums[j]来说, 当我们遍历到nums[j]的过程中, 还会将nums[j]添加到diff中
因此这里不需要添加其他的元素
3. 这里添加到diff中的元素只是一部分, 怎么保证差分计算的正确性?
我们可以将初始的diff差分数组看成是一个 全0 的数组, 那么在更新完成后, 对于那些我们没有被更新到的位置, 其diff值显然为0
因此对于这些位置的值来说, 即使我们不把对应的下标添加到diff这个Map中, 此时也不会影响差分计算的正确性
话又说回来, 之所以要添加 nums[i] 到diff中, 是因为我们要保证后续的遍历要能够遍历到nums[i], 这样才能将originCnt[nums[i]]考虑进去, 保证答案的正确性
 */
public class LC3347_1 {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        Map<Integer, Integer> diff = new TreeMap<>();
        Map<Integer, Integer> originCnt = new HashMap<>();
        for(int x : nums){
            originCnt.put(x, originCnt.getOrDefault(x, 0) + 1);
            diff.put(x - k, diff.getOrDefault(x - k, 0) + 1);
            diff.put(x, diff.getOrDefault(x, 0));
            diff.put(x + k + 1, diff.getOrDefault(x + k + 1, 0) - 1);
        }
        int ret = 0, cnt = 0;
        for(int key : diff.keySet()){
            cnt += diff.get(key);
            ret = Math.max(ret, Math.min(numOperations, cnt - originCnt.getOrDefault(key, 0)) + originCnt.getOrDefault(key, 0));
        }
        return ret;
    }
}
