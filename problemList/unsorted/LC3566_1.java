package problemList.unsorted;

/**
nums的长度只有12, 可以暴力枚举每个元素分别分给两个集合的情况, 最多有2 ^ 12种情况
 */
public class LC3566_1 {
    private boolean find = false;
    public boolean checkEqualPartitions(int[] nums, long target) {
        dfs(0, 1, 1, nums, target);
        return find;
    }
    
    private void dfs(int i, long leftRet, long rightRet, int[] nums, long target){
        int n = nums.length;
        if(i == n){
            if(leftRet == rightRet && leftRet == target){
                find = true;
            }
            return;
        }
        dfs(i + 1, leftRet * nums[i], rightRet, nums, target);
        dfs(i + 1, leftRet, rightRet * nums[i], nums, target);
    }
}
