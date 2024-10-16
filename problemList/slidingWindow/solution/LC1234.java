package problemList.slidingWindow.solution;

import java.util.HashMap;
import java.util.Map;

/**
替换的子串越长, 越有可能使得替换后的字符串变成一个平衡字符串, 具有单调性, 可以使用滑窗
并且这是一个 越长越合法 的滑窗
 */
public class LC1234 {
    private static Map<Character, Integer> map = new HashMap<>();
    static{
        map.put('Q', 0);
        map.put('W', 1);
        map.put('E', 2);
        map.put('R', 3);
    }
    public int balancedString(String s) {
        int n = s.length(), left = 0, ret = Integer.MAX_VALUE;
        int[] cnt = new int[4];
        for(char c : s.toCharArray()) cnt[map.get(c)]++;
        if(check(cnt, 0)) return 0;
        for(int i = 0;i < n;i++){
            cnt[map.get(s.charAt(i))]--;
            while(left <= i && check(cnt, i - left + 1)){
                ret = Math.min(ret, i - left + 1);
                cnt[map.get(s.charAt(left))]++;
                left++;
            }
        }
        return ret;
    }
    private boolean check(int[] cnt, int i) {
        int max = 0, tot = 0;
        for(int x : cnt) {
            max = Math.max(max, x);
            tot += x;
        }
        if(3 * max <= tot + i - max) return true;
        else return false;
    }
}
