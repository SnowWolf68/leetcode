package problems.misc.solution;

import java.util.*;

/**
n == 30, 还是考虑折半枚举, 但是和前面两题不一样的是, 这题要求的是average平均值, 这个平均值和 元素和 以及 元素个数 都有关系
所以这里有一步很关键的转化: 由于选出来的两个子数组的平均值都相等, 那么可以得到: 选出来的两个数组各自的平均值和原数组的平均值相等, 都等于sum(nums[i]) / n
有了这一个转化, 下面的分析就很简单了, 既然选出来的两个子数组各自的平均值都知道了, 那么实际上就相当于只需要选出来一个数组, 并且要求其平均值是sum(nums[i]) / n即可

下面考虑如何选出来一个数组, 并且要求其平均值为sum(nums[i]) / n
考虑折半枚举的左右两部分, 由于这里的均值和 元素和 以及 元素数量 都有关系
因此, 如果保存左右两边所有可能的选择结果的list中只保存平均值average信息, 这显然不行, 因为无法将左边选出来的元素和右边选出来的元素合并
那么自然想到可以使用二元组[sum, cnt]来进行保存左边/右边每一种可能的选择下所选出来的元素信息

接下来就需要分析如何在 左半部分暴力枚举的所有可能 以及 右半部分暴力枚举的所有可能 中搜索, 是否存在均值 == sum(nums[i]) / n的情况
由于这里我们只需要判断 是否存在 一种选法能够满足要求, 因此我们可以直接遍历右半部分的每一对[sum, cnt], 同时查找左半部分是否有符合要求的[sum, cnt]即可
    1. 分析左半部分的[sum, cnt]需要满足的条件: 假设右半部分遍历的二元组为[sum2, cnt2], 左边对应的二元组为[sum1, cnt1], 总的元素和为sum, 那么如果要想满足要求, 必须有:
        (sum1 + sum2) / (cnt1 + cnt2) == sum / n
        通过这个式子我们不能得到sum1, cnt1是某个具体的值, 同时也无法得到sum1 / cnt1是某一个具体的值, 因此我们考虑遍历cnt1
        这里cnt1的范围是[0, n / 2 - 1]
        cnt1确定了, 就可以计算出sum1的值: sum1 = (sum / n) * (cnt1 + cnt2) - sum2
    2. 具体实现: 我们可以使用Set来存储每一个cnt1对应的sum1, 然后就可以在O(1)的时间内查找是否有符合要求的sum1
    3. 由于这里我们是对每一组[cnt2, sum2]来查找是否存在符合要求的[sum1, cnt1], 因此我们可以在 枚举右半部分的所有可能的同时 查找左半部分中是否有符合要求的选法, 而无需事先预处理左半部分以及右半部分
    其实就是将左半部分的所有可能按照cnt1进行分组, 每组中存储的是对应的所有可能的sum1
注意: 这里要求A, B数组不能为空, 所以需要去掉cnt1 + cnt2 == n 以及 cnt1 + cnt2 == 0这两种特殊情况
特别的: 考虑到精度问题, 对于sum1 = (sum / n) * (cnt1 + cnt2) - sum2这个式子, 不难发现, 如果(sum * (cnt1 + cnt2)) % n != 0, 那么算出来的sum1一定不是整数, 而sum1对应的是元素和, 其一定是整数
        因此如果(sum * (cnt1 + cnt2)) % n != 0, 那么直接return false;即可
时间复杂度: O(n * 2 ^ (n / 2))
 */
public class LC805 {
    public boolean splitArraySameAverage(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for(int x : nums) sum += x;
        // 左半部分范围: [0, n / 2 - 1], 一共n / 2个元素
        int mask1 = 1 << (n / 2), mask2 = 1 << (n - n / 2);
        // 其实就是按照cnt1分组
        List<Set<Integer>> left = new ArrayList<>();
        for(int i = 0;i <= n / 2;i++){
            left.add(new HashSet<>());
        }
        // 枚举左半部分的所有可能
        for(int i = 0;i < mask1;i++){
            int cnt = Integer.bitCount(i);
            int sum1 = 0;
            for(int j = 0;j < n / 2;j++){
                if(((i >> j) & 1) == 1){
                    sum1 += nums[j];
                }
            }
            left.get(cnt).add(sum1);
        }
        // 枚举右半部分的同时计算结果
        for(int i = 0;i < mask2;i++){
            int cnt2 = Integer.bitCount(i);
            int sum2 = 0;
            for(int j = 0;j < n - n / 2;j++){
                if(((i >> j) & 1) == 1){
                    sum2 += nums[j + n / 2];
                }
            }
            // 枚举cnt1
            for(int cnt1 = 0;cnt1 <= n / 2;cnt1++){
                if(cnt1 + cnt2 == n || cnt1 + cnt2 == 0) continue;
                if((sum * (cnt1 + cnt2)) % n != 0) continue;
                Set<Integer> set = left.get(cnt1);
                int sum1 = sum * (cnt1 + cnt2) / n - sum2;
                if(set.contains(sum1)) return true;
            }
        }
        return false;
    }
}
