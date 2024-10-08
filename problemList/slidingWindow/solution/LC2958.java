package problemList.slidingWindow.solution;

import java.util.HashMap;
import java.util.Map;

public class LC2958 {
    public int maxSubarrayLength(int[] nums, int k) {
        int n = nums.length, left = 0, ret = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0;i < n;i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            while(map.get(nums[i]) > k){
                map.put(nums[left], map.get(nums[left]) - 1);
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
