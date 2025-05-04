package problemList.Palindrome.ExbandAroundCenter;

/**
 * 中心拓展法 O(n ^ 2)
 */
public class LC5 {
    public String longestPalindrome(String s) {
        int maxLen = 0, n = s.length();
        String ret = "";
        // 2 * n - 1 个回文中心
        for(int i = 0;i < 2 * n - 1;i++){
            int l = -1, r = -1, curLen = -1;
            if(i % 2 == 0){
                // 奇回文
                l = i / 2 - 1;
                r = i / 2 + 1;
                curLen = 1;
            }else{
                // 偶回文
                l = i / 2;
                r = (i + 1) / 2;    // 等价于 i / 2 上取整
                curLen = 0;
            }
            while(l >= 0 && r < n && s.charAt(l) == s.charAt(r)){
                l--;
                r++;
                curLen += 2;
            }
            if(curLen > maxLen){
                maxLen = curLen;
                ret = s.substring(l + 1, r);
            }
        }
        return ret;
    }
}
