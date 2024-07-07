package notes;

import java.util.ArrayList;
import java.util.List;

public class kmp {

    /**
     * kmp
     * @param text
     * @param pattern
     * @return : text中所有和pattern匹配的子串的起始下标
     */
    List<Integer> kmp(String text, String pattern){
        List<Integer> ret = new ArrayList<>();
        int[] pi = Pi.getPi(pattern);
        // matchLen指的是当前匹配的长度
        int matchLen = 0;
        for(int i = 0;i < text.length();i++){
            // 如果不匹配, i不回退, 同时matchLen循环回退
            while(matchLen > 0 && text.charAt(i) != pattern.charAt(matchLen)){
                matchLen = pi[matchLen - 1];
            }
            if(text.charAt(i) == pattern.charAt(matchLen)){
                matchLen++;
            }
            if(matchLen == pattern.length()){
                ret.add(i);
                matchLen = pi[matchLen - 1];
            }
        }
        return ret;
    }
}
