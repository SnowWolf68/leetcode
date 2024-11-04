package problemList.dp.solution.history;

import java.util.List;

/**
考虑优化掉dp[i][state]中的第一个维度, 但是还是会T
 */
public class LC638_TLE_3 {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int mask = 0, n = needs.size();
        for(int i = 0;i < needs.size();i++){
            mask |= needs.get(i) << (4 * i);
        }
        int[] dp = new int[mask + 1];
        for(int state = 1;state <= mask;state++){
            // 单独买一个
            for(int j = 0;j < n;j++){
                if(getStateNum(state, j) != 0){
                    dp[state] = dp[updateState(j, state, 1)] + price.get(j);
                }
            }
            // 使用大礼包
            for(List<Integer> spec : special){
                if(check2(state, spec)){
                    dp[state] = Math.min(dp[state], dp[updateState(state, spec)] + spec.get(n));
                }
            }
        }
        return dp[mask];
    }

    // return: 是否可以使用spec这个大礼包
    private boolean check2(int state, List<Integer> spec) {
        for(int i = 0;i < spec.size() - 1;i++){
            if(getStateNum(state, i) < spec.get(i)) return false;
        }
        return true;
    }

    private boolean check1(int i, int state, List<Integer> needs){
        for(int k = 0;k <= i;k++){
            if(getStateNum(state, k) > needs.get(k)) return false;
        }
        for(int k = i + 1;k < needs.size();k++){
            if(getStateNum(state, k) != 0) return false;
        }
        return true;
    }

    private String getBin(int num){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < 32;i++){
            if(((num >> i) & 1) == 0) sb.append(0);
            else sb.append(1);
        }
        return sb.reverse().toString();
    }

    private boolean check(int i, int state, List<Integer> spec) {
        for(int j = 0;j <= i;j++){
            if(spec.get(j) > getStateNum(state, j)) return false;
        }
        for(int j = i + 1;j < spec.size() - 1;j++) {
            if(spec.get(j) != 0) return false;
        }
        return true;
    }

    private int getStateNum(int state, int idx){
        int subMask1 = (1 << (4 * (idx + 1))) - 1;
        int subMask2 = (1 << (4 * idx)) - 1;
        return (state & (subMask1 & (~subMask2))) >> (4 * idx);
    }

    private int updateState(int i, int state, List<Integer> spec){
        for(int k = 0;k <= i;k++){
            int subMask1 = (1 << (4 * (k + 1))) - 1;
            int subMask2 = (1 << (4 * k)) - 1;
            int num = getStateNum(state, k) - spec.get(k);
            state &= ~(subMask1 & (~subMask2));
            state |= num << (4 * k);
        }
        return state;
    }

    // 使用spec更新state
    private int updateState(int state, List<Integer> spec){
        for(int i = 0;i < spec.size() - 1;i++){
            int num = getStateNum(state, i) - spec.get(i);
            int subMask1 = (1 << (4 * (i + 1))) - 1;
            int subMask2 = (1 << (4 * i)) - 1;
            state &= ~(subMask1 & (~subMask2));
            state |= num << (4 * i);
        }
        return state;
    }

    // needs[i] -= cnt;
    private int updateState(int i, int state, int cnt){
        int subMask1 = (1 << (4 * (i + 1))) - 1;
        int subMask2 = (1 << (4 * i)) - 1;
        int num = getStateNum(state, i) - cnt;
        state &= ~(subMask1 & (~subMask2));
        state |= num << (4 * i);
        return state;
    }
}
