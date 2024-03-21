package problemList.dp.solution;

/**
枚举出现次数最多/最少的字符, 对于每一对字符, 对s进行一次dp(s中 出现次数最多/最少 的字符分别即为1/-1)
关键: 如何保证取到某一个dp值时, 这个dp值对应的子串一定同时包含 出现次数最多/最少 的字符
    能够明确的是: 只要dp值 > 0, 那么 "出现次数最多" 的字符一定存在, dp值 <= 0 不符合要求
    只需要保证 "出现次数最少" 的字符在dp值对应的子串中一定出现过即可

    由于 "出现次数最少" 的字符是否出现, 是和dp值对应的子串相关的, 所以这里我们需要给dp的状态增加一个维度, 有两种方式: 
        1. dp[i][0] 表示 "出现次数最少的字符" 没出现过, dp[i][1] 表示 "出现次数最少的字符" 出现过
        2. dp[i][0] 表示不管 "出现次数最少的字符" 出没出现过, dp[i][1] 表示 "出现次数最少的字符" 出现过
    这两种方式主要是状态转移方式不同, 代码我都写了一下, 分别对应LC2272_1, LC2272_2
 */
public class LC2272_1 {
    public int largestVariance(String _s) {
        char[] s = _s.toCharArray();
        int n = s.length, ret = 0, INF = 0x3f3f3f3f;
        for(int i = 0;i < 26;i++){
            for(int j = 0;j < 26;j++){
                // 最少, 最多
                char a = (char)(i + 'a'), b = (char)(j + 'a');
                // 包括, 不包括
                int f = -INF, g = -INF, max = -INF;
                for(int k = 0;k < n;k++){
                    if(s[k] == a){
                        f = Math.max(-1, Math.max(f, g) - 1);
                        g = -INF;
                    }else if(s[k] == b){
                        f = f + 1;
                        g = Math.max(1, g + 1);
                    }
                    max = Math.max(max, f);
                }
                ret = Math.max(ret, max);
            }
        }
        return ret;
    }
}
