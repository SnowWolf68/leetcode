package problemList.slidingWindow.solution;

public class LC3090 {
    public int maximumLengthSubstring(String s) {
        int n = s.length(), left = 0;
        int[] cnt = new int[26];
        int ret = 0;
        for(int i = 0;i < n;i++){
            cnt[s.charAt(i) - 'a']++;
            while(cnt[s.charAt(i) - 'a'] > 2){
                cnt[s.charAt(left) - 'a']--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
