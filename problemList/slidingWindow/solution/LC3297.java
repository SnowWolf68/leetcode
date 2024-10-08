package problemList.slidingWindow.solution;

/**
子串可重排 -- 本质是滑动窗口

时间复杂度: 
        滑动窗口的复杂度是O(n)
        check()的时间复杂度是O(26)

        总的时间复杂度是O(26 * n)
 */
public class LC3297 {
    public long validSubstringCount(String word1, String word2) {
        int[] cnt = new int[26];
        for(char c : word2.toCharArray()) cnt[c - 'a']++;
        int n = word1.length(), left = 0;
        long ret = 0;
        int[] curCnt = new int[26];
        for(int i = 0;i < n;i++){
            curCnt[word1.charAt(i) - 'a']++;
            while(check(cnt, curCnt)){
                curCnt[word1.charAt(left) - 'a']--;
                left++;
            }
            ret += left;
        }
        return ret;
    }
    
    private boolean check(int[] cnt, int[] curCnt){
        for(int i = 0;i < 26;i++){
            if(curCnt[i] < cnt[i]) return false;
        }
        return true;
    }
}
