package problems.misc.solution;

import java.util.*;

/**
两部分的差 -> 其中一部分取正, 另一部分取负
    因此我们只需要枚举nums中哪一部分取正, 哪一部分取负即可
数据范围nums.length可以到30, 显然2 ^ 30的时间复杂度还是太高了, 所以需要 折半枚举
注意题目要求两部分的长度还都需要是n, 即nums.length / 2

考虑枚举前半部分, 其中一部分取正, 一部分取负 的所有情况下的和, 由于这里要求所有取正的数量 和所有取负的数量都必须是n, 因此这里我们需要按照 取正的数量 分组
然后枚举后半部分, 过程同上

枚举完之后, 还是考虑排序 + 双指针
由于这里要求取正的数量和取负的数量都是n, 因此对于前半部分每一种 取正的分组, 从后半部分找出对应的一组, 对于这两组, 首先还是分别升序排序, 然后继续双指针查找

时间复杂度: O(n * 2 ^ (n / 2))
 */
public class LC2035 {
    public int minimumDifference(int[] nums) {
        int m = nums.length, n = m / 2;
        int mask = 1 << n;
        List<List<Integer>> left = new ArrayList<>(), right = new ArrayList<>();
        // 取正的数量的范围是[0, n]
        for(int i = 0;i <= n;i++) {
            left.add(new ArrayList<>());
            right.add(new ArrayList<>());
        }
        for(int i = 0;i < mask;i++){
            int cur = 0;
            for(int j = 0;j < n;j++){
                if(((i >> j) & 1) == 1){
                    cur += nums[j];
                }else{
                    cur -= nums[j];
                }
            }
            left.get(Integer.bitCount(i)).add(cur);
        }
        for(int i = 0;i < mask;i++){
            int cur = 0;
            for(int j = 0;j < n;j++){
                if(((i >> j) & 1) == 1){
                    cur += nums[j + n];
                }else{
                    cur -= nums[j + n];
                }
            }
            right.get(Integer.bitCount(i)).add(cur);
        }
        for(List<Integer> list : left) Collections.sort(list);
        for(List<Integer> list : right) Collections.sort(list);
        int ret = Integer.MAX_VALUE;
        // 枚举左边取正的数量, 由于取正的数量可能是0, 因此不需要单独讨论其中一边取正的数量为0的情况
        for(int i = 0;i <= n;i++){
            List<Integer> le = left.get(i), ri = right.get(n - i);
            int l = 0, r = ri.size() - 1;
            while(l < le.size() && r >= 0){
                int sum = le.get(l) + ri.get(r);
                ret = Math.min(ret, Math.abs(sum));
                if(sum > 0 && r >= 0) r--;
                else if(sum < 0 && l < le.size()) l++;
                else break;
            }
        }
        return ret;
    }
}