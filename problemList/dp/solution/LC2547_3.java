package solution;

import java.util.*;

/**
优化:
    状态转移方程: g[i] = k + min(g[j - 1] - unique(j, i)), 其中1 <= j <= i  (这里j从1开始是因为我们添加了辅助节点, 并且恰好线段树也要求下标从1开始)
    显然查询区间最小值可以使用线段树来优化
    在这里显然线段树需要维护的是g[j - 1] - unique(j, i)的信息, 也就是说, 对于线段树中的每一个下标j, 其对应的值为g[j - 1] - unique(j, i)
    其中, g[j - 1]之和j有关, 但是unique(j, i)不仅和j有关, 还和i有关
    因此对于每一个i, 我们在查询线段树之前, 都需要首先更新线段树中的所有j的unique(j, i)的值, 然后再进行查询

    更新unique(j, i)的策略如下: 假设当前遍历到nums[i], 并且假设last[nums[i]]表示nums[i]上一次出现的下标, last2[nums[i]]表示nums[i]上上一次出现的下标
    那么对于[last[nums[i]] + 1, i]区间的元素来说, 其unique值需要 + 1
    对于[last2[nums[i]] + 1, last[nums[i]]]区间的元素来说, 其unique值需要 - 1
    对于[0, last2[nums[i]]]区间的元素来说, 其unique值不变
    需要注意的是: 这里我们的 + 1, - 1操作, 其对应的是 "整个区间的unique值" , 也就是说, 我们是将整个区间的unique值都 + 1, - 1, 而不是对区间中的每一个元素进行 + 1, - 1
    因此在线段树的实现中, update方法中, 应该是 + v, 而不是 (+ v * 区间长度), 同样的, 在pushdown方法中, 也不需要多传入一个len参数, 只需要直接 += v即可, 而不是 += v * (len - len / 2), 以及 += v * (len / 2)

    并且由于这里的j可以取到i, 因此计算g[i]的时候, 需要首先计算并更新线段树中j == i位置的值, 即g[j - 1] - unique(j, i)的值, 这里由于j == i, 因此实际上就是g[i - 1] - 1
    具体实现中, 我们可以使用一个变量ans记录每一次的g[i], 并且在下一个i的时候, 使用ans - 1更新当前的线段树中j == i位置的值

    线段树维护区间最小, 更新的时候对区间 整体 进行 加 操作

    这题的数据范围只有1000, 所以直接开4 * N的空间的线段树即可

    注意:
        1. last[nums[i - 1]], last2[nums[i - 1]], 都是相对于线段树中的下标来说的, 即相对于[1, n]区间的下标来说的, 因为计算unique(j, i)的时候, 其中的j, i都是指的线段树中的下标
        2. 特别的, 如果last[nums[i - 1]]不存在, 显然last2[nums[i - 1]]也不存在, 那么last[nums[i - 1]]默认就是0, 那么last[nums[i - 1]] + 1就是1, 意味着将[1, i]区间的unique整体 + 1, 同时不需要更新last2[nums[i - 1]]相关的区间
        3. 特别的, 如果last[nums[i - 1]]存在, 并且last2[nums[i - 1]]不存在, 那么last2[nums[i - 1]]默认就是0, + 1就是1, 默认更新[0, last[nums[i - 1]]]区间的unique - 1
 */
public class LC2547_3 {
    // 线段树维护区间最小, 更新的时候对区间 整体 进行 加 操作
    private int[] mn, add;
    private int INF = 0x3f3f3f3f;
    private void update(int u, int L, int R, int l, int r, int v){
        if(l <= L && R <= r){
            mn[u] += v;
            add[u] += v;
        }else{
            pushdown(u);
            int mid = (L + R) >> 1;
            if(l <= mid) update(u << 1, L, mid, l, r, v);
            if(r > mid) update(u << 1 | 1, mid + 1, R, l, r, v);
            pushup(u);
        }
    }
    private void pushdown(int u){
        int v = add[u];
        mn[u << 1] += v; mn[u << 1 | 1] += v;
        add[u << 1] += v; add[u << 1 | 1] += v;
        add[u] = 0;
    }
    private void pushup(int u){
        mn[u] = Math.min(mn[u << 1], mn[u << 1 | 1]);
    }
    private int query(int u, int L, int R, int l, int r){
        if(l <= L && R <= r){
            return mn[u];
        }else{
            pushdown(u);
            int mid = (L + R) >> 1;
            int ret = INF;
            if(l <= mid) ret = Math.min(ret, query(u << 1, L, mid, l, r));
            if(r > mid) ret = Math.min(ret, query(u << 1 | 1, mid + 1, R, l, r));
            return ret;
        }
    }
    public int minCost(int[] nums, int k) {
        int n = nums.length;
        mn = new int[4 * n]; add = new int[4 * n];
        int ans = -1, max = Arrays.stream(nums).max().getAsInt();
        int[] last = new int[max + 1], last2 = new int[max + 1];
        Arrays.fill(last2, -1);
        for(int i = 1;i <= n;i++){
            update(1, 1, n, i, i, ans);
            update(1, 1, n, last[nums[i - 1]] + 1, i, -1);
            if(last2[nums[i - 1]] != -1) update(1, 1, n, last2[nums[i - 1]] + 1, last[nums[i - 1]], 1);
            ans = k + query(1, 1, n, 1, i);
            last2[nums[i - 1]] = last[nums[i - 1]];
            last[nums[i - 1]] = i;
        }
        return ans + n + 1;
    }
}
