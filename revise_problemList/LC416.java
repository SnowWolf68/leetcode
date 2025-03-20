package revise_problemList;

public class LC416 {
    public boolean canPartition(int[] nums) {
        int n = nums.length, sum = 0;
        for(int num : nums) sum += num;
        if(sum % 2 != 0) return false;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for(int num : nums){
            for(int j = sum;j >= 1;j--){
                if(j - num >= 0) dp[j] |= dp[j - num];
            }
        }
        return dp[sum / 2];
    }
}
