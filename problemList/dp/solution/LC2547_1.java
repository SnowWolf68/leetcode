package solution;

import java.util.*;

/**
数据范围1000, 本来划分dp的复杂度就是O(n ^ 2), 因此显然不能将计算trimmed(subArray).length放在dp中每次都O(n)地计算
考虑预处理出info[i][j]数组, 表示[i, j]这个subArray的特征

时间复杂度: (n ^ 2)
 */
public class LC2547_1 {
    public int minCost(int[] nums, int k) {
        int n = nums.length, INF = 0x3f3f3f3f;
        int[][] info = new int[n][n];
        Set<Integer> single = new HashSet<>();
        Set<Integer> multi = new HashSet<>();
        for(int i = 0;i < n;i++){
            single.add(nums[i]);
            for(int j = i;j < n;j++){
                if(j == i) info[i][j] = 0;
                else {
                    if(single.contains(nums[j])){
                        single.remove(nums[j]);
                        multi.add(nums[j]);
                    }else if(!multi.contains(nums[j])){
                        single.add(nums[j]);
                    }
                    info[i][j] = j - i + 1 - single.size();
                }
            }
            single.clear();
            multi.clear();
        }
        int[] dp = new int[n + 1];
        for(int i = 1;i <= n;i++){
            dp[i] = INF;
            for(int j = i;j > 0;j--){
                dp[i] = Math.min(dp[i], dp[j - 1] + info[j - 1][i - 1] + k);
            }
        }
        return dp[n];
    }
}
