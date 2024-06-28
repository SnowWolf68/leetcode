package solution;

import java.util.*;

/**
首先解释一下如何计算某一个排列的 score
假设某一个排列为 perm[], 那么score = abs(perm[0] - nums[perm[1]]) + abs(perm[1] - nums[perm[2]]) + ... + abs(perm[n - 1] - nums[perm[0]]);
    对于题目中给的这个例子: nums = [1, 0, 2]
    假设当前我们找到的一个排列为 perm = [0, 1, 2]
    那么 score = abs(prem[0] - nums[prem[1]]) + abs(prem[1] - nums[perm[2]]) + abs(prem[2] - nums[prem[0]])
    如果仔细体会一下上面这个式子, 其实能够发现一个很巧妙, 也很重要的规律: 对于abs(prem[0] - nums[prem[1]])这个式子而言, 减去的nums[]中元素的下标prem[1]之和前一项prem[0]有关
    换句话说 score 式子中的每一项我们可以这样理解: 被减数是prem[]数组中的某一项(这里假设是prem[i]), 减数是prem[i]这一项 "后面" 那一项的元素值prem[i + 1]作为下标对应到nums[]中的元素值
        这里 "后面" 的含义为: 如果i == n - 1, 即i为perm数组中的最后一项, 那么此时下一项就是prem[]数组的第一项
    因此我们可以发现, 只要perm数组中元素之间的 "相对位置" 不变, 即perm数组可以 "任意旋转" , 此时score都不变   ----- 重要结论

    而由于题目要求最后得到的perm排列的字典树要最小, 并且通过上面的分析可以知道perm数组可以任意旋转, 因此显然让perm[0]旋转到0的时候, 此时的字典序最小
    --- 重要结论: 最终得到的perm中, perm[0]一定为0

接下来分析 状压dp 的部分
dp[state][j] 表示当前元素的选择情况为state, 并且当前选择的元素为j时, 此时最低的分数
dp[state][j]: 枚举上一个选择的元素k
    dp[state][j] = min(dp[state & (~(1 << j))][k] + abs(k - nums[j]));
    这里我们需要明确, 每次计算的abs(k - nums[j]))是属于哪一部分
        这里k表示前一个数, j表示当前的数, 即k = perm[i - 1], j = perm[i]
        并且这里我们规定i是从1开始, 因为perm[0]必须是0
        因此最后都计算完之后, 还缺少 abs(perm[n - 1] - nums[perm[0]]) 这一项没有计算
        所以需要在计算完i == n - 1这一项之后(对应代码中的 state == mask), 让dp[state][j] 加上 abs(perm[n - 1] - nums[perm[0]]) 这一项
初始化: 由于perm[0] = 0, 因此这里的state肯定是要从1开始往后转移, 因此可以首先初始化dp表的所有值为INF, 然后单独初始化dp[1][0] = 0

接下来分析一下如何构造结果
由于这里我们的状压dp的顺序是 从前往后 转移, 因此对于
 */
public class LC3149 {
    // O(n ^ 3 * 2 ^ n)
    public int[] findPermutation(int[] nums) {
        int n = nums.length, INF = 0x3f3f3f3f;
        int mask = (1 << n) - 1;
        int[][] dp = new int[mask + 1][n];
        for(int[] row : dp){
            Arrays.fill(row, INF);
        }
        dp[1][0] = 0;
        int[][][] info = new int[mask + 1][n][n];
        for(int state = 0;state <= mask;state++){
            for(int j = 0;j < n;j++){
                Arrays.fill(info[state][j], -1);
                // 第一位一定是0
                info[state][j][0] = 0;
            }
        }
        for(int state = 1;state <= mask; state++){
            for(int j = 0;j < n;j++){
                if(((state >> j) & 1) != 1) continue;
                int prev = (state & (~(1 << j)));
                for(int k = 0;k < n;k++){
                    if(k == j || ((prev >> k) & 1) != 1) continue;
                    int cur = dp[prev][k] + Math.abs(k - nums[j]);
                    int[] p = info[prev][k].clone();
                    int preCount = Integer.bitCount(prev);
                    p[preCount] = j;
                    if(cur < dp[state][j] || (cur == dp[state][j] && !check(p, info[state][j]))){
                        dp[state][j] = cur;
                        info[state][j] = p;
                    }
                }
                if(state == mask){
                    dp[state][j] += Math.abs(j - nums[0]);
                }
            }
        }
        int minScore = INF;
        List<int[]> retList = new ArrayList<>();
        for(int i = 0;i < n;i++){
            if(dp[mask][i] < minScore){
                minScore = dp[mask][i];
                retList.clear();
                retList.add(info[mask][i]);
            }else if(dp[mask][i] == minScore){
                retList.add(info[mask][i]);
            }
        }
        retList.sort((int[] a, int[] b) -> {
            if(check(a, b)) return 1;
            else return -1;
        });
        return retList.get(0);
    }

    /**
     * 比较 p 和 q 的字典序, 若p > q, 则返回 true, 反之返回 false
     * 若相等也返回true
     *  note: p, q中标记为-1的位置表示该位置未被使用
     * @param p
     * @param q
     * @return
     */
    private boolean check(int[] p, int[] q) {
        int idx = 0;
        while(idx < p.length && p[idx] != -1 && q[idx] != -1){
            if(p[idx] == -1){
                return false;
            }else if(q[idx] == -1){
                return true;
            }

            if(p[idx] < q[idx]){
                return false;
            }else if(p[idx] > q[idx]){
                return true;
            }else{
                idx++;
            }
        }
        // 若相等也返回true
        return true;
    }
}