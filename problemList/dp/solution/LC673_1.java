package problemList.dp.solution;

/**
dp求最大长度的同时, 求对应数量
注意: 这里我们需要再单独开一个数组cnt[], 其中cnt[i]表示以下标i结尾的最长递增子序列的数量
每次dp更新dp[i]的同时, 更新cnt[i]
然后在更新完dp[i]以及cnt[i]之后, 再计算maxLen以及对应maxCnt

特别的: 如果最长递增子序列长度为1, 那么此时数量就为nums.length

时间复杂度: O(n ^ 2)
 */
public class LC673_1 {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int[] cnt = new int[n];
        cnt[0] = 1;
        int maxLen = 1, maxCnt = 1;
        for(int i = 1;i < n;i++){
            dp[i] = 1;
            cnt[i] = 1;
            for(int j = 0;j < i;j++){
                if(nums[j] < nums[i]){
                    if(dp[j] + 1 > dp[i]){
                        dp[i] = dp[j] + 1;
                        cnt[i] = cnt[j];
                    }else if(dp[j] + 1 == dp[i]){
                        cnt[i] += cnt[j];
                    }
                }
            }
            if(dp[i] > maxLen){
                maxLen = dp[i];
                maxCnt = cnt[i];
            }else if(dp[i] == maxLen){
                maxCnt += cnt[i];
            }
        }
        return maxLen == 1 ? n : maxCnt;
    }
}
