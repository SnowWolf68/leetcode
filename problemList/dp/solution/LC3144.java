package problemList.dp.solution;

/**
划分型DP
dp[i] 表示s在[0, i]区间的子串中, 最少能分割成多少个平衡子字符串
dp[i]: 枚举最后一个平衡字符串的起始位置, 假设最后一个平衡子字符串的起始位置为j
    首先需要判断s[j, i]区间的子串是否是平衡子字符串, 这里假设我们从后往前遍历j, 那么我们可以在遍历的同时, 记录当前子串中字符出现的次数
    这样就可以在遍历到每一个j的时候, 使用O(26)的时间判断出来当前s[j, i]区间的子串是否是平衡子字符串
    假设当前s[j, i]区间的子串是平衡子字符串, 那么有 dp[i] = dp[j - 1] + 1;

    对于所有可能的j, 这里需要取一个min
初始化: 添加一个辅助节点, 初始化dp[0] = 0;
return dp[n];
 */
public class LC3144 {
    public int minimumSubstringsInPartition(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = Integer.MAX_VALUE / 2;
            int[] cnt = new int[26];
            for(int j = i;j > 0;j--){
                cnt[s.charAt(j - 1) - 'a']++;
                if(check(cnt)) dp[i] = Math.min(dp[i], dp[j - 1] + 1);
            }
        }
        return dp[n];
    }

    private boolean check(int[] cnt) {
        int curCnt = -1;
        for(int i = 0;i < 26;i++){
            if(cnt[i] != 0){
                if(curCnt == -1) {
                    curCnt = cnt[i];
                }else {
                    if(curCnt != cnt[i]) return false;
                }
            }
        }
        return true;
    }
}
