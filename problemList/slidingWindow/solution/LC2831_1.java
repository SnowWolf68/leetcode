package problemList.slidingWindow.solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
删除最多k个元素 --> 存在至多k个不等值, 其他都是等值 的子数组的最长长度

关于代码中注释的一行while, 严谨来说应该是加上的, 但是测试下来, 不加也能过
下面是我的简单分析: 
    首先我们考虑一下这一行while加和不加会带来什么影响
    这一行while的作用是负责懒删除大根堆, 加上能够保证接下来的一次peek出来的堆顶元素的出现次数一定是正确的
                                    如果不加, 那么接下来的一次peek出来的堆顶元素的出现次数可能是错误的
    因此我们考虑, 什么情况下会出现, 不加while, 接下来一次peek出来的堆顶元素的出现次数是错误的 这种情况
    我们知道, 之所以使用懒删除, 就是因为大根堆中更新出现次数不能直接更新
    因此如果left++前后, 大根堆中出现次数最多的元素发生了改变, 那么不加懒删除while时, peek出来的元素就有可能不是出现次数最多的元素

    再换一个角度继续分析, 要想走到left++这里, 首先要满足的前提就是: 当前窗口[left, i]一定是不合法的
    而我们又知道, 对于一个合法窗口来说, 往窗口中一直加 出现次数最多的元素, 其窗口的合法性不会发生变化, 即一直都是合法的
    因此要想让窗口从合法变得不合法, 只有一种情况, 就是往窗口中添加 不是出现次数最多的 元素

    再结合第一部分的分析, 要想让去掉懒删除之后, 大根堆的堆顶元素出现问题, 只有一种可能, 就是从窗口中pop一个元素时, 窗口中出现次数最多的元素发生改变
    即当从窗口中pop一个元素 (left++) 之后, 原来窗口中 出现次数最多的元素 ->(变成了) 出现次数次多的元素, 并且 出现次数次多的元素 ->(变成了) 出现次数最多的元素

    窗口合法 --> 不合法 --> left++ --> ?      假设三个状态分别为1, 2, 3
    在状态1时, 假设出现次数最多的元素为a, 次数为cnt1, 出现次数次多的元素为b, 次数为cnt2
    要想能够让状态1 --> 状态2, 只有一种情况: 进入窗口的元素不是a    (这点前面都分析到了)
    要想能够在状态3时出现 在不进行懒删除时, 堆顶元素并不是出现次数最多的元素 的这种情况, 只有一种可能, 就是 删除的元素是a, 并且之前在状态1时, 进入窗口的元素是b
    那么在状态3时, 出现次数最多的元素是b, 次数为cnt2 + 1, 出现次数次多的元素是a, 出现次数是cnt1 - 1
    而要想满足cnt2 + 1 > cnt1 - 1 , 必须有cnt2 == cnt1 - 1, 即原先出现次数次多的元素, 比出现次数最多的元素 少出现一次

    而此时使用cnt2 + 1更新ret, 实际上等于使用cnt2 + 1 == cnt1来更新ret, 而在状态1, 我们就是使用cnt1来更新ret, 因此此时ret不会被错误更新
    而注意到这里 while(maxHeap.peek()[0] + k < i - left + 1) 
    此时区间长度 i - left + 1 减一, 而 maxHeap.peek()[0] + k 不变, 并且由于此时是第一次pop, 所以当区间长度 - 1时, 下一次就不会满足这个while条件了
    因此下一次就又会走到i++这里, 窗口右端点 + 1, 此时就会执行懒删除while循环, 保证堆顶元素一定是正确的

    综上, 即使在缩小窗口左端点这里, 不加懒删除的while, 其实也是正确的, 但是为了严谨, 还是加上最好

时间复杂度: 大根堆中最多有 2 * n个元素
    maxHeap.offer(new int[]{cnt.get(nums.get(i)), nums.get(i)});    至多执行n次, 每次执行花费O(logn)时间
    while(maxHeap.peek()[0] + k < i - left + 1)                     这个while循环, 每次执行都会导致left++, 至多执行n次, 每次执行O(logn)时间
    while(maxHeap.peek()[0] != cnt.get(maxHeap.peek()[1]))          这个while循环, 每次执行都会从小根堆中poll一个元素, 因此最多执行2 * n次 约等于n次, 每次执行O(logn)时间
因此总的时间复杂度是O(n * logn)
 */
public class LC2831_1 {
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int n = nums.size(), left = 0, ret = 0;
        // <nums[i], cnt>
        Map<Integer, Integer> cnt = new HashMap<>();
        // 懒删除大根堆
        // <cnt, nums[i]>
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        for(int i = 0;i < n;i++){
            cnt.put(nums.get(i), cnt.getOrDefault(nums.get(i), 0) + 1);
            maxHeap.offer(new int[]{cnt.get(nums.get(i)), nums.get(i)});
            // 懒删除
            while(maxHeap.peek()[0] != cnt.get(maxHeap.peek()[1])) maxHeap.poll();
            while(maxHeap.peek()[0] + k < i - left + 1){
                cnt.put(nums.get(left), cnt.get(nums.get(left)) - 1);
                maxHeap.offer(new int[]{cnt.get(nums.get(left)), nums.get(left)});
                left++;
                // 懒删除
                while(maxHeap.peek()[0] != cnt.get(maxHeap.peek()[1])) maxHeap.poll();      // 这行不加不影响答案的正确性, 但是不严谨, 详情看最上面的注释
            }
            ret = Math.max(ret, maxHeap.peek()[0]);
        }
        return ret;
    }
}
