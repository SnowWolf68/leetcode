package problems.misc.solution;

import java.util.*;

/**
题目条件转化: 
    1. 任意两个字符出现的次数之差的绝对值 <= k --> 出现次数最多的 与 出现次数最少的 两个字符的出现次数只差 <= k
    2. 逆向思维: 求 成为k特殊字符串所需要的最少删除字符数量 --> 求 成为k特殊字符串能够保留的最多字符数量

    枚举所有可能的 出现次数最多的字符 与 出现次数最少的字符
    假设出现次数最多的字符出现次数为cntMax, 出现次数最少的字符出现的次数为cntMin, 那么对于其余字符出现的次数cnt来说, 其要满足整个字符串是k特殊字符串, 就必须满足:
        1. 如果cnt < cntMin, 那么cnt应该直接置为0
        2. 如果cnt > cntMax, 那么cnt应该保留Math.min(cnt, cntMin + k)个
        特别的: 由于cntMax与cntMin也需要满足cntMax - cntMin <= k的要求, 因此cntMax也需要保留Math.min(cntMax, cntMin + k)个
    
    时间复杂度: O(C ^ 2)  C == 26
 */
public class Weekly389T3_1 {
    public int minimumDeletions(String word, int k) {
        int n = word.length();
        int[] cnt = new int[26];
        for(char c : word.toCharArray()) cnt[c - 'a']++;
        int ret = 0;
        for(int i = 0;i < 26;i++){
            int cur = 0;
            for(int j = 0;j < 26;j++){
                if(cnt[j] >= cnt[i]) cur += Math.min(cnt[j], cnt[i] + k);
            }
            ret = Math.max(ret, cur);
        }
        return n - ret;
    }
}
