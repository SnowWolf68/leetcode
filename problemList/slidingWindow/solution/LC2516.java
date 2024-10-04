package problemList.slidingWindow.solution;

/**
删除两边, 求最少删除次数 --> 保留中间, 求最长子数组长度

题意: 在s中选取一个最长子串, 子串中 'a', 'b', 'c' 出现的次数最多是 每个字符出现的总次数 - k 次, 求最长子串长度
 */
public class LC2516 {
    public int takeCharacters(String s, int k) {
        int n = s.length(), left = 0, ret = 0;
        int[] cnt = new int[3];
        for(char c : s.toCharArray()){
            cnt[c - 'a']++;
        }
        for(int i = 0;i < 3;i++) {
            if(cnt[i] - k < 0) return -1;
        }
        int[] curCnt = new int[3];
        for(int i = 0;i < n;i++){
            curCnt[s.charAt(i) - 'a']++;
            while(curCnt[s.charAt(i) - 'a'] > cnt[s.charAt(i) - 'a'] - k){
                curCnt[s.charAt(left) - 'a']--;
                left++;
            }
            ret = Math.max(ret, i - left + 1);
        }
        return n - ret;
    }
}
