package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
需要读清题的一点是: 本题要求的点的序列, 并不需要是coordinates的子序列, 因此我们可以对coordinates排序之后再选择
因此本题就变成了 二维LIS问题, 解法: 双关键字排序 (按照coordinates[i][0]升序, coordinates[i][0]相同则按照coordinates[i][1]降序) + 一维LIS
如何处理 必须过coordinates[k] 这个点? 
先在所有满足 x < coordinates[k][0] && y < coordinates[k][1] 的点中, 求LIS长度len1, 再从所有满足 x > coordinates[k][0] && y > coordinates[k][1] 的点中, 求LIS长度len2
那么答案就为 len1 + len2 + 1
 */
public class LC3288 {
    public int maxPathLength(int[][] coordinates, int k) {
        Arrays.sort(coordinates, (o1, o2) -> o1[0] == o2[0] ? o2[1] - o2[1] : o1[0] - o2[0]);
        List<Integer> list1 = new ArrayList<>();
        for(int i = 0;i < coordinates.length;i++){
            if(coordinates[i][0] > coordinates[k][0] || coordinates[i][1] > coordinates[k][1]) continue;
            if(list1.isEmpty() || coordinates[i][1] > list1.get(list1.size() - 1)){
                list1.add(coordinates[i][0]);
            }else{
                int l = 0, r = list1.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list1.get(mid) >= coordinates[i][1]) r = mid;
                    else l = mid + 1;
                }
                list1.set(l, coordinates[i][1]);
            }
        }
        List<Integer> list2 = new ArrayList<>();
        for(int i = 0;i < coordinates.length;i++){
            while(coordinates[i][0] < coordinates[k][0] || coordinates[i][1] < coordinates[k][1]) continue;
            if(list2.isEmpty() || coordinates[i][1] > list2.get(list2.size() - 1)){
                list2.add(coordinates[i][0]);
            }else{
                int l = 0, r = list2.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list2.get(mid) >= coordinates[i][1]) r = mid;
                    else l = mid + 1;
                }
                list2.set(l, coordinates[i][1]);
            }
        }
        return list1.size() + list2.size() + 1;
    }
}
