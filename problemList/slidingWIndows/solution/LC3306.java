package problemList.slidingWIndows.solution;

import java.util.HashMap;
import java.util.Map;

/**
恰好型滑动窗口 -> 转换成两个至少型滑动窗口
滑动窗口计数
 */
public class LC3306 {
    private static Map<Character, Integer> hashMap = new HashMap<>();
    static {
        hashMap.put('a', 0);
        hashMap.put('e', 1);
        hashMap.put('i', 2);
        hashMap.put('o', 3);
        hashMap.put('u', 4);
    }
    public long countOfSubstrings(String word, int k) {
        return sliding(word, k) - sliding(word, k + 1);
    }

    private long sliding(String word, int k){
        int cnt2 = 0, left = 0, n = word.length();
        long ret = 0;
        int[] cnt1 = new int[5];
        for(int i = 0;i < n;i++){
            if(hashMap.containsKey(word.charAt(i))) cnt1[hashMap.get(word.charAt(i))]++;
            else cnt2++;
            while(check(cnt1, cnt2, k)){
                if(hashMap.containsKey(word.charAt(left))) cnt1[hashMap.get(word.charAt(left))]--;
                else cnt2--;
                left++;
            }
            // 此时到了第一个不符合的位置, 这个位置左边(不包括当前位置), 都是合法的
            // 即[0, left - 1]都是合法位置, 总共有left个
            ret += left;
        }
        return ret;
    }

    private boolean check(int[] cnt1, int cnt2, int k) {
        for(int i = 0;i < 5;i++){
            if(cnt1[i] < 1) return false;
        }
        return cnt2 >= k;
    }
}
