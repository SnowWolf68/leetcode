package problemList.DS.solution;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
这题和前面两题的不同之处在于: 
    1. 区间 [l, r] 中的每一个元素至多减少1, 并且减少的值相互独立
    2. 求: 在queries中选择数量最少的queries[i], 使用这些queries[i]之后, 让nums变成零数组, 求最少的选择数量

由于这里要求我们选择数量最少的queries[i], 因此我们可以尝试贪心分析一下, 每次选择哪些queries[i], 能够让总的选择数量最少, 并且还能保证让nums变成零数组
假设当前我们遍历到 nums[i] , 并且 nums[i] != 0, 此时我们考虑需要选择哪些区间, 让 nums[i] 变成0
首先由于每一个区间都只能让 nums[i] 减少1, 因此我们需要选择 nums[i] 个区间
接下来我们需要分析: 对于能够覆盖 i 这个下标的所有区间, 我们选择哪 nums[i] 个区间, 来让 nums[i] 减少到0?
贪心地想: 要想让总的选择的区间数量最少, 我们就应该让选择的区间能够包括的下标尽可能大, 因此我们得出如下的贪心策略: 
    对于能够包括下标i的这些区间, 我们优先选择 右端点大 的那些区间, 因为这些区间能够覆盖更多的下标

如何实现上述过程?
    这里贪心的实现也很有意思 (有点难)
    我们可以首先对 queries[] 按照左端点升序排序
    然后使用双指针 i, j 分别遍历 nums[i] 和 queries[j]:
        对于当前的 nums[i] , 首先移动j, 找到此时能够覆盖下标i的所有区间, 与此同时, 将这些区间的右端点添加到一个大根堆(maxHeap)中
        然后根据之前分析出来的贪心策略, 此时我们需要从所有能够覆盖下标i的这些区间中, 选出右端点最大的 nums[i] 个 区间, 作为覆盖 i 这个下标的区间
        然后对于取出来的这些区间来说, 我们需要将其所覆盖的范围的下标内的元素都 - 1
        最后将这些区间从大根堆中删除

如果遍历到某一个下标 i 时, 此时大根堆中的区间个数不够 nums[i] 个, 那么此时显然不能得到零数组, 直接返回-1
当遍历完所有的 nums[i] 时, 此时所有的区间就都已经添加到大根堆中一次了, 那么此时大根堆中剩余的区间个数, 就是能够删除的最多的区间数量
 */
public class LC3362 {
    public int maxRemoval(int[] nums, int[][] queries) {
        int n = nums.length, m = queries.length;
        int[] diff = new int[n + 1];
        PriorityQueue<Integer> maxHeap  = new PriorityQueue<>((o1, o2) -> o2 - o1);
        Arrays.sort(queries, (o1, o2) -> o1[0] - o2[0]);
        int sum = 0, j = 0;
        for(int i = 0;i < n;i++){
            sum += diff[i];
            while(j < m && queries[j][0] <= i){
                maxHeap.offer(queries[j][1]);
                j++;
            }
            int diffCount = nums[i] - sum;
            while(diffCount > 0 && !maxHeap.isEmpty() && maxHeap.peek() >= i){
                int r = maxHeap.poll();
                diff[i] += 1;
                sum++;
                diff[r + 1] -= 1;
                diffCount--;
            } 
            if(diffCount > 0) return -1;
        }
        return maxHeap.size();
    }
}