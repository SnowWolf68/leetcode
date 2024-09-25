package problemList.slidingWIndows.solution;

/**
滑动窗口求最小值
 */
public class LC76 {
    public String minWindow(String s, String t) {
        int[] cnt = new int[128];
        for(char ch : t.toCharArray()) cnt[ch]++;
        int n = s.length();
        int[] curCnt = new int[128];
        int left = 0, minLen = Integer.MAX_VALUE;
        String ret = "";
        for(int i = 0;i < n;i++){
            curCnt[s.charAt(i)]++;
            while(check(cnt, curCnt)){
                // 区间: [left, i]
                if(i - left + 1 < minLen){
                    minLen = i - left + 1;
                    ret = s.substring(left, i + 1);
                }
                curCnt[s.charAt(left)]--;
                left++;
            }
        }
        return ret;
    }
    
    private boolean check(int[] cnt, int[] curCnt){
        for(int i = 0;i < 128;i++){
            if(curCnt[i] < cnt[i]) return false;
        }
        return true;
    }
}
