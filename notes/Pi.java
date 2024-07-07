package notes;

public class Pi {
    static int[] getPi(String pattern){
        int n = pattern.length();
        int[] pi = new int[n];
        pi[0] = 0;  // 0位置无意义
        // matchLen表示上一个位置的最大匹配长度, 即matchLen = pi[i - 1]
        int matchLen = 0;
        for(int i = 1;i < n;i++){
            while(matchLen > 0 && pattern.charAt(matchLen) != pattern.charAt(i)){
                // 找[0, matchLen - 1]区间的最大匹配
                matchLen = pi[matchLen - 1];
            }
            if(pattern.charAt(matchLen) == pattern.charAt(i)){
                matchLen++;
            }
            pi[i] = matchLen;
        }
        return pi;
    }
}
