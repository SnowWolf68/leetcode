package problemList.dp.solution;

/**
题目转化: 最少删除次数 -> 最多保留个数
假设山峰位置是i, 那么[0, i]区间就是LIS问题, [i, n - 1]区间就是倒序的LIS问题
前后缀分解, 分别求出每个下标i的前后的LIS长度/倒序LIS长度, 然后枚举山峰位置i, 求前后缀子序列长度的最大值即可

时间复杂度: O(n ^ 2)
 */
public class LC1671_1 {
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n], suffix = new int[n];
        prefix[0] = 1;
        for(int i = 1;i < n;i++){
            prefix[i] = 1;
            for(int j = 0;j < i;j++){
                if(nums[j] < nums[i]) prefix[i] = Math.max(prefix[i], prefix[j] + 1);
            }
        }
        suffix[n - 1] = 1;
        for(int i = n - 2;i >= 0;i--){
            suffix[i] = 1;
            for(int j = i + 1;j < n;j++){
                if(nums[j] < nums[i]) suffix[i] = Math.max(suffix[i], suffix[j] + 1);
            }
        }
        int ret = 0;
        for(int i = 1;i < n - 1;i++){
            // prefix[i] != 1 && suffix[i] != 1 的目的是为了保证两边必须有递增/递减的部分
            if(prefix[i] != 1 && suffix[i] != 1) ret = Math.max(ret, prefix[i] + suffix[i] - 1);
        }
        return n - ret;
    }
}
