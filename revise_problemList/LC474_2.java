package revise_problemList;

import java.util.ArrayList;
import java.util.List;

/**
不超过 类型背包 TODO
 */
public class LC474_2 {
    public int findMaxForm(String[] strs, int m, int n) {
        List<int[]> list = new ArrayList<>();
        for(String s : strs){
            int cnt0 = 0;
            for(char c : s.toCharArray()){
                if(c == '0') cnt0++;
            }
            list.add(new int[]{cnt0, s.length() - cnt0});
        }
        int sz = list.size(), INF = 0x3f3f3f3f;
        
    }
}
