package problemList.unsorted;

import java.util.List;

/**
注意到本题的数据范围比较小, 1 <= n <= 6, 并且 0 <= price[i], needs[i] <= 10, 因此可以使用状态压缩将need压缩到一个int中
    needs[i] <= 10, 可以用4位表示, 而物品总数最多有6个, 因此最多需要4 * 6 = 24bit, 一个int正好可以放下
dp[i][state] 表示考虑needs下标在[0, i]区间的需求, 并且当前needs集合用state表示, 此时所需要的最低价格
    这里state使用低4 * n位分别表示当前还剩余的needs需求, 其中, 低位对应needs[]中的低下标范围
dp[i][state]: 对于needs[i]来说, 此时有多种选择: 
    1. 不使用大礼包
        int subMask1 = (1 << (4 * (i + 1))) - 1;
        int subMask2 = (1 << (4 * i)) - 1;
        dp[i][state] = dp[i - 1][state & (~(subMask1 & (~subMask2)))];
    2. 使用大礼包, 由于这里有很多种大礼包, 因此需要遍历所有的大礼包, 假设当前遍历到的大礼包下标为special[j]
        要想使用这个大礼包, 首先需要满足条件: 大礼包中包含的物品不能超过当前needs[]中的物品数量
        为了方便处理state这个二进制集合, 这里封装一个函数, 用于求state中下标为idx的物品需要的数量
        也就是求state中代表的needs[idx]
        private int getStateNum(int state, int idx){
            int subMask1 = (1 << (4 * (idx + 1))) - 1;
            int subMask2 = (1 << (4 * idx)) - 1;
            return state & (subMask1 & (~subMask2));
        }
        遍历当前special[j], 如果每一个special[j][idx]都满足 special[j][idx] <= getStateNum(state, idx), 那么说明当前大礼包可以选用
        在写状态转移方程之前, 还需要再封装一个函数, 用于实现state的更新
        // 更新[0, i]区间的needs[], needs[k] -= spec[k] 其中0 <= k <= i
        private int updateState(int i, int state, int[] spec){
            for(int k = 0;k <= i;k++){
                int subMask1 = (1 << (4 * (k + 1))) - 1;
                int subMask2 = (1 << (4 * k)) - 1;
                int num = getStateNum(state, k) - spec[k];
                state &= (subMask1 & (~subMask2));
                state &= num << (4 * k);
            }
            return state;
        }
        因此有状态转移方程: dp[i][state] = dp[getStateNum(state, i) - special[j][i] == 0 ? i - 1 : i][updateState(i, state, spec)];
初始化: 这里i - 1有可能越界, 因此可以添加一行辅助节点, 第一行此时意味着没有任何需求需要满足, 因此第一行全都初始化为0
return dp[n - 1][needs对应的mask];
 */
public class LC638 {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int mask = 0, n = needs.size();
        for(int i = 0;i < needs.size();i++){
            mask &= needs.get(i) << (4 * i);
        }
        int[][] dp = new int[n + 1][mask + 1];
        for(int i = 1;i <= n;i++){
            for(int state = 0;state <= mask;state++){
                int subMask1 = (1 << (4 * (i + 1))) - 1;
                int subMask2 = (1 << (4 * i)) - 1;
                dp[i][state] = dp[i - 1][state & (~(subMask1 & (~subMask2)))];
                for(List<Integer> spec : special){
                    if(check(i, state, spec)){
                        dp[i][state] = Math.min(dp[i][state], dp[getStateNum(state) - special[j][i] == 0 ? i - 1 : i][updateState(i, state, spec)]);
                    }
                }
            }
        }
    }

    private boolean check(int i, int state, List<Integer> spec) {
        for(int j = 0;j < spec.size();j++){
            if(spec.get(j) > getStateNum(state, j)) return false;
        }
        return true;
    }

    private int getStateNum(int state, int idx){
        int subMask1 = (1 << (4 * (idx + 1))) - 1;
        int subMask2 = (1 << (4 * idx)) - 1;
        return state & (subMask1 & (~subMask2));
    }

    private int updateState(int i, int state, List<Integer> spec){
        for(int k = 0;k <= i;k++){
            int subMask1 = (1 << (4 * (k + 1))) - 1;
            int subMask2 = (1 << (4 * k)) - 1;
            int num = getStateNum(state, k) - spec.get(k);
            state &= (subMask1 & (~subMask2));
            state &= num << (4 * k);
        }
        return state;
    }
}
