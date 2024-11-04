package problemList.dp.solution;

import java.util.Arrays;
import java.util.List;

/**
状态压缩 + DFS   1011 ms

 */
public class LC638_DFS {
    private int n;
    private List<Integer> price;
    private List<List<Integer>> special;
    private List<Integer> needs;
    private int[] memo;
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        this.price = price;
        this.special = special;
        this.needs = needs;
        int mask = 0;
        n = needs.size();
        for(int i = 0;i < needs.size();i++){
            mask |= needs.get(i) << (4 * i);
        }
        memo = new int[mask + 1];
        Arrays.fill(memo, -1);
        return dfs(mask);
    }

    private int dfs(int state){
        if(state == 0) return 0;
        if(memo[state] != -1) return memo[state];
        int ret = Integer.MAX_VALUE;
        // 不使用大礼包
        for(int i = 0;i < n;i++){
            if(getStateNum(state, i) > 0) ret = Math.min(ret, dfs(updateState(i, state, 1)) + price.get(i));
        }
        // 使用大礼包
        for(List<Integer> spec : special){
            if(check(state, spec)){
                ret = Math.min(ret, dfs(updateState(state, spec)) + spec.get(spec.size() - 1));
            }
        }
        memo[state] = ret;
        return ret;
    }

    // return: 是否可以使用spec这个大礼包
    private boolean check(int state, List<Integer> spec) {
        for(int i = 0;i < spec.size() - 1;i++){
            if(getStateNum(state, i) < spec.get(i)) return false;
        }
        return true;
    }

    private int getStateNum(int state, int idx){
        int subMask1 = (1 << (4 * (idx + 1))) - 1;
        int subMask2 = (1 << (4 * idx)) - 1;
        return (state & (subMask1 & (~subMask2))) >> (4 * idx);
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
