package problemList.slidingWindow.solution;

/**
这题就是一个很简单的 越短越合法 类型的滑窗计数问题
 */
public class LC3258 {
    public int countKConstraintSubstrings(String s, int k) {
        int n = s.length(), left = 0, ret = 0;
        int[] cnt = new int[2];     // cnt[0]: 0的个数, cnt[1]: 1的个数
        for(int i = 0;i < n;i++){
            cnt[(s.charAt(i) - '0') % 2]++;
            while(cnt[0] > k && cnt[1] > k){
                cnt[(s.charAt(left) - '0')]--;
                left++;
            }
            ret += i - left + 1;
        }
        return ret;
    }
}
