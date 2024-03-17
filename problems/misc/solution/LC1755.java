package problems.misc.solution;

import java.util.*;

/**
折半枚举子集元素和 + 双指针
首先这里`n`的范围能够到40, 因此如果直接枚举`nums`的子集, 显然是不行的, 因此这里使用**折半枚举**, 首先枚举计算出左半部分的所有子集的元素和, 然后枚举右半部分的所有子集的元素和, 然后整个nums的子序列的元素和只存在下面三种情况: 
1. 元素全都在左半部分
2. 元素全都在右半部分
3. 元素在左半部分和右半部分都有

对于1, 2两种情况, 我们只需要遍历即可, 但是对于第三种情况, 这里相当于是给你两个数组, 让你从两个数组中分别选一个元素, 并且要求这两个元素的和`sum`满足`Math.abs(sum - goal)`最小, 这显然就是一个双指针问题, 首先对这两个数组升序排序, 然后定义两个指针分别指向**第一个数组的开始**以及**第二个数组的结束**位置, 然后
1. 如果`sum < goal`, 那么左边的指针++
2. 如果`sum > goal`, 那么右边的指针--
3. 如果`sum == goal`, 那么此时找到的`Math.abs(sum - goal)`已经是最小值, 无需寻找

最后别忘了还有可能**一个元素都不选**, 那么此时对应值就是`Math.abs(goal)`

时间复杂度: O(n * 2 ^ (n / 2))
 */

public class LC1755 {
    public int minAbsDifference(int[] nums, int goal) {
        int n = nums.length;
        // 左边部分的范围是[0, n / 2 - 1], 右边部分的范围是[n / 2, n - 1]
        List<Integer> leftList = new ArrayList<>(), rightList = new ArrayList<>();
        int mask1 = 1 << (n / 2), mask2 = 1 << (n - n / 2);
        for(int p = 1;p < mask1;p++){
            int cur = 0;
            for(int i = 0;i < n / 2;i++){
                if(((p >> i) & 1) == 1){
                    cur += nums[i];
                }
            }
            leftList.add(cur);
        }
        for(int p = 0;p < mask2;p++){
            int cur = 0;
            for(int i = 0;i < n - n / 2;i++){
                if(((p >> i) & 1) == 1){
                    cur += nums[i + n / 2];
                }
            }
            rightList.add(cur);
        }
        int ret = Integer.MAX_VALUE;
        for(int x : leftList) ret = Math.min(ret, Math.abs(x - goal));
        for(int x : rightList) ret = Math.min(ret, Math.abs(x - goal));
        Collections.sort(leftList); Collections.sort(rightList);
        int l = 0, r = rightList.size() - 1;
        while(l < leftList.size() && r >= 0){
            int sum = leftList.get(l) + rightList.get(r);
            ret = Math.min(ret, Math.abs(goal - sum));
            if(sum > goal) r--;
            else if(sum < goal) l++;
            else break;
        }
        // 别忘了还有全都不选的情况
        ret = Math.min(ret, Math.abs(goal));
        return ret;
    }
}
