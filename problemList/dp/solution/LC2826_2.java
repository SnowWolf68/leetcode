package problemList.dp.solution;

import java.util.*;

/**
不是严格递增的LIS
贪心 + 二分: O(n * logn)
 */
public class LC2826_2 {
    public int minimumOperations(List<Integer> nums) {
        List<Integer> list = new ArrayList<>();
        for(int x : nums){
            if(list.isEmpty() || x >= list.get(list.size() - 1)){
                list.add(x);
            }else{
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list.get(mid) > x) r = mid;
                    else l = mid + 1;
                }
                list.set(l, x);
            }
        }
        return nums.size() - list.size();
    }
}
