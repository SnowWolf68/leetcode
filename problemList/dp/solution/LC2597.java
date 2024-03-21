package problemList.dp.solution;

import java.util.*;

/**
根据 mod k 的结果对nums中元素分组, 那么有以下性质: 
    1. 不同组的元素之间相差一定不等于k
    2. 同组的元素按照升序排列, 那么相差为k的元素只能是相邻元素
因此对于所有的组, 我们可以首先求出来每一组中, 符合要求的子集有多少种, 然后对于所有的组, 应用乘法原理即可

特别的: 对于空集的处理, 我们可以在前面所有的计算中, 都考虑空集的情况, 然后在最后的ret上 -1 即可
    因为最后的结果为空集的情况只有一种, 所以只在最后减去1即可
 */
public class LC2597 {
    public int beautifulSubsets(int[] nums, int k) {
        int[] cnt = new int[1010];
        for(int x : nums) cnt[x]++;
        List<List<int[]>> list = new ArrayList<>(k);
        for(int i = 0;i < k;i++) list.add(new ArrayList<>());
        // set的作用是避免相同的nums[i]被重复添加到list中, 这里用数组模拟哈希表
        int[] set = new int[1010];
        for(int x : nums){
            if(set[x] == 0){
                int rest = x % k;
                list.get(rest).add(new int[]{x, cnt[x]});
                set[x] = 1;
            }
        }
        // 遍历所有的组, 每组排序 + dp, 再加上其余组应用乘法原理后的结果
        int ret = 1;
        for(List<int[]> curList : list){
            if(curList.size() == 0) continue;
            Collections.sort(curList, (o1, o2) -> o1[0] - o2[0]);
            // 最后一个元素选/不选
            int f = (1 << curList.get(0)[1]) - 1, g = 1;
            for(int i = 1;i < curList.size();i++){
                if(curList.get(i)[0] - curList.get(i - 1)[0] == k){
                    // 当前元素和前一个元素不能同时选
                    int preG = g;
                    g += f;
                    f = preG * ((1 << curList.get(i)[1]) - 1);
                }else{
                    // 当前元素和前一个元素可以同时选
                    int preF = f;
                    f = (f + g) * ((1 << curList.get(i)[1]) - 1);
                    g += preF;
                }
            }
            // 这里f + g就是 "最后一个元素不选" + "最后一个元素选" 的所有情况, 即当前分组的所有符合要求的子集的数量
            ret *= f + g;
        }
        return ret - 1;
    }
}
