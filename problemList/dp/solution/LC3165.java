package problemList.dp.solution;

/**
线段树上维护dp信息

这题挺有意思, 首先题目要求计算的 不包含相邻元素的子序列的最大和  (其实就是打家劫舍) 可以使用dp计算得到
并且通过示例1来看, 这里对于queries里的每一个查询, 其对nums的修改都是永久的, 因此对于每一个修改, 我们都需要重新进行一次dp来计算, 这显然不行

但是注意到, 对于 不包含相邻元素的子序列的最大和 这个信息, 我们可以使用分治来计算得到, 具体来说:
    如果我们想要计算某一整个区间上的 不包含相邻元素的子序列的最大和 , 那么我们可以将原本的区间拆分成左右两个子区间
    这里假设f1表示左边的区间不选最后一个元素, 此时的不包含相邻元素的子序列的最大和
           f2表示左边的区间选最后一个元素, 此时的不包含相邻元素的子序列的最大和
           f3表示右边的区间不选第一个元素, 此时不包含相邻元素的子序列的最大和
           f4表示右边的区间选第一个元素, 此时不包含相邻元素的子序列的最大和
    那么此时整个区间上的 不包含相邻元素的子序列的最大和 = max(f1 + f4, f2 + f3, f1 + f3)
    换句话说, 整个区间上的信息可以由两个子区间在O(1)的时间内计算得到, 显然这个信息可以使用线段树来维护

因此本题的总的思路就是: 使用 支持单点更新的线段树 维护 每一个区间的dp信息
不过需要注意的是, 这里的dp和普通的dp还有点差别, 在普通的dp中, 我们都是按照一定的顺序递推, 得到整个dp表
但是在这里, 由于dp的信息是在线段树中维护的, 因此我们需要在线段树中进行dp的计算
具体来说, 在线段树的build方法中, 我们就需要按照初始的nums信息, 计算得到线段树维护的每一个区间的dp信息
同时, 在单点修改中, 我们也需要在线段树的单点修改中, 完成dp值的更新
 */
public class LC3165 {
    public int maximumSumSubsequence(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] newNums = new int[n + 1];
        for(int i = 1;i <= n;i++) newNums[i] = nums[i - 1];
        int MOD = (int)1e9 + 7;
        SegmentTreeBasic segTree = new SegmentTreeBasic(n, MOD);
        segTree.build(1, 1, n, newNums);
        int ret = 0;
        for(int[] query : queries){
            segTree.modify(1, 1, n, query[0] + 1, query[1]);
            ret = (ret + segTree.getRet()) % MOD;
        }
        return ret;
    }
}

class SegmentTreeBasic {
    // 为了维护区间的dp信息, 这里需要一个二维数组, 这里我们可以这样定义
    // dp[o][0]: 第一个数选, 最后一个数不选, 此时o维护的这个区间中的不包含相邻元素的子序列的最大和
    // dp[o][1]: 第一个数选, 最后一个数选, 此时o维护的这个区间中的不包含相邻元素的子序列的最大和
    // dp[o][2]: 第一个数不选, 最后一个数不选, 此时o维护的这个区间中的不包含相邻元素的子序列的最大和
    // dp[o][3]: 第一个数不选, 最后一个数选, 此时o维护的这个区间中的不包含相邻元素的子序列的最大和
    private int[][] dp;

    private int MOD;

    // 线段树 维护的 下标范围: [1, n]   (不是线段树的下标范围)
    SegmentTreeBasic(int n, int _MOD) {
        this.MOD = _MOD;
        int len = 2 << (32 - Integer.numberOfLeadingZeros(n));
        this.dp = new int[len][4];
    }

    /**
        o, l, r: 当前节点以及当前区间的左右端点, 调用入口: o, l, r = 1, 1, n
        使用nums在[1, n]区间的元素初始化线段树, 这里的n和构造函数中传入的n是一样的
     */
    public void build(int o, int l, int r, int[] nums){
        if(l == r){
            // 这里的初始化需要注意, 由于此时区间中只有一个元素, 因此此时dp[o][1] = nums[l], 其余几个都是0
            dp[o][0] = dp[o][2] = dp[o][3] = 0;
            dp[o][1] = nums[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(o * 2, l, mid, nums);
        build(o * 2 + 1, mid + 1, r, nums);
        up(o);
    }

    /**
        在这up方法中, 完成dp信息的合并
        总区间上第一个选, 最后一个不选: dp[o][0]
            1. 左区间第一个选, 最后一个不选 - 右区间第一个选或不选, 最后一个不选
            2. 左区间第一个选, 最后一个选 - 右区间第一个不选, 最后一个不选
        总区间上 第一个选, 最后一个选: dp[o][1]
            1. 左区间第一个选, 最后一个不选 - 右区间第一个选或不选, 最后一个选
            2. 左区间第一个选, 最后一个选 - 右区间第一个不选, 最后一个选
        总区间上第一个不选, 最后一个不选: dp[o][2]
            1. 左区间第一个不选, 最后一个不选 - 右区间第一个选或不选, 最后一个不选
            2. 左区间第一个不选, 最后一个选 - 右区间第一个不选, 最后一个不选
        总区间上第一个不选, 最后一个选: dp[o][3]
            1. 左区间第一个不选, 最后一个不选 - 右区间第一个选或不选, 最后一个选
            2. 左区间第一个不选, 最后一个选 - 右区间第一个不选, 最后一个选
     */
    private void up(int o){
        dp[o][0] = Math.max((dp[o * 2][0] + Math.max(dp[o * 2 + 1][0], dp[o * 2 + 1][2])) % MOD, (dp[o * 2][1] + dp[o * 2 + 1][2]) % MOD);
        dp[o][1] = Math.max((dp[o * 2][0] + Math.max(dp[o * 2 + 1][1], dp[o * 2 + 1][3])) % MOD, (dp[o * 2][1] + dp[o * 2 + 1][3]) % MOD);
        dp[o][2] = Math.max((dp[o * 2][2] + Math.max(dp[o * 2 + 1][0], dp[o * 2 + 1][2])) % MOD, (dp[o * 2][3] + dp[o * 2 + 1][2]) % MOD);
        dp[o][3] = Math.max((dp[o * 2][2] + Math.max(dp[o * 2 + 1][1], dp[o * 2 + 1][3])) % MOD, (dp[o * 2][3] + dp[o * 2 + 1][3]) % MOD);
    }

    // 单点修改
    public void modify(int o, int l, int r, int idx, int val) {
        if (l == r) {
            // 如果递归到了叶子节点, 那么直接修改
            dp[o][1] = val;
            return;
        }
        // 不是叶子节点, 判断是在左子树还是右子树, 继续递归
        int mid = (l + r) >> 1;
        if (idx <= mid) modify(o * 2, l, mid, idx, val);
        else modify(o * 2 + 1, mid + 1, r, idx, val);
        // 最后在回溯的过程中更新当前节点
        up(o);
    }

    public int getRet(){
        return Math.max(Math.max(dp[1][0], dp[1][1]), Math.max(dp[1][2], dp[1][3]));
    }
}
