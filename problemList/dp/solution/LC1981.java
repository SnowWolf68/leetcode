package problemList.dp.solution;

import java.util.*;

/**
对于 "最小绝对差" 的处理: 首先dp出所有能够组成的和, 然后遍历这些和, 找出 "最小绝对差"
分组背包: 每一行看看做一组, 每一组恰好选一个
    dp[i][j] 表示考虑[0, i]这些行, 此时能不能组成和为j
        dp[i][j]: 枚举第i行中选择哪个元素, 假设选择下标为k的元素
            dp[i][j] |= dp[i - 1][j - mat[i][k]];
    初始化: i这个维度需要初始化, 添加辅助节点, 第一行辅助节点此时意味着当前没有任何元素, 那么此时和只能是0, 初始化dp[0][0] = true, 其余位置都是非法, 初始化为false
关于dp[i][j]中j的上界的确定: 在每行都选一个元素的前提下, 此时我们能够选到的最小值min即为每一行的最小值的和, 那么此时根据对称性, j的上界我们只需要考虑到2 * target - min(包括)即可
    特别的: 如果min >= target, 此时意味着所有选择情况下, 所选元素的和都 >= target, 那么此时显然我们取min得到的绝对差最小, 此时可以直接返回Math.abs(min - target)
 */
public class LC1981 {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length, n = mat[0].length;
        int min = 0;
        for(int i = 0;i < m;i++){
            min += Arrays.stream(mat[i]).min().getAsInt();
        }
        if(min >= target) return Math.abs(min - target);
        int upper = 2 * target - min;
        boolean[][] dp = new boolean[m + 1][upper + 1];
        dp[0][0] = true;
        for(int i = 1;i <= m;i++){
            for(int j = 0;j <= upper;j++){
                for(int k = 0;k < n;k++){
                    if(j - mat[i - 1][k] >= 0) dp[i][j] |= dp[i - 1][j - mat[i - 1][k]];
                }
            }
        }
        int ret = target;
        for(int i = 0;i <= upper;i++){
            if(dp[m][i]) ret = Math.min(ret, Math.abs(target - i));
        }
        return ret;
    }
}
