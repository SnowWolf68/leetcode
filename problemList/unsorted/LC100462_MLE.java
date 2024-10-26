package problemList.unsorted;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/contest/biweekly-contest-142/problems/find-the-original-typed-string-ii/
/**
这题很有意思
将word按照相邻字符分组, 将出现次数大于1的字符的出现次数依次添加到cnt中
dp[i][j] 表示考虑list的[0, i]区间的字符, 并且初始长度为j, 此时不同种类数
dp[i][j]: 枚举当前list[i]中相同的字符选多少个, 假设选p个, 那么
    dp[i][j] += dp[i - 1][j - p]
初始化: 这里 i - 1, j - p 都有可能越界, 但是这里j - p可以手动判断是否合法, 因此只需要考虑i - 1越界的情况, 因此添加一行辅助节点
        第一行此时意味着不考虑list中的任何元素, 因此显然此时dp[0][0] = 1, 其余位置都是非法, 初始化为0
return sum(dp[n][j]), 其中j >= k - cnt, cnt = word.length - sum(list)
 */
public class LC100462_MLE {
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
        int sum = 0;
        for(int x : list) sum += x;

        // System.out.println(list.toString());

        int n = list.size();
        // int[][] dp = new int[n + 1][sum + 1];
        int[] dp1 = new int[sum + 1];
        // dp[0][0] = 1;
        dp1[0] = 1;
        for(int i = 1;i <= n;i++){
            int[] dp2 = new int[sum + 1];
            for(int j = 0;j <= sum;j++){
                for(int p = 0;p <= list.get(i - 1);p++){
                    if(j - p >= 0) dp2[j] = (dp2[j] + dp1[j - p]) % MOD;
                }
            }
            dp1 = dp2;
        }
        int ret = 0;
        for(int i = k - (word.length() - sum);i <= sum;i++){
            ret = (ret + dp1[i]) % MOD;
        }
        return ret;
    }

    public static void main(String[] args) {
        // 5
        // String word = "aabbccdd";
        // int k = 7;

        // 8
        // String word = "aaabbb";
        // int k = 3;

        // 1
        // String word = "aabbccdd";
        // int k = 8;

        // 3
        String word = "ccnjuccc";
        int k = 7;
        System.out.println(new LC100462_MLE().possibleStringCount(word, k));
    }
}
