package problemList.misc.solution;

/**
环形数组的处理方式: 将原数组复制一份, 拼在原数组的后面
但是需要注意不能重复统计, 因此这里需要限制枚举的右端点的下标范围: 右端点不能超过 n + k - 2
 */
public class LC3208 {
    public int numberOfAlternatingGroups(int[] colors, int k) {
        int n = colors.length, ret = 0, cnt = 1;
        int[] nums = new int[2 * n];
        for(int i = 0;i < 2 * n;i++) nums[i] = colors[i % n];
        for(int i = 1;i < n + k - 1;i++){
            if(nums[i] != nums[i - 1]) cnt++;
            else cnt = 1;
            if(cnt >= k) ret++;
        }
        return ret;
    }
}
