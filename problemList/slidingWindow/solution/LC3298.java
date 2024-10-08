package problemList.slidingWindow.solution;
/**
子串可重排 -- 本质是滑动窗口

O(26 * n)的时间复杂度会T, 因此考虑优化check()

考虑额外维护一个变量less, less表示word2中有多少个字母的出现次数是比word1多的
每次check时, 只需要让less和0比较即可

需要每次在left和right(i)移动的时候, 同时更新less

时间复杂度: O(n)
 */
public class LC3298 {
    public long validSubstringCount(String word1, String word2) {
        int[] cnt = new int[26];
        for(char c : word2.toCharArray()) cnt[c - 'a']++;
        int n = word1.length(), left = 0, less = 0;
        for(int i = 0;i < 26;i++){
            if(cnt[i] != 0) less++;
        }
        long ret = 0;
        int[] curCnt = new int[26];
        for(int i = 0;i < n;i++){
            int u = word1.charAt(i) - 'a';
            curCnt[u]++;
            // 为了避免重复统计, 这里需要用curCnt[u] == cnt[u] 而不是curCnt[u] >= cnt[u]
            if(curCnt[u] == cnt[u]) less--;
            while(less == 0){
                u = word1.charAt(left) - 'a';
                curCnt[u]--;
                // 同理这里为了避免重复统计, 需要用curCnt[u] == cnt[u] - 1而不是curCnt[u] < cnt[u]
                if(curCnt[u] == cnt[u] - 1) less++;
                left++;
            }
            ret += left;
        }
        return ret;
    }
}
