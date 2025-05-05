package revise_problemList;

import java.util.Arrays;

/**
Manacher正确写法, 和LC5_WA的区别之处为代码中的①, ②, ③处

为什么中心拓展法只需要考虑n - 1个偶回文中心, 而Manacher必须要考虑n + 1个偶回文中心 (Manacher必须额外考虑首尾的两处偶回文中心)
中心拓展法中, 我们考虑的n - 1个偶回文中心只是单纯的考虑这个位置, 而并没有像Manacher中这样在偶回文中心插入字符
然而, 对于Manacher来说, 如果我们少插入了首尾的两个#, 那么
 */
public class LC5 {
    private int[] calcHL(String s){
        StringBuilder sb = new StringBuilder();
        sb.append('#');     // ①
        for(char c : s.toCharArray()){
            sb.append(c);
            sb.append('#');
        }
        String ss = sb.toString();
        int n = ss.length();
        int mMid = -1, mRight = -1;
        int[] hl = new int[n];
        for(int i = 0;i < n;i++){
            int curHL = 0;
            if(i < mRight){
                curHL = Math.min(2 * mMid - i >= 0 ? hl[2 * mMid - i] : 0, mRight - i + 1);
            }
            while(i + curHL < n && i - curHL >= 0 && ss.charAt(i + curHL) == ss.charAt(i - curHL)){
                curHL++;
            }
            if(i + curHL - 1 > mRight){
                mRight = i + curHL - 1;
                mMid = i;
            }
            hl[i] = curHL;
        }
        return hl;
    }

    public String longestPalindrome(String s) {
        int n = s.length();
        int[] hl = calcHL(s);
        String ret = "";
        // 这里不能只枚举s中的下标, 那样会漏掉偶回文中心
        for(int i = 0;i < 2 * n + 1;i++){
            if(i % 2 == 1){     // ②
                // 奇回文中心, idx: 奇回文中心在s中的下标, curLen: s中的回文半径
                int curLen = hl[i] / 2, idx = i / 2;
                if(2 * curLen - 1 > ret.length()){
                    ret = s.substring(idx - curLen + 1, idx + curLen);
                }
            }else{
                // 偶回文中心, leftIdx: 偶回文中心左边的字符在s中的下标, curLen: s中的回文半径
                int curLen = hl[i] / 2, leftIdx = i / 2 - 1;    // ③
                if(2 * curLen > ret.length()){
                    ret = s.substring(leftIdx - curLen + 1, leftIdx + 1 + curLen);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        // String s = "cbbd";      // bb
        // String s = "babad";        // aba or bab
        // String s = "a";             // a
        String s = "aacabdkacaa";       // aca
        System.out.println(new LC5().longestPalindrome(s));
    }
}
