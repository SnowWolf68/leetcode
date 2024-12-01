package problemList.dp.solution;

/**
假设翻转前缀时的最大下标为i, 翻转后缀时的最小下标为j
如果 i > j , 那么对于[i, j]区间的元素来说, 其会被翻转两次, 相当于没翻转, 由于翻转的代价和翻转的前后缀长度有关, 因此这样相当于浪费了一些代价
因此我们可以得出这样的一个结论: 对于前缀翻转的最大下标i 和 后缀翻转的最小下标j, 一定满足 i < j
既然前缀和后缀不会交叉, 那么我们就可以把前缀和后缀分开来考虑, 即 前后缀分解
因此问题转化成: 对于某一个前缀[0, i]或后缀[i, n - 1], 计算此时将这些元素全都翻转成0或翻转成1, 所需要的最少代价
这个计算过程可以使用dp来完成

状态机dp: 
prefix[i][j] 表示将[0, i]区间的前缀全都翻转成j, 所需要的最少代价, 其中j取0或1
prefix[i][0]: 
    1. s[i] == '0': 无需翻转, prefix[i][0] = prefix[i - 1][0]
    2. s[i] == '1': 需要翻转前缀[0, i]一次, 代价为i + 1, 并且翻转之后, 问题转化为: 将前缀[0, i - 1]区间的元素翻转成全1的代价
        prefix[i][0] = prefix[i - 1][1] + i + 1;
prefix[i][1]: 
    1. s[i] == '1': prefix[i][1] = prefix[i - 1][1];
    2. s[i] == '0': 和上面类似, 此时显然必须要翻转[0, i]区间一次, 那么问题转化为求[0, i - 1]区间的前缀翻转为0所需要的最小代价
        prefix[i][1] = prefix[i - 1][0] + i + 1;
初始化: 显然 i - 1 有可能越界, 因此添加两个辅助节点: dp[0][0], dp[0][1], 此时辅助节点意味着此时前缀没有任何元素, 那么显然这两个辅助节点都应该初始化为0

对于后缀的处理, 也是类似
suffix[i][j] 表示将[i, n - 1]区间的后缀全都翻转成j, 所需要的最少代价, 其中j取0或1
suffix[i][0]: 
    1. s[i] == '0': suffix[i][0] = suffix[i + 1][0];
    2. s[i] == '1': suffix[i][0] = suffix[i + 1][1] + n - i;
suffix[i][1]: 
    1. s[i] == '1': suffix[i][1] = suffix[i + 1][1];
    2. s[i] == '0': suffix[i][1] = suffix[i + 1][0] + n - i;
初始化: 这里同样 i + 1 有可能越界, 因此添加dp[n][0], dp[n][1]这两个辅助节点, 并且此时同样都初始化为0

dp完成之后, 枚举分割点, 前后缀分解即可

需要注意的是, 由于这里对于prefix[][]的dp添加了辅助节点, 因此状态转移方程需要做一些修改, 具体可以看代码
 */
public class LC2712 {
    public long minimumCost(String s) {
        int n = s.length();
        long[][] prefix = new long[n + 1][2], suffix = new long[n + 1][2];
        for(int i = 1;i <= n;i++){
            if(s.charAt(i - 1) == '0') {
                prefix[i][0] = prefix[i - 1][0];
                prefix[i][1] = prefix[i - 1][0] + i;    // 由于添加了辅助节点, 这里变为 + i 而不是 + i + 1
            }else {
                prefix[i][0] = prefix[i - 1][1] + i;    // 由于添加了辅助节点, 这里变为 + i 而不是 + i + 1
                prefix[i][1] = prefix[i - 1][1];
            }
        }
        for(int i = n - 1;i >= 0;i--){
            if(s.charAt(i) == '0'){
                suffix[i][0] = suffix[i + 1][0];
                suffix[i][1] = suffix[i + 1][0] + n - i;
            }else{
                suffix[i][0] = suffix[i + 1][1] + n - i;
                suffix[i][1] = suffix[i + 1][1];
            }
        }
        // 前后缀分解
        long ret = Math.min(suffix[0][0], suffix[0][1]);
        for(int i = 0;i < n;i++){
            // 分解为: [0, i] 和 [i + 1, n - 1] 两部分
            ret = Math.min(ret, Math.min(prefix[i + 1][0] + suffix[i + 1][0], prefix[i + 1][1] + suffix[i + 1][1]));
        }
        return ret;
    }
}
