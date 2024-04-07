package problemList.dp.solution;

import java.util.*;

/**
核心还是基于值域dp + 树状数组
树状数组同时维护长度以及数量信息
在普通LIS问题的树状数组解法中, 我们使用了基于值域的dp, 使得能够应用树状数组优化
这里我们不仅需要求最长的长度, 还需要求最长的长度对应的子序列数量, 因此考虑在树状数组中同时维护 长度 以及 数量 信息
具体的: 由于LIS的需要, 树状数组总的逻辑还是 维护最大值, 只是树状数组的第二个维度需要维护 数量 信息, 那么此时关键就是考虑如何更新第二个维度的信息(即数量信息)

不难得到: 对于当前计算出来的某一个dp[i], 其更新对应的cnt[i]的逻辑为: 对于dp[i]所依赖的所有dp[j](即满足dp[i] = dp[j] + 1)的这些j, 此时cnt[i] += cnt[j]
那么对应树状数组的add(或者update)以及query方法中, 就是: 
首先这里显然需要更新两个信息, 分别是len, cnt, 那么下面两个方法中就都需要在原来len的基础上, 加上一个cnt参数
    1. add(x, len, cnt): 此时需要从x往后跳, 每跳到一个节点, 那么此时需要更新i节点的leni, cnti两个信息: 
        leni: 此时还需要按照LIS的规则, leni就取max(leni, len)
        cnti: 此时需要判断len和leni的关系: 
            注意: 这里的leni应该是原始的leni, 而不是经过上面更新之后的leni
            如果len == leni: 那么cnti += cnt
            如果len > leni: 那么cnti = cnt
    2. query(x): 这里我们需要从x向前跳, 那么此时也是需要维护两个信息len, cnt
        对于每次跳到的节点i, 此时需要使用当前节点i的leni, cnti来更新len, cnt
            1. len: 此时还需要按照LIS的规则, len取max(len, leni)
            2. cnt: 同样需要判断len和leni的关系
                如果len == leni: cnt += cnti
                如果len < leni: cnt = cnti
        注: query(x)的返回值显然需要是两个值: len, cnt, 这可以封装成一个int[2]来返回

时间复杂度: O(n * logn)
 */
public class LC673_2 {
    private int[][] tree;
    private int N;
    private int lowbit(int x){
        return x & (-x);
    }
    private void add(int x, int len, int cnt){
        for(int i = x;i <= N;i += lowbit(i)){
            if(len == tree[i][0]) tree[i][1] += cnt;
            else if(len > tree[i][0]) tree[i][1] = cnt;
            tree[i][0] = Math.max(tree[i][0], len);
        }
    }
    private int[] query(int x){
        int[] ret = new int[2];
        for(int i = x;i > 0;i -= lowbit(i)){
            if(ret[0] == tree[i][0]) ret[1] += tree[i][1];
            else if(ret[0] < tree[i][0]) ret[1] = tree[i][1];
            ret[0] = Math.max(ret[0], tree[i][0]);
        }
        return ret;
    }
    public int findNumberOfLIS(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int x : nums) set.add(x);
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        // <原始值, 离散化后的值>
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0;i < list.size();i++){
            map.put(list.get(i), i + 1);
        }
        N = list.size();
        tree = new int[N + 1][2];
        for(int num : nums){
            // 离散化后的值
            int x = map.get(num);
            int[] queryRet = query(x - 1);
            // 如果queryRet[1] == 0, 那么此时意味着前面没有任何满足要求的序列, 那么此时num这个元素就单独作为一个序列, 那么此时序列的个数为1
            // 因此这里queryRet[1]需要对1取一个max
            add(x, queryRet[0] + 1, Math.max(1, queryRet[1]));
        }
        return query(N)[1];
    }
}
