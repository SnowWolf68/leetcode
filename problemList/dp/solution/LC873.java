package problemList.dp.solution;

import java.util.HashMap;
import java.util.Map;

/**
只能是使用最后两个元素来确定整个序列, 对于重复元素, 还是只需要考虑最后的那一个, 同时注意什么时候进行map.put()
 */
public class LC873 {
    public int lenLongestFibSubseq(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int[][] dp = new int[n][n];
        int ret = 2;
        for(int i = 0;i < n;i++){
            for(int j = i + 1;j < n;j++){
                dp[i][j] = 2;
                int idx = map.getOrDefault(nums[j] - nums[i], -1);
                if(idx != -1){
                    dp[i][j] = Math.max(dp[i][j], dp[idx][i] + 1);
                }
                ret = Math.max(ret, dp[i][j]);
            }
            map.put(nums[i], i);
        }
        return ret > 2 ? ret : 0;
    }
}
