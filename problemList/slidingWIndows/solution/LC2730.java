package problemList.slidingWIndows.solution;

public class LC2730 {
    public int longestSemiRepetitiveSubstring(String s) {
        int n = s.length(), cnt = 0, left = 0, ret = 0;
        for(int i = 0;i < n;i++){
            if(i - left + 1 >= 2 && s.charAt(i) == s.charAt(i - 1)) cnt++;
            while(cnt > 1){
                if(left + 1 < n && s.charAt(left) == s.charAt(left + 1)) cnt--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
