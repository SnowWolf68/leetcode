package problemList.fenwick.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
本题中关键在于如何快速计算greaterCount(arr, val)
greaterCount(arr, val) = cnt中严格大于val的元素数量
我们可以做一个值域到下标的转化
    构造一个新的数组 cnt, 其中cnt[i] = i这个元素在nums中出现的次数
    这样我们就将nums的值域映射到了cnt的下标, 好处是, 如果我们想要统计nums中大于val的元素有多少个, 
    可以转化为统计cnt[val:]区间的元素和
做了这个转化之后, 我们就将greaterCount(arr, val)转化为了计算cnt中一段区间的元素和的问题

而对于greaterCount(arr, val)来说, 我们每一次从nums中取一个元素, 判断将其插入到arr1还是arr2时
都需要计算一次greaterCount(arr, val), 如果用前缀和的话, 每次更新前缀和, 都需要花费O(n)的时间
因此我们可以使用树状数组对上述过程进行优化

除此之外, 由于nums的值域较大, 达到了1e9的范围, 而n比较小, 只有1e5, 因此我们可以将nums进行离散化, 用于缩小值域
 */
public class LC3072 {
    public int[] resultArray(int[] nums) {
        // 离散化
        Set<Integer> set = new HashSet<>();
        for(int x : nums) set.add(x);
        List<Integer> list = new ArrayList<>(set);
        // <离散化前, 离散化后>
        Map<Integer, Integer> map = new HashMap<>();
        Collections.sort(list);
        // 因为值域映射到下标, 而树状数组下标要从1开始, 因此值域映射到[1, list.size()]范围内
        for(int i = 0;i < list.size();i++) map.put(list.get(i), i + 1);
        BIT bit1 = new BIT(list.size());
        BIT bit2 = new BIT(list.size());
        List<Integer> list1 = new ArrayList<>(), list2 = new ArrayList<>();
        list1.add(nums[0]);
        list2.add(nums[1]);
        bit1.update(map.get(nums[0]), 1);
        bit2.update(map.get(nums[1]), 1);
        for(int i = 2;i < nums.length;i++){
            // 离散化后的值
            int after = map.get(nums[i]);
            int cnt1 = bit1.pre(bit1.len - 1) - bit1.pre(after);
            int cnt2 = bit2.pre(bit2.len - 1) - bit2.pre(after);
            if(cnt1 > cnt2){
                list1.add(nums[i]);
                bit1.update(after, 1);
            }else if(cnt1 < cnt2){
                list2.add(nums[i]);
                bit2.update(after, 1);
            }else if(cnt1 == cnt2){
                if(list1.size() > list2.size()){
                    list2.add(nums[i]);
                    bit2.update(after, 1);
                }else{
                    list1.add(nums[i]);
                    bit1.update(after, 1);
                }
            }
        }
        int[] ret = new int[list1.size() + list2.size()];
        int idx = 0;
        for(int x : list1) ret[idx++] = x;
        for(int x : list2) ret[idx++] = x;
        return ret;
    }
    
    public class BIT {
        private int[] tree;
        // len是tree数组的长度, 树状数组的下标范围是[1, len - 1], 即[1, n]
        public int len;
    
        // n: 下标范围[1, n]
        BIT(int n){
            this.len = n + 1;
            this.tree = new int[this.len];
        }
    
        private int pre(int i){
            int ret = 0;
            while(i > 0){
                ret += this.tree[i];
                i &= i - 1;
            }
            return ret;
        }
    
        private void update(int i, int x){
            while(i < this.tree.length){
                this.tree[i] += x;
                i += i & (-i);
            }
        }
    }
}
