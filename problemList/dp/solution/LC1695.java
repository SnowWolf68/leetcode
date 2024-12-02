package problemList.dp.solution;

import java.util.ArrayList;
import java.util.List;

/**
看清题目要求, 注意这里只要求 i 之前/后的 k 个元素是 非递增/非递减 的, 而对 i 下标的元素没有要求
因此需要在LC2100的基础上稍加修改
 */
public class LC1695 {
    public List<Integer> goodIndices(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n], suffix = new int[n];
        for(int i = 1;i < n;i++) prefix[i] = nums[i] <= nums[i - 1] ? prefix[i - 1] + 1 : 0;
        for(int i = n - 2;i >= 0;i--) suffix[i] = nums[i] <= nums[i + 1] ? suffix[i + 1] + 1 : 0;
        List<Integer> ret = new ArrayList<>();
        for(int i = k;i < n - k;i++){
            if(prefix[i - 1] >= k - 1 && suffix[i + 1] >= k - 1) ret.add(i);    // 修改的地方
        }
        return ret;
    }
}
