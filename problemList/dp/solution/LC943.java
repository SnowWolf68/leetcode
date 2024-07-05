package solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
先dp, 再构造
dp[state][i] 表示使用state这个集合中的字符串进行构造, 并且当前最后一个用到的字符串下标为i, 此时的最短超级串的长度
dp[state][i]: 枚举上一次用到的字符串的下标, 假设为j
    dp[state][i] = min(dp[state & (~(1 << i))][j] + words[i].length - comLen[j][i])     // 其中 comLen[j][i] 表示 words[i] 拼接在 words[j] 后面时, 最长的重叠长度
                                                                                            comLen[j][i] 可以通过预处理得到
初始化: 初始化所有bitCount(state) == 1的行, dp[1 << i][i] = words[i].length, 这一行的其余位置都初始化为INF
return min(dp[mask - 1][i])

然后根据dp表构造某一个具体方案
假设dp[mask - 1]这一行中取到min时的列下标为i, 那么意味着最后一个字符串下标为i, 因此只需要从后往前进行构造即可
 */
public class LC943 {
    public String shortestSuperstring(String[] words) {
        int n = words.length, mask = 1 << n, INF = 0x3f3f3f3f;
        int[][] comLen = new int[n][n];
        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++){
                int len = 0;
                while(len < words[i].length() && len < words[j].length() && words[i].charAt(words[i].length() - len - 1) == words[j].charAt(len)) len++;
                comLen[i][j] = len;
            }
        }
        int[][] dp = new int[mask][n];
        for(int i = 0;i < n;i++){
            Arrays.fill(dp[1 << i], INF);
            dp[1 << i][i] = words[i].length();
        }
        for(int state = 2;state < mask;state++){
            if(Integer.bitCount(state) <= 1) continue;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                dp[state][i] = INF;
                for(int j = 0;j < n;j++){
                    if(j == i || ((state >> j) & 1) == 0) continue;
                    dp[state][i] = Math.min(dp[state][i], dp[state & (~(1 << i))][j] + words[i].length() - comLen[j][i]);
                }
            }
        }
        List<Integer> idxList = new ArrayList<>();
        int idx = -1, minLen = INF, state = mask - 1;
        for(int i = 0;i < n;i++){
            if(dp[mask - 1][i] < minLen){
                minLen = dp[mask - 1][i];
                idx = i;
            }
        }
        idxList.add(idx);
        while(dp[state][idx] != words[idx].length()){
            int preState = state & (~(1 << idx));
            for(int i = 0;i < n;i++){
                if(((preState >> i) & 1) == 0) continue;
                if(dp[preState][i] + words[idx].length() - comLen[i][idx] == dp[state][idx]){
                    idxList.add(i);
                    state = preState;
                    idx = i;
                    break;
                }
            }
        }
        Collections.reverse(idxList);
        StringBuilder sb = new StringBuilder();
        sb.append(words[idxList.get(0)]);
        for(int i = 1;i < idxList.size();i++){
            sb.append(words[idxList.get(i)].substring(comLen[idxList.get(i - 1)][idxList.get(i)]));
        }
        return sb.toString();
    }
}
