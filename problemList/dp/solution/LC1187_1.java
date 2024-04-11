package problemList.dp.solution;

import java.util.*;

/**
从单纯dp的角度出发考虑

不妨还是尝试定义dp[i]: 将[0, i]区间的元素变成严格递增所需的最少操作数
那么考虑dp[i]如何计算得到: 由于这里需要将[0, i]区间的所有元素都满足 "严格递增" 的条件, 因此对于下标为i的位置, 只需要依赖下标为i - 1的位置
但是问题在于: 下标为i - 1的位置, 此时有可能"发生了替换", 也可能"没发生替换", 如果发生了替换, 那么此时有arr2.length种替换可能, 对于每一种替换可能, 我们都需要考虑
因此这里计算dp[i]的时间复杂度为O(arr2.length)
对于dp表而言, 此时我们需要记录 "下标为i时, 对应所有替换可能下的dp[i]值" , 因此我们可以把dp[i]变成一个map<k, v>, 其中k: 替换后的值, v: 替换后的最少操作数

那么此时对于dp[i], 从dp的角度来看, 我们可以根据 "选或不选" 来进行分类
    1. 不替换: 此时我们需要遍历下标为i - 1的元素的所有替换可能, 在符合要求的替换可能中, 取一个 "最少操作数" 的最小值, 即为当前下标为i位置不替换情况下, 最少的操作数
    2. 替换: 首先依赖的位置还是下标为i - 1的位置的dp值, 那么我们还是需要遍历下标为i - 1位置的所有替换可能, 得到所有可能的替换后的值val, 以及对应最少操作数ops
        那么此时考虑当前位置(下标为i)应该替换成哪个值: 这里我们可以贪心的认为, 替换成 arr2中大于val的最小值 是最优的
        因此这里我们可以首先对arr2升序排序, 然后二分查找第一个大于val的值, 在这种情况下, dp[i]的值就应该是ops + 1 (+1是因为当前位置也发生了一次替换)
    最后对于上面两种情况, 我们只需要取一个min即可
初始化: 由于这里我们需要依赖dp[i - 1]的值, 因此我们可以首先初始化dp[0]对应的这个map, 具体方法为: 
    由于这个位置是arr1的第一个位置, 因此不需要考虑前面元素是否符合要求, 因此此时只有两种情况: 1) 不替换, 操作数为0  2) 替换成arr2中的最小值, 操作数为1
    也就是dp[0]对应的map中只有两个键值对元素
return dp[arr1.length - 1]这个map中, 所有val的最小值

注: 实际上, dp[i]这个map集合, 其中的元素(<k, v>)的数量, 最多也就是2个(因为只有替换和不替换两种情况, 替换的情况下, 我们通过贪心找到了最优的一种替换策略)

时间复杂度: dp至多有2 * n个状态, 每一个状态的复杂度是O(logm), 因此dp的时间复杂度为O(n * logm), 考虑对arr2排序的时间O(m * logm), 那么总的时间复杂度为O(Math.max(m, n) * logm)
 */
public class LC1187_1 {
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length, INF = 0x3f3f3f3f;
        Arrays.sort(arr2);
        Map<Integer, Integer>[] dp = new Map[n];
        // 使用这种方式会报java.util.ConcurrentModificationException错误, 我也不知道为啥
        // Arrays.fill(dp, new HashMap<>());
        for(int i = 0;i < n;i++) dp[i] = new HashMap<>();
        dp[0].put(arr1[0], 0);
        // 这里必须加上判断, 因为有可能出现arr1[0] == arr2[0]的情况, 如果不判断就会发生val的覆盖, 这显然不行
        if(arr2[0] < arr1[0]) dp[0].put(arr2[0], 1);
        for(int i = 1;i < n;i++){
            for(int key : dp[i - 1].keySet()){
                int ops = dp[i - 1].get(key);
                // 1. 不替换
                if(key < arr1[i]){
                    dp[i].put(arr1[i], Math.min(dp[i].getOrDefault(arr1[i], INF), ops));
                }
                // 2. 替换
                // 二分查找arr2中第一个大于key的元素
                int l = 0, r = m - 1;
                while(l < r){
                    int mid = (l + r) >> 1;
                    if(arr2[mid] > key) r = mid;
                    else l = mid + 1;
                }
                // 加一个判断, 防止出现arr2中所有元素都比key小的情况
                if(arr2[l] > key){
                    dp[i].put(arr2[l], Math.min(dp[i].getOrDefault(arr2[l], INF), ops + 1));
                }
            }
        }
        int ret = INF;
        for(int key : dp[n - 1].keySet()){
            ret = Math.min(ret, dp[n - 1].get(key));
        }
        return ret == INF ? -1 : ret;
    }
}
