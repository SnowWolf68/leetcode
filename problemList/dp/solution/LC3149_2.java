package problemList.dp.solution;

import java.util.*;

/**
第一种做法用的是 从前往后 的状压dp, 这样就会导致最后构造具体结果比较麻烦, 需要在dp的同时进行构造, 并且需要按照字典序排序
    这里 从前往后 也可以理解为state的增长方向为从小到大
在 从前往后 的递推中, 对于某一个dp[state][j], 我们只能知道其取到最优值时的前一个元素是什么, 而不能够知道其后面的元素是什么
    因此无法根据dp表正序构造出来最优解, 需要在dp的同时记录最优解, 这个记录过程涉及到两个perm字典序的比较, 并且也会额外耗费O(n)的时间
所以考虑能不能换一种方式, 使得可以直接从dp表中正序构造出最优解?

这种状态表示的理解关键在于: 当前需要在 "剩下的元素" 中选, 而不是 "当前已经选了xxx"

dp[state][j] 表示之前选择情况为state, 并且前一个元素选的是j, 此时从剩下的元素中选, 此时的最小分数
dp[state][j]: 枚举当前选的元素, 假设为k
    dp[state][j] = min(dp[state | (1 << k)][k] + abs(j - nums[k]));
    同样, 每次计算的都是相邻两项的关系, 所以也需要额外加上 abs(perm[n - 1] - nums[perm[0]]) 这一项, 具体在什么时候加, 下面会讨论
填表顺序: 这里state的转移顺序是从大到小, 因此填表顺序应该是从下往上, 每一行中填表顺序无所谓
初始化: 这里我们是让state从大到小去递推, 因此需要初始化的是dp[mask][]这一行的所有元素, 此时可以直接把这些位置初始化为之前在dp过程中没有加上的这一项 abs(perm[n - 1] - nums[perm[0]])
    即 dp[mask][j] = Math.abs(j - nums[0]);
return: min(dp[1 << j][j])

然后考虑构造最优解的过程
这里由于dp是从后往前转移, 因此计算dp[state][j]的同时, 就能够确定dp[state][j]取到最优解时, 下一个元素一定是k
因此可以添加一个数组info[][], 其中info[state][j] = k, 即记录了dp[state][j]取到最优解时的下一个元素
这样就可以根据info[][]这个数组来从前往后构造最优解

并且字典序最小也很容易保证, 因为这里info[state][j]数组记录的是 当前元素 (即 j 的下一个元素) , 所以只需要在更新info[state][j]数组的时候, 
    从小到大遍历当前元素(j的下一个元素)k, 并且当遇到更低的分数时, 只更新第一个k(字典序最小的k) 即可

这种方式在计算每个状态时, 只需要O(n)的时间遍历所有k, 因此总的时间复杂度为O(n ^ 2 * 2 ^ n)
 */
public class LC3149_2 {
    public int[] findPermutation(int[] nums) {
        int n = nums.length, mask = (1 << n) - 1, INF = 0x3f3f3f3f;
        int[][] dp = new int[mask + 1][n], info = new int[mask + 1][n];
        for(int[] row : dp){
            Arrays.fill(row, INF);
        }
        for(int j = 1;j < n;j++){
            dp[mask][j] = Math.abs(j - nums[0]);
        }
        for(int[] row : info){
            Arrays.fill(row, -1);
        }
        for(int state = mask - 1;state >= 0;state--){
            for(int j = 0;j < n;j++){
                if(((state >> j) & 1) == 0) continue;
                for(int k = 0;k < n;k++){
                    if(((state >> k) & 1) == 1) continue;
                    if(dp[state | (1 << k)][k] + Math.abs(j - nums[k]) < dp[state][j]){
                        dp[state][j] = dp[state | (1 << k)][k] + Math.abs(j - nums[k]);
                        info[state][j] = k;
                    }
                }
            }
        }
        int[] ret = new int[n];
        int state = 1, j = 0, idx = 1;
        while(info[state][j] != -1){
            j = info[state][j];
            state = state | (1 << j);
            ret[idx++] = j;
        }
        return ret;
    }
}
