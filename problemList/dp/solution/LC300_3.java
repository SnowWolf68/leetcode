package problemList.dp.solution;

import java.util.*;;

/**
O(n ^ 2) 的基于值域的dp

dp[i] 表示以元素i结尾的子序列中, 最长递增子序列的长度
    dp[i] = max(dp[j] + 1), 其中, j < i
return max(dp[i]);
 */
public class LC300_3 {
    public int lengthOfLIS(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int x : nums) set.add(x);
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        // <原始值, 离散化后的值>
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0;i < list.size();i++){
            map.put(list.get(i), i + 1);
        }
        int[] dp = new int[list.size() + 1];
        for(int num : nums){
            int x = map.get(num);
            dp[x] = 1;
            for(int i = 0;i < x;i++){
                dp[x] = Math.max(dp[x], dp[i] + 1);
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
