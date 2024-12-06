package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
二刷
相邻相关型状压DP + 构造具体方案

dp[state][i] 表示当前字符串选择集合为state, 并且最后选择的字符串为i, 此时最短超级串的长度
dp[state][i]: 枚举前一个字符串下标, 假设为pre
    dp[state][i] = min(dp[state][i], dp[state ^ (1 << i)][pre] + commonLen[pre][i])
        其中 commonLen[pre][i] 表示 i 拼接在 pre 后面, 此时的最长公共长度
初始化: 初始化state中只有一个字符串的情况, 即初始化 dp[1 << i][i] = i.length()
return min(dp[mask - 1][i])

如何计算commonLen[i][j]?
需要注意的是, 这里的commonLen[i][j]的匹配, 对于i和j中的子串来说, 其比较的是 i 的后缀 和 j 的等长前缀 的最大匹配长度
因此这里的计算方式为: 首先假设commonLen[i][j] = min(words[i].length(), words[j].length())
然后从较短的串的开头开始, 每次比较第一个串的后缀和第二个串的前缀是否相等, 如果相等, 那么commonLen[i][j]就是当前值, 否则commonLen[i][j]--, 在继续比较剩下的后缀和前缀
由于两串都较短, 因此每次截取子串并比较并不会引入过多的复杂度

这部分commonLen[i][j]的计算需要好好看看


如何构造具体方案?
先根据dp表从后往前, 得到超级串中所有字符串在words中的下标
然后再从前往后拼接出答案
 */
public class LC943_1 {
    public String shortestSuperstring(String[] words) {
        int n = words.length;
        int[][] commonLen = new int[n][n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                commonLen[i][j] = Math.min(words[i].length(), words[j].length());
                while(commonLen[i][j] > 0 && !words[i].substring(words[i].length() - commonLen[i][j]).equals(words[j].substring(0, commonLen[i][j]))) commonLen[i][j]--;
            }
        }
        int mask = 1 << n, idx = -1, min = Integer.MAX_VALUE;
        int[][] dp = new int[mask][n];
        for(int i = 0;i < n;i++) dp[1 << i][i] = words[i].length();
        if(words.length == 1) return words[0];
        for(int state = 1;state < mask;state++){
            if(Integer.bitCount(state) == 1) continue;
            for(int i = 0;i < n;i++){
                dp[state][i] = Integer.MAX_VALUE;
                if(((state >> i) & 1) == 0) continue;
                for(int pre = 0;pre < n;pre++){
                    if(((state >> pre) & 1) == 0 || pre == i) continue;
                    dp[state][i] = Math.min(dp[state][i], dp[state ^ (1 << i)][pre] + words[i].length() - commonLen[pre][i]);
                }
                if(state == mask - 1){
                    if(dp[state][i] < min){
                        min = dp[state][i];
                        idx = i;
                    }
                }
            }
        };
        // System.out.println(min);
        List<Integer> list = new ArrayList<>();
        list.add(idx);
        int state = mask - 1;
        // System.out.println("idx = " + idx);
        while(dp[state][idx] != words[idx].length()){   // 只要这个条件满足, 就说明前面还有字符串需要拼接
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0 || i == idx) continue;

                if(dp[state ^ (1 << idx)][i] + words[idx].length() - commonLen[i][idx] == dp[state][idx]){
                    state = state ^ (1 << idx);
                    idx = i;
                    list.add(i);
                }
            }
        }
        Collections.reverse(list);
        StringBuilder ret = new StringBuilder();
        ret.append(words[list.get(0)]);
        for(int i = 1;i < list.size();i++){
            ret.append(words[list.get(i)].substring(commonLen[list.get(i - 1)][list.get(i)]));
        }
        return ret.toString();
    }
}
