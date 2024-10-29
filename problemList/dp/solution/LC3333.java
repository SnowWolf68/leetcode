package problemList.dp.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.cn/contest/biweekly-contest-142/problems/find-the-original-typed-string-ii/
/**
正难则反 + 前缀和优化DP

长度至少为k的数量 = 总的数量 - 长度小于k的数量
    1. 总的数量: list[0] * list[1] * ... * list[list.size() - 1]
    2. 长度小于k的数量: dp
        dp[i][j] 表示考虑list[0, i]区间, 长度为j, 此时有多少种情况
        dp[i][j]: 枚举list[i]选几个字符, 假设选p个, 那么有
            dp[i][j] += dp[i - 1][j - p]
        初始化: 添加一行辅助节点, 第一行意味着此时不考虑任何list区间, 因此此时显然dp[0][0] = 1, 其余位置都是非法, 初始化为0
        return sum(dp[list.size()][i]) 其中0 <= i < k
 */
public class LC3333 {
    public int possibleStringCount(String word, int k) {
        List<Integer> list = new ArrayList<>();
        char pre = word.charAt(0);
        int curCnt = 0, MOD = (int)1e9 + 7;
        for(int i = 1;i < word.length();i++){
            if(word.charAt(i - 1) == pre){
                if(word.charAt(i - 1) == word.charAt(i)){
                    curCnt++;
                }else{
                    if(curCnt > 0) list.add(curCnt);
                    curCnt = 0;
                    pre = word.charAt(i);
                }
            }else{
                curCnt = 0;
                list.add(curCnt);
                pre = word.charAt(i);
            }
        }
        if(curCnt > 0) list.add(curCnt);

        int tot = 1;
        for(int x : list) tot = (int)(((long)tot * (x + 1)) % MOD);

        int repeatSum = 0;
        for(int x : list) repeatSum += x;
        k -= word.length() - repeatSum;
        if(k <= 0) return tot;

        int n = list.size();
        int[][] dp = new int[n + 1][k];
        dp[0][0] = 1;
        int[] preSum = new int[k + 1];
        Arrays.fill(preSum, 1);
        preSum[0] = 0;
        for(int i = 1;i <= n;i++){
            int[] newPreSum = new int[k + 1];
            for(int j = 0;j < k;j++){
                // for(int p = 0;p <= list.get(i - 1);p++){
                //     if(j - p >= 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - p]) % MOD;
                // }
                dp[i][j] = (preSum[j + 1] - preSum[Math.max(0, j - list.get(i - 1))] + MOD) % MOD;
                newPreSum[j + 1] = (newPreSum[j] + dp[i][j]) % MOD;
            }
            preSum = newPreSum;
        }
        int sum = 0;
        for(int i = 0;i < k;i++) sum = (sum + dp[n][i]) % MOD;
        return (tot - sum + MOD) % MOD;
    }
}
