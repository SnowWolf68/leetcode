package problemList.slidingWIndows.solution;

/**
转化: 
    1. 删除左右两边 -> 保留中间
    2. 删除的最少操作数 -> 中间保留的最多元素个数
题意: 和为sum - x的最长子数组长度
 */
public class LC1658 {
    public int minOperations(int[] nums, int x) {
        int n = nums.length, sum = 0, ret = 0, left = 0, curSum = 0;
        for(int num : nums) sum += num;
        for(int i = 0;i < n;i++){
            curSum += nums[i];
            while(left < n && curSum > sum - x){
                curSum -= nums[left];
                left++;
            }
            // 需要判断当前窗口是否合法
            if(curSum == sum - x) ret = Math.max(ret, i - left + 1);
        }
        return ret == 0 ? (sum == x ? n : -1) : n - ret;
    }
}
