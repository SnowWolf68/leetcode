package notes.Palindrome.Manacher;

import java.util.Arrays;

public class Manacher {
    public String s, t;
    public int[] hl;    // t串的回文半径
    public int[] hl2;   // s串的回文半径
    Manacher(String _s){
        this.s = _s;
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for(char c : s.toCharArray()){
            sb.append(c);
            sb.append('#');
        }
        t = sb.toString();
        int n = t.length();
        hl = new int[n];
        Arrays.fill(hl, 1);
        int mRight = -1, mMid = -1;
        for(int i = 0;i < n;i++){
            if(i < mRight){
                hl[i] = Math.min(hl[2 * mMid - i], mRight - i + 1);
            }
            while(i - hl[i] >= 0 && i + hl[i] < n && t.charAt(i - hl[i]) == t.charAt(i + hl[i])){
                hl[i]++;
                mRight = i + hl[i] - 1;
                mMid = i;
            }
        }
    }

    public boolean check(int l, int r){
        int l2 = l * 2 + 1, r2 = r * 2 + 1, mid = (l2 + r2) >> 1;
        return r2 - l2 + 1 <= hl[mid] * 2 - 1;
    }

    // 使用t串的回文半径数组hl[] 计算s串的回文半径数组hl2[]
    public void getHL2(){
        hl2 = new int[s.length()];
        for(int i = 0;i < s.length();i++){
            hl2[i] = hl[i * 2 + 1] / 2;
        }
    }
}
