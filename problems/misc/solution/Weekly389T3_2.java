package problems.misc.solution;

import java.util.*;

/**
    也可以事先对cnt进行排序
    但是我觉得排不排序好像没啥区别??
 */
public class Weekly389T3_2 {
    public int minimumDeletions(String word, int k) {
        int n = word.length();
        int[] cnt = new int[26];
        for(char c : word.toCharArray()) cnt[c - 'a']++;
        int ret = 0;
        Arrays.sort(cnt);
        for(int i = 0;i < 26;i++){
            int cur = 0;
            for(int j = i;j < 26;j++){
                cur += Math.min(cnt[j], cnt[i] + k);
            }
            ret = Math.max(ret, cur);
        }
        return n - ret;
    }
}
