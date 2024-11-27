package problemList.dp.solution;

/**
dp[i][j][p] 表示考虑 nums[0, i] 区间的下标, 操作1执行 j 次, 操作2执行 p 次, 此时所有元素的最小可能和
dp[i][j][p]: 针对i这个下标分析, 此时有4种可能
    1. 不执行任何操作: dp[i][j][p] = dp[i - 1][j][p] + nums[i];
    2. 只执行op1: dp[i][j][p] = dp[i - 1][j - 1][p] + (int)Math.ceil(nums[i] / 2.0);
    3. 只执行op2: 首先需要满足前提: nums[i] >= k, 如果满足, 那么有: dp[i][j][p] = dp[i - 1][j][p - 1] + nums[i] - k;
    4. 两种操作都执行: 此时有操作顺序的问题, 因此还需要再分两种情况
        1. 先 / 2 再 - k: 需要满足条件: (int)Math.ceil(nums[i] / 2.0) >= k
            如果满足, 那么: dp[i][j][p] = dp[i - 1][j - 1][p - 1] + (int)Math.ceil(nums[i] / 2.0) - k;
        2. 先 - k 再 / 2: 需要满足条件: nums[i] >= k
            如果满足, 那么: dp[i][j][p] = dp[i - 1][j - 1][p - 1] + (int)Math.ceil((nums[i] - k) / 2.0);
初始化: 这里 i - 1, j - 1, p - 1 都有可能越界, 但是 j - 1, p - 1 的越界可以在转移的时候手动判断, 因此这里只需要处理 i - 1 的越界即可
    添加辅助节点, 由于这里是三维的dp表, 因此就是添加一面的辅助节点 (i == 0的这一面)
    对于添加的这一面辅助节点, 此时意味着不需要考虑 nums 中的任何下标, 因此dp[0][j][p]的所有节点都初始化为0即可
return dp[n - 1][op1][op2];
 */
public class LC3366 {
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int n = nums.length;
        int[][][] dp = new int[n + 1][op1 + 1][op2 + 1];
        for(int i = 1;i <= n;i++){
            for(int j = 0;j <= op1;j++){
                for(int p = 0;p <= op2;p++){
                    dp[i][j][p] = dp[i - 1][j][p] + nums[i - 1];
                    if(j - 1 >= 0) dp[i][j][p] = Math.min(dp[i][j][p], dp[i - 1][j - 1][p] + (int)Math.ceil(nums[i - 1] / 2.0));
                    if(nums[i - 1] >= k && p - 1 >= 0) dp[i][j][p] = Math.min(dp[i][j][p], dp[i - 1][j][p - 1] + nums[i - 1] - k);
                    if((int)Math.ceil(nums[i - 1] / 2.0) >= k && j - 1 >= 0 && p - 1 >= 0) dp[i][j][p] = Math.min(dp[i][j][p], dp[i - 1][j - 1][p - 1] + (int)Math.ceil(nums[i - 1] / 2.0) - k);
                    if(nums[i - 1] >= k && j - 1 >= 0 && p - 1 >= 0) dp[i][j][p] = Math.min(dp[i][j][p], dp[i - 1][j - 1][p - 1] + (int)Math.ceil((nums[i - 1] - k) / 2.0));
                }
            }
        }
        return dp[n][op1][op2];
    }
}
