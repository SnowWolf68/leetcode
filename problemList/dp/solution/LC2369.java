package problemList.dp.solution;

/**
dp[i] 表示考虑[0, i]区间的子数组, 此时是否存在有效划分
    dp[i]: 枚举最后一个划分的起始位置j, 如果最后一个划分能够满足要求, 那么有: 
        dp[i] |= dp[j - 1];
初始化: 这里j - 1有可能会越界, 因此需要考虑初始化第一个位置, 或者添加一个辅助节点
    这里我们采取添加一个辅助节点, 辅助节点的值初始化为true
return dp[n - 1];
 */
public class LC2369 {
    public boolean validPartition(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for(int i = 1;i <= n;i++){
            int j = i - 2;
            if(j - 1 >= 0 && check(nums, j - 1, i - 1)) dp[i] |= dp[j - 1];
            j = i - 1;
            if(j - 1 >= 0 && check(nums, j - 1, i - 1)) dp[i] |= dp[j - 1];
        }
        return dp[n];
    }

    private boolean check(int[] nums, int j, int i) {
        if(i - j + 1 == 3){
            if(nums[j] == nums[j + 1] && nums[j + 1] == nums[i]) return true;
            if(nums[j] + 1 == nums[j + 1] && nums[j + 1] + 1 == nums[i]) return true;
        }else{
            if(nums[j] == nums[i]) return true;
        }
        return false;
    }
}
