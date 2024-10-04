package problemList.slidingWIndows.solution;

/**
滑动窗口的优化策略: 每次检查窗口是否合法时, 不再检查整个窗口, 而是针对 "窗口的变化"

对于本题来说, 每次右端点i++时, 对整个窗口的影响, 只能是cnt[s.charAt(i)]这个位置的计数有影响
因此我们只需要考虑cnt[s.charAt(i)]是否>=2, 即可知道此时整个窗口是否合法
因此把check的过程从O(128)优化到O(1)
 */
public class LC3_2 {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), left = 0;
        int[] cnt = new int[128];
        int ret = 0;
        for(int i = 0;i < n;i++){
            cnt[s.charAt(i)]++;
            while(cnt[s.charAt(i)] >= 2){
                cnt[s.charAt(left)]--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return ret;
    }
}
