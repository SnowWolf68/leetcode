package problemList.Greedy.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
如何保证一定经过 coordinates[k] ? 
    假设 kx = coordinate[k][0], ky = coordinates[k][1]
    看成两部分, 先在 [0, kx - 1] 范围内找满足 y < ky 的 LIS的最长长度 leftLen
    然后在 [kx + 1, ] 范围内找满足 y > ky 的 LIS的最长长度 rightLen
    然后 leftLen + rightLen + 1 即为答案
 */
public class LC3288 {
    public int maxPathLength(int[][] coordinates, int k) {
        int kx = coordinates[k][0], ky = coordinates[k][1], n = coordinates.length;
        Arrays.sort(coordinates, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);
        List<Integer> list = new ArrayList<>();
        int i = 0;
        for(;i < n;i++){
            if(coordinates[i][0] >= kx) break;
            int y = coordinates[i][1];
            if(y >= ky) continue;
            if(list.isEmpty() || list.get(list.size() - 1) < y) list.add(y);
            else{
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list.get(mid) >= y) r = mid;
                    else l = mid + 1;
                }
                list.set(l, y);
            }
        }
        int leftLen = list.size();
        list.clear();
        for(;i < n;i++){
            if(coordinates[i][0] <= kx) continue;
            int y = coordinates[i][1];
            if(y <= ky) continue;
            if(list.isEmpty() || list.get(list.size() - 1) < y) list.add(y);
            else{
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(list.get(mid) >= y) r = mid;
                    else l = mid + 1;
                }
                list.set(l, y);
            }
        }
        return leftLen + list.size() + 1;
    }
}
