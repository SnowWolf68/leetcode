package problemList.slidingWindow.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
正难则反 + 去重 + 排序 + 越短越合法的滑窗
 */
public class LC2009 {
    public int minOperations(int[] nums) {
        int n = nums.length, left = 0, ret = 0;
        Set<Integer> set = new HashSet<>();
        for(int x : nums) set.add(x);
        List<Integer> list = new ArrayList<>(set);
        nums = new int[list.size()];
        for(int i = 0;i < list.size();i++) nums[i] = list.get(i);
        Arrays.sort(nums);
        for(int i = 0;i < nums.length;i++){
            while(left <= i && nums[i] - nums[left] + 1 > n){
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return n - ret;
    }
}
