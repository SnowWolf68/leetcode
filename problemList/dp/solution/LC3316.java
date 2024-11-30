package problemList.dp.solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
dp[i][j] 表示要能够在 source[0, i] 区间中得到一个等于 pattern[0, j] 的子序列, 此时的最多删除次数
dp[i][j]: 根据结尾元素的不同, 分为两种情况
    1. source[i] == pattern[j]: 此时再分两种情况:
        1. pattern[j]这个字符是由source[i]这个字符得来的, 此时显然不能删除i这个下标, 因此有
            dp[i][j] = dp[i - 1][j - 1]
        2. pattern[j]这个字符是由source[0, i - 1]区间中的某一个字符得来的
            此时是否能够删除i这个下标, 还需要看i这个下标是否在targetIndices中,
            1. 如果在: dp[i][j] = dp[i - 1][j] + 1;
                需要注意的是, 此时我们需要保证, source[0, i - 1]中要能够存在pattern[0, j]这个子序列才行, 因此我们规定 dp[i][j] = -1 表示不能找到pattern[0, j]这个子序列
                因此此时需要满足 dp[i - 1][j] != -1 才能转移
            2. 如果不在, 那么不能删除: dp[i][j] = dp[i - 1][j];
    2. source[i] != pattern[j]:
        此时只需要判断i是否在targetIndices中即可
        如果在, dp[i][j] = dp[i - 1][j] + 1;    同样要满足 dp[i - 1][j] != -1
        不在, dp[i][j] = dp[i - 1][j];
    需要注意的是, 在实际的代码中, 上面的有些情况可以合并在一起写, 具体可以看代码中的实现
初始化: i - 1, j - 1 都有可能越界, 因此添加一行一列辅助节点
    第一行: 此时意味着source中没有任何字符, 此时应该初始化dp[0][0] = 0, 其余位置都是-1
    第一列: 此时意味着pattern中没有任何字符, 此时显然只要是在targetIndices中的下标, 都可以删掉, 因此dp[0][0] = 0, 其余位置: dp[i][0] = [0, i - 1]区间的下标中, 出现在targetIndices中的数目
return dp[n - 1][m - 1];

需要注意的是, 每次遍历到dp[i][j]时, 需要首先让dp[i][j] = -1
 */
public class LC3316 {
    public int maxRemovals(String source, String pattern, int[] targetIndices) {
        int n = source.length(), m = pattern.length();
        // Set<Integer> hashSet = new HashSet<>();
        // for(int x : targetIndices) hashSet.add(x);
        int[] hash = new int[n];
        for(int x : targetIndices) hash[x] = 1;
        int[][] dp = new int[n + 1][m + 1];
        Arrays.fill(dp[0], -1);
        dp[0][0] = 0;
        int cnt = 0;
        for(int i = 0;i < n;i++){
            // if(hashSet.contains(i)) cnt++;
            if(hash[i] == 1) cnt++;
            dp[i + 1][0] = cnt;
        }
        for(int i = 1;i <= n;i++){
            for(int j = 1;j <= m;j++){
                dp[i][j] = -1;
                if(source.charAt(i - 1) == pattern.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                // if(hashSet.contains(i - 1) && dp[i - 1][j] != -1) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + 1);
                if(hash[i - 1] == 1 && dp[i - 1][j] != -1) dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + 1);
                else dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
            }
        }
        return dp[n][m];
    }
}
