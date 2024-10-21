package problemList.slidingWindow.solution;

/**
滑窗计数
 */
public class LC3325 {
    public int numberOfSubstrings(String s, int k) {
        int n = s.length(), left = 0, ret = 0;
        int[] cnt = new int[26];
        for(int i = 0;i < n;i++){
            cnt[s.charAt(i) - 'a']++;
            while(left <= i && check(cnt, k)){
                cnt[s.charAt(left) - 'a']--;
                left++;
            }
            ret += left;
        }
        return ret;
    }

    private boolean check(int[] cnt, int k) {
        for(int i = 0;i < 26;i++){
            if(cnt[i] >= k) return true;
        }
        return false;
    }
}
