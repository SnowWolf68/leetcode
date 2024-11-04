package problemList.dp.solution.history;

import java.util.ArrayList;
import java.util.List;

/**
对于needs[i]来说, 此时有多种选择: 1) 不使用大礼包, 直接购买  2) 使用大礼包购买, 有可能有多种大礼包都可以
    考虑完need[i]之后, 问题转换为, 购买剩下的这些物品所需要的最低价格
因此存在重复子问题, 可以使用dp解决

需要注意的是, 这里状态表示比较麻烦, 因此这里使用dfs而不是递推(dp)
dfs(i, needs) 表示满足needs[0, i]区间的购买需求的最少花费
状态转移: 对于needs[i]来说, 此时有多种选择: 
    1. 不使用大礼包: dfs(i, needs) = dfs(i - 1, needs) + price[i] * needs[i];
    2. 使用大礼包: 由于有多种大礼包, 因此需要遍历所有大礼包, 假设当前遍历到的大礼包为special[j]:
        首先需要满足大礼包special[j]中包含的物品要都包含在nees[0, i]区间当中
        然后此时需要更新needs[]数组, 遍历special[j]中包含的所有物品 (假设遍历的下标为k), needs[k] -= special[j][k]
        状态转移这里首先需要判断needs[i]是否等于0, 如果等于0, 那么可以递归到dfs(i - 1), 否则还应该递归到dfs(i)
        dfs(i, needs) = needs[i] == 0 ? dfs(i - 1, needs) + special[j][n] : dfs(i, needs) + special[j][n];

        最后由于这里会对needs进行修改, 因此在每次dfs之后都要恢复现场
return dfs(n - 1, needs);
 */
public class LC638_TLE_1 {
    private List<Integer> price;
    private List<List<Integer>> special;
    public int shoppingOffers(List<Integer> _price, List<List<Integer>> _special, List<Integer> needs) {
        this.price = _price;
        this.special = _special;
        return dfs(needs.size() - 1, needs);
    }

    private int dfs(int i, List<Integer> needs){
        if(i < 0) return 0;
        int ret = dfs(i - 1, needs) + price.get(i) * needs.get(i);
        for(List<Integer> spec : special){
            boolean flag = true;
            for(int j = 0;j <= i;j++){
                if(spec.get(j) > needs.get(j)) {
                    flag = false;
                    break;
                }
            }
            for(int j = i + 1;j < needs.size();j++){
                if(spec.get(j) > 0) {
                    flag = false;
                    break;
                }
            }
            if(!flag) continue;
            List<Integer> newNeeds = new ArrayList<>(needs);
            for(int j = 0;j <= i;j++) newNeeds.set(j, newNeeds.get(j) - spec.get(j));
            ret = Math.min(ret, (newNeeds.get(i) == 0 ? dfs(i - 1, newNeeds) : dfs(i, newNeeds)) + spec.get(needs.size()));
        }
        return ret;
    }
}
