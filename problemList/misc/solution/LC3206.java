package problemList.misc.solution;

public class LC3206 {
    public int numberOfAlternatingGroups(int[] colors) {
        int n = colors.length, ret = 0, cnt = 1, k = 3;
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
