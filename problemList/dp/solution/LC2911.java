package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
显然需要预处理出 每个子串变成半回文串, 所需要的最少修改次数
由于每个半回文串, 都有多个可能的d, 因此我们还需要预处理出所有可能的len对应哪些d

预处理len对应的所有真因子: 
    贡献法, 遍历所有的真因子, 考虑当前因子是哪些len的真因子
    真因子的范围: [1,MAX / 2]
    使用贡献法计算当前因子i是哪些len的真因子时, 需要从2 * i开始遍历, 每次j += i, 上限是MAX

    估计一下复杂度: 真因子共有MAX / 2个, 每个真因子需要遍历所有可能的len, 每次遍历的次数为MAX / i, 其中i表示当前遍历到的真因子
        累加起来有: MAX / 1 + MAX / 2 + ... + MAX / (MAX / 2)
        其中, (1 / 1 + 1 / 2 + ... + 1 / (MAX / 2))这些项组成调和级数, 其数量级在logMAX级别
        那么总的复杂度就在MAX * logMAX级别
        而这时对应MAX个数的真因子的情况, 对应到一个数n, 其真因子数量就在logn级别

预处理所有子串变成半回文串所需要的最少修改数目: 
    这题要求半回文串的长度len % d == 0, 那么我们对于半回文串中的某一个下标i, 就能够直接找出与其对应的最后一个元素len - d + i
    对于每一个子串, 我们只需要遍历其len对应的所有d, 每次都计算当前d情况下, 变成半回文串所需要的最少修改次数
    最后对所有的d取一个min即可

dp部分: 
    dp[i[]j] 表示将s[0, j]区间的部分, 划分成i个半回文串, 此时所需要的最少修改次数
        dp[i][j]: 枚举最后一个半回文串的起始位置m
            dp[i][j] = dp[i - 1][m - 1] + cnt[m][j];    // 其中cnt[m][j]表示将s的[m, j]区间的子串变成半回文串, 所需要的最少修改次数
        对于所有的m, 只需要取一个min即可
    初始化: i == 0本身就是辅助节点, 第一行意味着没有划分成任何回文串, 那么dp[0][0] = 0, 其余位置都是非法, 初始化为INF
        第一列添加一列辅助节点, 第一列意味着当前没有任何字符可供划分, 那么dp[0][0] = 0, 其余位置都是非法, 初始化为INF
    return dp[k][n - 1];

时间复杂度: dp的复杂度是O(n ^ 2 * k), 预处理真因子的过程我们可以放在静态代码块中, 不算入复杂度, 预处理子串的复杂度为O(n ^ 3 * logn), 其中n ^ 2枚举子串, logn枚举真因子, 对于每个真因子, 花费n的时间遍历计算修改次数
 */
public class LC2911 {
    private static List<List<Integer>> info = new ArrayList<>();
    private static int MAX = 201;
    static{
        for(int i = 0;i <= MAX;i++) info.add(new ArrayList<>());
        for(int i = 1;i <= MAX / 2;i++){
            for(int j = i * 2;j <= MAX;j += i) info.get(j).add(i);
        }
    }
    public int minimumChanges(String s, int k) {
        int n = s.length(), INF = 0x3f3f3f3f;
        int[][] cnt = new int[n][n];
        for(int i = 0;i < n;i++){
            for(int j = i;j < n;j++){
                int len = j - i + 1;
                List<Integer> list = info.get(len);
                int curCnt = INF;
                for(int d : list){
                    int cur = 0;
                    for(int m = 0;m < d;m++){
                        int p = i + m, q = j + 1 - d + m;
                        while(p < q){
                            if(s.charAt(p) != s.charAt(q)) cur++;
                            p += d; q -= d;
                        }
                    }
                    curCnt = Math.min(curCnt, cur);
                }
                cnt[i][j] = curCnt;
            }
        }
        int[][] dp = new int[k + 1][n + 1];
        Arrays.fill(dp[0], INF);
        for(int i = 0;i <= k;i++) dp[i][0] = INF;
        dp[0][0] = 0;
        for(int i = 1;i <= k;i++){
            for(int j = 1;j <= n;j++){
                dp[i][j] = INF;
                for(int m = j;m > 0;m--){
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][m - 1] + cnt[m - 1][j - 1]);
                }
            }
        }
        return dp[k][n];
    }
}
