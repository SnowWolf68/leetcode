package revise_problemList;

import java.util.Arrays;

/*
错误的Manacher写法: 只添加了n - 1个#, 应该添加n + 1个 (首尾各添加一个)

WA样例: s = "a"
 */
public class LC5_WA {
    private int[] calcHL(String s){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            sb.append(c);
            if(i != s.length() - 1) sb.append('#');
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
        for(int i = 0;i < 2 * n - 1;i++){
            if(i % 2 == 0){
                // 奇回文中心, idx: 奇回文中心在s中的下标, curLen: s中的回文半径
                int curLen = hl[i] / 2, idx = i / 2;
                if(2 * curLen - 1 > ret.length()){
                    ret = s.substring(idx - curLen + 1, idx + curLen);
                }
            }else{
                // 偶回文中心, leftIdx: 偶回文中心左边的字符在s中的下标, curLen: s中的回文半径
                int curLen = hl[i] / 2, leftIdx = i / 2;
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
        String s = "a";             // a
        System.out.println(new LC5_WA().longestPalindrome(s));
    }
}
