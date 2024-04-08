package problemList.dp.solution;

import java.util.*;

/**
贪心 + 二分: O(n * logn)

注意这里ret[i]指的是: 以下标i结尾的LIS长度
因此这里更新ret[i]就不能使用list.size(), 而是需要用l + 1

因为list.size()指的是[0, i]区间, 但不一定是以i结尾的LIS长度, l + 1指的才是以i下标结尾的LIS长度
 */
public class LC1964_1 {
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int n = obstacles.length;
        List<Integer> list = new ArrayList<>();
        int[] ret = new int[n];
        for(int i = 0;i < n;i++){
            int x = obstacles[i];
            if(list.isEmpty() || x >= list.get(list.size() - 1)){
                list.add(x);
            }
            int l = 0, r = list.size() - 1;
            while(l < r){
                int mid = (l + r) >> 1;
                if(list.get(mid) > x) r = mid;
                else l = mid + 1;
            }
            list.set(l, x);
            ret[i] = l + 1;
        }
        return ret;
    }
}
