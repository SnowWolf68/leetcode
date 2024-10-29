package problemList.dp.solution;

import java.util.Arrays;
import java.util.List;

/**
和LC3335类似的状态机DP

重复子问题: 字符ch替换i次后的字符串长度 = ch1 替换 i - 1 次后的字符串长度 + ch2 替换 i - 1 次后的字符串长度 + ...
            其中, ch1, ch2, ... 即为字符 ch 替换后得到的字符

dp[i][j] 表示对于j这个字符, 进行i次操作, 最终得到的字符串的长度
dp[i][j]: 
    dp[i][j] += dp[i - 1][(j + k) % 26], 其中 1 <= k <= nums[j]
初始化: i - 1可能越界, 因此直接初始化第一行, 第一行所有元素显然都是1

这题数据范围到了 1 <= t <= 1e9 直接dp显然超时并且还爆内存
 */
public class LC3337_TLE_MLE {
    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int[][] dp = new int[t + 1][26];
        Arrays.fill(dp[0], 1);
        int MOD = (int)1e9 + 7;
        for(int i = 1;i <= t;i++){
            for(int j = 0;j < 26;j++){
                for(int k = 1;k <= nums.get(j);k++){
                    dp[i][j] = (dp[i][j] + dp[i - 1][(j + k) % 26]) % MOD;
                }
            }
        }
        int ret = 0;
        for(char c : s.toCharArray()){
            ret = (ret + dp[t][c - 'a']) % MOD;
        }
        return ret;
    }
}
