package problemList.dp.solution;

import java.util.*;

/**
多重背包求方案数, 同余前缀和优化

和上一题不同的是, 这题的nums[i]有可能为0, 如果nums[i] == 0, 也就是list.get(i) == 0, 那么意味着同余前缀和中, 下标的差为0, 那么显然此时应用同余前缀和是不正确的
因此我们可以首先将0的情况特殊处理一下
 */
public class LC2902 {
    public int countSubMultisets(List<Integer> nums, int l, int r) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);
        int cnt = 1, MOD = (int)1e9 + 7;
        if(map.containsKey(0)) {
            cnt = (cnt + map.get(0)) % MOD;
            map.remove(0);
        }
        if(map.size() == 0) {
            if(l <= 0 && r >= 0) return cnt;
            else return 0;
        }
        List<Integer> list = new ArrayList<>(map.keySet());
        int n = list.size(), sum = 0;
        for(int x : nums) sum += x;
        int[][] dp = new int[n + 1][sum + 1];
        dp[0][0] = cnt;
        int[] s = new int[sum + 2];
        for(int j = 0;j <= sum;j++){
            if(j + 1 - list.get(0) >= 0) s[j + 1] = (s[j + 1 - list.get(0)] + dp[0][j]) % MOD;
            else s[j + 1] = dp[0][j];
        }
        for(int i = 1;i <= n;i++){
            int[] preS = s.clone();
            for(int j = 0;j <= sum;j++){
                if(j - map.get(list.get(i - 1)) * list.get(i - 1) >= 0) dp[i][j] = ((preS[j + 1] - preS[j + 1 - map.get(list.get(i - 1)) * list.get(i - 1)] + MOD) % MOD + dp[i - 1][j - map.get(list.get(i - 1)) * list.get(i - 1)]) % MOD;
                else dp[i][j] = preS[j + 1];
                if(i != n){
                    if(j + 1 - list.get(i) >= 0) s[j + 1] = (s[j + 1 - list.get(i)] + dp[i][j]) % MOD;
                    else s[j + 1] = dp[i][j];
                }
            }
        }
        int ret = 0;
        for(int i = l;i <= Math.min(r, sum);i++) ret = (ret + dp[n][i]) % MOD;
        return ret;
    }
}
