package problemList.bit.solotion;

import java.util.HashSet;
import java.util.Set;

/**
灵神的位运算做法 -- 位运算构造法

构造的结果: ans = nums[i] ^ nums[j], 即ans由nums中的两个元素nums[i], nums[j]异或得到, 并且要求构造出来的ans是最大的

如何构造? 
既然要求构造出最大的ans, 那么就从ans的最高位开始构造
    需要注意的是, ans的二进制长度是有限制的, 不会超过nums[i]的最大二进制长度
        假设nums[i]的最大二进制长度为len, 那么ans的二进制长度最长就是len, 假设ans的高位到低位分别为len - 1, len - 2, ..., k, ... 1, 0
            其中, 使用k表示ans的下标为k的这一位
如果最高位可以为1, 即存在nums[i], nums[j], 使得nums[i] ^ nums[j]的下标为len - 1位为1, 那么ans的下标为len - 1的这位就是1
反之, ans下标为len - 1的这一位就是0

需要注意的是, 由于构造出来的ans还必须能够满足: ans = nums[i] ^ nums[j], 即ans必须能够由nums中的两个元素异或得到
因此在构造ans的下标为k的这一位的同时, 选取的nums[i], nums[j]必须能够满足nums[i] ^ nums[j]异或结果的[len - 1, k + 1]这些位(为了表述直观, 这里len - 1 >= k + 1, 下同), 
    都要和ans中[len - 1, k + 1]这些位相同

构造完最高位, 按照同样的规则构造次高位, 然后依次构造下去

按照上述规则构造出来的ans即是最大的ans

具体实现: 上述策略具体实现起来也是比较麻烦, 这里提供一种实现策略
假设当前构造到了ans下标为k的这一位 (意味着ans[len - 1, k + 1]这些位(为了表述直观, 这里len - 1 >= k + 1, 下同)都已经构造完成)
    首先定义newAns = ans | (1 << k), 即newAns等于ans的下标为k的这位置1
    然后将此时nums中所有的nums[i]的低k位置0, 即nums[i] = nums[i] & mask, 这里的mask满足 [k, 0] 这些位(为了表述直观, 这里k >= 0, 下同) 都为0, 并且[len - 1, k + 1]这些位都为1
        这个mask可以在k的遍历过程中每次都让mask = mask | (1 << k)得到
    此时就可以在nums中寻找nums[i] ^ nums[j] == newAns,
        如果存在nums[i] ^ nums[j] == newAns, 那么说明ans下标为k的这一位可以是1, 即ans = newAns
        如果不存在, 那么说明ans下标为k的这位不能是1, 因此ans不变
    
    需要注意的是, 这里nums.length的范围到了1e5, 所以如果使用两层循环i, j遍历显然不行, 这里采用了类似 两数之和 中的哈希表做法
    使用哈希表优化到O(n)的思想为: 
        我们要找的i, j需要满足 nums[i] ^ nums[j] == newAns, 两边同时异或nums[j], 由于nums[j] ^ nums[j] == 0, 那么有nums[i] == nums[j] ^ newAns
        通过这个式子可以看到, 寻找i, j, 可以转换为在nums中寻找是否存在一个nums[i], 使得nums[i]等于nums[j] ^ newAns, 即nums[i] == nums[j] ^ newAns
        因此我们遍历i, 每次遍历到一个nums[i], 都将nums[i]添加到HashSet中, 同时在这个HashSet中寻找是否有nums[i] ^ newAns, 
        如果有, 说明此时找到了一组i, j, 满足nums[i] ^ nums[j] == newAns
            需要注意的是, 这里我们只需要在i已经遍历过的这些元素中寻找nums[i] ^ newAns, 因为如果某个符合条件的nums[j]在i的右边, 即j > i, 那么虽然此时并不能找到这一组i, j
            但是此时会将nums[i]添加到HashSet中, 当继续向后遍历到j的时候, 就会发现哈希表中存在一个元素, 等于nums[i] ^ newAns (这里i指的是此时的i, 相当于原来的j)
                这里有点绕, 好好理解一下
            因此这种方法并不会漏掉某些情况
    
    还需要注意的一点是, 通过之前的分析我们知道, 在每次构造到下标为k的这一位时, 都需要首先让nums中的所有元素和mask相&,
    而恰好在寻找nums[i], nums[j]的过程中, HashSet中的元素也是随着i的遍历逐步添加进去的, 
    因此当我们寻找i, j的时候, 对于当前遍历到的nums[i], 可以首先定义x = nums[i] & mask, 然后在哈希表中寻找是否存在x ^ newAns这个元素, 
    如果有, 说明找到了符合要求的i, j, 即ans = newAns, 此时需要跳出i的循环, 继续构造k + 1这一位即可
    反之, 说明还没有找到, 因此将x添加到哈希表中, 继续遍历nums[i + 1]

    还需要注意的是, 如果一直使用同一个HashSet, 那么不要忘记在每次构造到下标为k的这一位时, 首先将当前的HashSet clear() 一下, 
    原因也很显然, 因为每次构造下标为k的这一位时, 都会重新往HashSet中添加数据, 并且对新添加的这些数据进行处理, 因此需要每次都清楚旧的数据
        
 */
public class LC421 {
    public int findMaximumXOR(int[] nums) {
        int max = 0;
        for(int x : nums) max = Math.max(max, x);
        // Integer.numberOfLeadingZeros()   包括符号位的0, 因此这里是用32 - 前导0 = 剩余的总位数
        int ans = 0, mask = 0, len = 32 - Integer.numberOfLeadingZeros(max);
        Set<Integer> set = new HashSet<>();
        for(int k = len - 1;k >= 0;k--) {
            mask |= 1 << k;
            set.clear();
            int newAns = ans | (1 << k);
            for(int i = 0;i < nums.length;i++){
                int x = nums[i] & mask;
                if(set.contains(x ^ newAns)){
                    ans = newAns;
                    break;
                }
                set.add(x);
            }
        }
        return ans;
    }
}