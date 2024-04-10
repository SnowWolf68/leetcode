package problemList.dp.solution;

import java.util.*;

/**
本质还是一维LIS, 只不过对于i依赖的j, 此时的判断更复杂一点: 判断对于strs中的所有字符串str, 都有str[j] <= str[i]

时间复杂度: dp的时间复杂度是O(m ^ 2), 判断j是否合法的时间复杂度是O(n), 因此总的时间复杂度是O(m ^ 2 * n)
 */
public class LC960 {
    public int minDeletionSize(String[] strs) {
        int n = strs.length, m = strs[0].length();
        int[] dp = new int[m];
        for(int i = 0;i < m;i++){
            dp[i] = 1;
            for(int j = 0;j < i;j++){
                boolean check = true;
                for(int k = 0;k < n;k++){
                    if(strs[k].charAt(j) > strs[k].charAt(i)) {
                        check = false;
                        break;
                    }
                }
                if(check) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        return m - Arrays.stream(dp).max().getAsInt();
    }
}
