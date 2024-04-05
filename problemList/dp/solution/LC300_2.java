package problemList.dp.solution;

import java.util.*;

/**
O(n * logn) 的贪心 + 二分 写法
 */
public class LC300_2 {
    public int lengthOfLIS(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for(int x : nums){
            if(list.isEmpty() || list.get(list.size() - 1) < x){
                list.add(x);
            }else{
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list.get(mid) >= x) r = mid;
                    else l = mid + 1;
                }
                list.set(l, x);
            }
        }
        return list.size();
    }
}
