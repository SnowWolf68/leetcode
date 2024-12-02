package problemList.dp.solution;

/**
枚举中间元素j, 看成前后缀分解来做, 那么只需要计算j前面, 是否有比nums[j]小的元素, 以及j后面, 是否有比nums[j]大的元素即可
如何一次遍历的同时, 计算j前面, 是否有比nums[j]小的元素?
    在遍历的同时, 维护遍历过的元素的最小值即可
计算j后面, 是否有比nums[j]小的元素 也是同理, 只需要在倒序遍历的同时, 维护一个遍历过的元素的最大值即可
 */
public class LC334 {
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        boolean[] prefix = new boolean[n], suffix = new boolean[n];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(int i = 0;i < n;i++){
            if(min < nums[i]) prefix[i] = true;
            else min = nums[i];
        }
        for(int i = n - 1;i >= 0;i--){
            if(max >nums[i]) suffix[i] = true;
            else max = nums[i];
        }
        for(int i = 0;i < n;i++){
            if(prefix[i] && suffix[i]) return true;
        }
        return false;
    }
}
