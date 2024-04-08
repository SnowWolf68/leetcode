package problemList.dp.solution;

import java.util.*;

/**
考虑优化: 由于这里我们需要枚举下标i, 所以值域dp肯定是行不通, 但是贪心 + 二分的优化还是可以的
我们可以在每次遍历到一个下标i的时候, 花费logn的时间, 计算以这个下标结尾的前后缀的LIS的长度

具体实现中, 我们可以首先处理每个下标上倒序LIS的长度, 然后再枚举i的同时, 计算正序LIS的长度

时间复杂度: O(n * logn)
 */
public class LC1671_2 {
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] suffix = new int[n];
        List<Integer> list = new ArrayList<>();
        for(int i = n - 1;i >= 0;i--){
            if(list.isEmpty() || nums[i] > list.get(list.size() - 1)){
                list.add(nums[i]);
            }
            int l = 0, r = list.size() - 1;
            while(l < r){
                int mid = (l + r) >> 1;
                if(list.get(mid) >= nums[i]) r = mid;
                else l = mid + 1;
            }
            list.set(l, nums[i]);
            suffix[i] = list.size();
        }
        list = new ArrayList<>();
        int ret = 0;
        for(int i = 0;i < n;i++){
            if(list.isEmpty() || nums[i] > list.get(list.size() - 1)){
                list.add(nums[i]);
            }
            int l = 0, r = list.size() - 1;
            while(l < r){
                int mid = (l + r) >> 1;
                if(list.get(mid) >= nums[i]) r = mid;
                else l = mid + 1;
            }
            list.set(l, nums[i]);
            if(list.size() != 1 && suffix[i] != 1){
                ret = Math.max(ret, list.size() + suffix[i] - 1);
            }
        }
        return n - ret;
    }
}
