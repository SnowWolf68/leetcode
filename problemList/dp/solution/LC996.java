package problemList.dp.solution;

import java.util.HashMap;
import java.util.Map;

/**
状压DP 相邻相关 的板子题
dp[state][i] 表示当前选择元素下标集合为state, 并且当前选择的最后一个下标为i, 此时所有不同的排列数量
dp[state][i]: 枚举前一个选择的下标, 假设为j
    如果满足条件: check(nums[i] + nums[j]) == true // check(nums) 表示判断nums是不是完全平方数, 如果是, 返回true, 反之返回false
    那么dp[state][i] += dp[state & (~(1 << i))][j];
初始化: 初始化所有 bitCount(state) == 1的这些行, 这些行中, 初始化dp[state][i] = 1 (满足 ((state >> i) & 1) == 1 ) 其余位置都初始化为0
return sum(dp[mask][i])

需要注意的是, 这里nums数组中可能会存在重复元素, 因此如果直接按照上面的方法来算, 那么最后的结果中会包含重复的排列
如果想直接在计算的同时去掉重复的排列会比较难, 因此这里可以首先按照 有重复排列 的方式来算, 然后再分析一下重复的排列一共有多少种情况, 最后从总的排列数量中减去重复的排列的数量即可

考虑一下重复的排列是如何产生的
假如nums中存在重复元素, 那么对于其中某一个出现了多次的元素, 假设为x, 并且假设x这个元素出现了cnt次, 那么在上面的计算过程中, 对于x的某一种排列, 其实是算成了 cnt! 次
也就是说, 对于nums中的某一个出现了多次的元素, 假设其出现次数为cnt, 那么最终的结果会被多算 cnt! 次
假设通过上面的方式计算出来的结果为ret (这个ret包含重复的排列), 并且nums中只有一个元素出现了多次, 假设为cnt次, 那么去重后的排列数量应该为 ret / (cnt!)

然后再考虑一下如果nums中有不止一个重复元素的情况, 此时假设这两个元素及其出现次数分别为 x1, x2, cnt1, cnt2, 那么对于x1, x2的某一种排列, 上面的方法多算了 cnt1! * cnt2! 次

基于上面的分析就可以完成去重, 具体实现方式见下面代码
 */
public class LC996 {
    public int numSquarefulPerms(int[] nums) {
        int n = nums.length, mask = 1 << n;
        Map<Integer, Integer> repeatMap = new HashMap<>();
        for(int x : nums){
            if(repeatMap.containsKey(x)){
                repeatMap.put(x, repeatMap.get(x) + 1);
            }else{
                repeatMap.put(x, 1);
            }
        }
        int repeatCnt = calc(repeatMap);
        int[][] dp = new int[mask][n];
        for(int i = 0;i < n;i++){
            int state = 1 << i;
            dp[state][i] = 1;
        }
        int ret = 0;
        for(int state = 2;state < mask;state++){
            int bitCount = Integer.bitCount(state);
            if(bitCount <= 1) continue;
            for(int i = 0;i < n;i++){
                if(((state >> i) & 1) == 0) continue;
                for(int j = 0;j < n;j++){
                    if(j == i || ((state >> i) & 1) == 0) continue;
                    if(check(nums[i] + nums[j])) dp[state][i] += dp[state & (~(1 << i))][j];
                }
                if(state == mask - 1){
                    ret += dp[state][i];
                }
            }
        }
        return repeatCnt != 0 ? ret / repeatCnt : ret;
    }

    private int calc(Map<Integer, Integer> repeatMap) {
        int ret = 1;
        for(int cnt : repeatMap.values()){
            if(cnt != 1){
                ret *= calc2(cnt);
            }
        }
        return ret == 1 ? 0 : ret;
    }

    private int calc2(int num) {
        if(num == 1) return 1;
        return num * (calc2(num - 1));
    }

    private boolean check(int i) {
        int sqrt = (int)Math.sqrt(i);
        return sqrt * sqrt == i;
    }
}
