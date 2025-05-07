package problemList.Palindrome.Manacher.solution;

import java.util.Arrays;

/**
之前我那种写法好像有那个大病
本来好好的O(n)的Manacher, 结果主函数里枚举下标i, j成了O(n ^ 2), 跟个神经病一样
而且那种写法中hl2[]也没有用上, 实际上也不需要计算hl2[], 直接根据映射关系现求就行

还是看看这种写法吧
 */
public class LC5_1 {
    private int[] calcHL(String s){
        StringBuilder sb = new StringBuilder();
        sb.append('#');
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
            if(i % 2 == 1){
                // 奇回文中心, idx: 奇回文中心在s中的下标, curLen: s中的回文半径
                int curLen = hl[i] / 2, idx = i / 2;
                if(2 * curLen - 1 > ret.length()){
                    ret = s.substring(idx - curLen + 1, idx + curLen);
                }
            }else{
                // 偶回文中心, leftIdx: 偶回文中心左边的字符在s中的下标, curLen: s中的回文半径
                int curLen = hl[i] / 2, leftIdx = i / 2 - 1;
                if(2 * curLen > ret.length()){
                    ret = s.substring(leftIdx - curLen + 1, leftIdx + 1 + curLen);
                }
            }
        }
        return ret;
    }
}
