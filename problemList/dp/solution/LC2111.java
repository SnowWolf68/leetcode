package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
这题关键的是如何转化
首先我们显然能够发现: 对于每一组相隔为k的元素, 其与其他组显然是独立的, 因此我们可以分开考虑
那么对于其中某一组间隔为k的元素来说, 我们需要求: 让其变为 非递减 所需要的最少修改次数
由于这里的修改操作, 能够让我们将任意位置的元素, 修改成任意值, 并且这里要求的是 非递减
因此问题可以转化为: 对于这一组元素来说, 求不需要修改的元素数量的最大值, 然后对于剩下需要修改的元素, 一定能够修改为某个值, 使得这一组元素满足非递减的要求
因此这其实就是一个 不要求严格递增的 LIS问题, 由于这里arr总的元素数量最大能够到1e5, 并且由于对于每一组元素来说, 我们都是分开考虑, 因此时间复杂度为O(n * logn), 在这个数据范围下可以过

需要注意的是, 这里由于求的是 最长非递减子序列的长度 , 因此贪心策略需要发生一些改动, 具体可以看代码中的实现
 */
public class LC2111 {
    public int kIncreasing(int[] arr, int k) {
        int n = arr.length;
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0;i < k;i++) list.add(new ArrayList<>());
        for(int i = 0;i < n;i++) list.get(i % k).add(arr[i]);
        int tot = 0;
        for(List<Integer> li : list){
            tot += dp(li);
        }
        return n - tot;
    }

    // return: list中最长非递减子序列的长度
    private int dp(List<Integer> nums){
        int n = nums.size();
        List<Integer> list = new ArrayList<>();
        for(int x : nums){
            if(list.isEmpty() || x >= list.get(list.size() - 1)) list.add(x);   // 相等也add
            else{
                // 这里的二分从 查找第一个 >= x 的下标 变成 查找第一个 > x 的下标
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list.get(mid) > x) r = mid;
                    else l = mid + 1;
                }
                list.set(l, x);
            }
        }
        return list.size();
    }
}
