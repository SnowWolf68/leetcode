package problemList.misc.solution;

/**
枚举交替子数组的右端点
注意到: 如果 nums[i + 1] != nums[i], 那么nums[i + 1]可以拼接在nums[i]可以形成的交替子数组后面, 形成更长的交替子数组
因此:
    假设以nums[i]结尾的交替子数组个数为cnt, 那么
    1. nums[i + 1] != nums[i]: cnt++;
    2. nums[i + 1] == nums[i]: cnt = 1;
 */
public class LC3101 {
    public long countAlternatingSubarrays(int[] nums) {
        int n = nums.length;
        long ret = 1, cnt = 1;
        for(int i = 1;i < n;i++){
            if(nums[i] != nums[i - 1]) cnt++;
            else cnt = 1;
            ret += cnt;
        }
        return ret;
    }
}
