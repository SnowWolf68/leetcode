package problemList.dp.solution;

import java.util.*;

/**
加了一个限定条件的LIS, 数据范围1e5, 只能用O(n * logn)的方法
由于加的这个限制条件是针对 "值" 的限制, 因此没法用贪心 + 二分, 只能用值域dp + 树状数组/线段树
假设当前求dp[i], 其依赖的位置为j, 满足j < i, 那么分析一下j应该满足的条件: 
    nums[j] < nums[i] && nums[i] - nums[j] <= k
其实也就是nums[j]的范围应该在[nums[i] - k, nums[i])这个范围内, 对应树状数组/线段树, 这就是 "区间查询"
而由于这里树状数组/线段树维护的是 "最大值信息", 因此区间查询最大值, 树状数组无法完成, 只能用线段树

线段树需要完成的操作: 1. 维护的是 "最大值" 信息  2. 单点修改  3. 区间查询
 */
public class LC2407 {
    private int N;
    private int[] tree;
    // (i, l, r)表示当前节点下标以及管理范围, 更新x位置的值为val(如果val比x位置原来的值大的话)
    private void update(int i, int l, int r, int x, int val){
        if(l == r){
            tree[i] = Math.max(tree[i], val);
        }else{
            int mid = l + r >> 1;
            if(x <= mid) update(i * 2, l, mid, x, val);
            else update(i * 2 + 1, mid + 1, r, x, val);
            tree[i] = Math.max(tree[2 * i], tree[2 * i + 1]);
        }
    }
    // 查询[L, R]区间的最大值
    private int query(int i, int l, int r, int L, int R){
        if(L <= l && r <= R){
            return tree[i];
        }
        int ret = Integer.MIN_VALUE;
        int mid = l + r >> 1;
        if(mid >= L) ret = query(2 * i, l, mid, L, R);
        if(mid < R) ret = Math.max(ret, query(2 * i + 1, mid + 1, r, L, R));
        return ret;
    }
    public int lengthOfLIS(int[] nums, int k) {
        int max = Arrays.stream(nums).max().getAsInt();
        N = max;
        tree = new int[4 * N];
        for(int x : nums){
            // 单独判断x - 1 == 0的情况, 此时由于前面没有更小的元素, 因此直接把对应位置的值置为1即可
            if(x == 1){
                update(1, 1, N, 1, 1);
                continue;
            }
            // 查询[x - k, x - 1]区间的最大值
            // 如果x - k <= 0, 那么意味着需要查询x - k前面的所有值中的最大值, 因此可以让x - k对1取一个max
            int queryRet = query(1, 1, N, Math.max(1, x - k), x - 1);
            update(1, 1, N, x, queryRet + 1);
        }
        return query(1, 1, N, 1, N);
    }
}
