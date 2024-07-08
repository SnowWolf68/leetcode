package problemList.stringHash.solution;

import java.util.HashSet;
import java.util.Set;

/**
二分长度 + HashSet  -->  TLE
 */
public class LC1044_1_TLE {
    public String longestDupSubstring(String s) {
        int n = s.length();
        int l = 0, r = n;
        String ret = null;
        while(l < r){
            int mid = (l + r + 1) >> 1;
            String str = check(s, mid);
            if(str != null) {
                l = mid;
                ret = str;
            }
            else r = mid - 1;
        }
        return ret == null ? "" : ret;
    }

    /*
     * 检查是否有长为len的重复子串, 如果有, 返回其中某一个符合要求的子串
     */
    private String check(String s, int len){
        int n = s.length();
        Set<String> set = new HashSet<>();
        for(int i = 0;i - 1 + len < n;i++){
            // [i, i - 1 + len]
            String subString = s.substring(i, i + len);
            if(set.contains(subString)){
                return s.substring(i, i + len);
            }
            set.add(subString);
        }
        return null;
    }
}
