package problemList.misc.solution;

import java.util.Arrays;

/**
和LC45不同的是, LC45只能是向其中一侧覆盖, 而这题中的水龙头可以向两侧覆盖
因此这里的range并不是LC45中的 "跳跃距离" , 要想转化成LC45中的模型, 我们需要通过range计算出来 "跳跃距离" 数组

时间复杂度: O(n ^ 2)
 */
public class LC1326_1 {
    public int minTaps(int n, int[] ranges) {
        int[] nums = new int[n + 1];
        for(int i = 0;i <= n;i++){
            int left = i - ranges[i], right = i + ranges[i];
            for(int j = Math.max(left, 0);j <= Math.min(right, n);j++){
                nums[j] = Math.max(nums[j], right - j);
            }
        }
        int curR = 0, nxtR = 0, cnt = 0;
        for(int i = 0;i <= n;i++){
            nxtR = Math.max(nxtR, nums[i] + i);
            if(i == curR && i != n){
                if(curR == nxtR) return -1;
                curR = nxtR;
                cnt++;
            }
        }
        return cnt;
    }
}
