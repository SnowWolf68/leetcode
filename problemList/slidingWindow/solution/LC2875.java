package problemList.slidingWindow.solution;

/**
首先我们需要将 无限  -->  有限
即求出 nums 的元素和 sum, 判断sum和target的关系
    1. 如果sum <= target, 那么说明直接在 nums ** 2 这个数组中进行寻找最短子数组即可
    2. 如果sum > target, 那么我们可以令  rest = target % sum, 即首先去掉 cnt = target / sum 这么多整个nums数组, 直接看剩下的这部分
        接下来问题转换为 在 nums ** 2 这个数组中, 寻找元素和等于rest的子数组, 并返回最短子数组的长度
 */
public class LC2875 {
    public int minSizeSubarray(int[] nums, int target) {
        int n = nums.length, sum = 0;
        for(int x : nums) sum += x;
        int rest = target % sum;
        int left = 0, ret = Integer.MAX_VALUE, curSum = 0;
        int[] arr = new int[2 * n];
        for(int i = 0;i < 2 * n;i++) arr[i] = nums[i % n];
        for(int i = 0;i < 2 * n;i++){
            curSum += arr[i];
            while(left <= i && curSum > rest){
                curSum -= arr[left];
                left++;
            }
            if(curSum == rest) ret = Math.min(ret, i - left + 1);
        }
        return ret == Integer.MAX_VALUE ? -1 : ret + n * (target / sum);
    }
}
